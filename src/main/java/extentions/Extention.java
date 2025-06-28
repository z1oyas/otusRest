package extentions;

import annatations.ApiType;
import api.BaseApi;
import com.google.inject.Guice;
import com.google.inject.Injector;
import factory.ApiFactory;
import guice.PipelineModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.lang.annotation.Annotation;

public class Extention implements BeforeEachCallback, AfterEachCallback {
  private static final ThreadLocal<Injector> INJECTOR_THREAD_LOCAL = new ThreadLocal<>();

  @Override
  public void afterEach(ExtensionContext context) {
    INJECTOR_THREAD_LOCAL.remove();
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    ApiFactory factory = new ApiFactory();
    String apiName = getAnnotation(ApiType.class, context);
    Injector injector = Guice.createInjector(new PipelineModule(factory.create(apiName)));
    INJECTOR_THREAD_LOCAL.set(injector);
    injector.injectMembers(context.getTestInstance().isPresent() ? context.getTestInstance().get() : null);
  }

  private <T extends Annotation> String getAnnotation(Class<T> annotation, ExtensionContext context) {
    Class<?> clazz = context.getRequiredTestClass();


    if (clazz.isAnnotationPresent(annotation)) {
      return clazz.getAnnotation(ApiType.class).value();
    }
    else
      throw new RuntimeException("неизвестный тип апи" + clazz.getCanonicalName());
  }
}
