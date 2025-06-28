package extentions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import factory.ApiFactory;
import guice.PipelineModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Extention implements BeforeEachCallback, AfterEachCallback {
  private static final ThreadLocal<Injector> INJECTOR_THREAD_LOCAL = new ThreadLocal<>();

  @Override
  public void afterEach(ExtensionContext context) {
    INJECTOR_THREAD_LOCAL.remove();
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    Object testInstance = context.getTestInstance().orElseThrow();
    Class<?> testClass = testInstance.getClass();
    List<String>  apiNames = getAnnotation(testClass);
    Injector injector = Guice.createInjector(new PipelineModule(apiNames));
    INJECTOR_THREAD_LOCAL.set(injector);
    injector.injectMembers(context.getTestInstance().isPresent() ? context.getTestInstance().get() : null);
  }

  private <T extends Annotation> List<String> getAnnotation(Class<?> clazz) {
    List<String> namedValues = new ArrayList<>();
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(Named.class)) {
        Named named = field.getAnnotation(Named.class);
        if (named != null) {
          namedValues.add(named.value());
        }
      }
    }
    if (namedValues.isEmpty()) {
      throw new RuntimeException("Не найдена аннотация @Named на полях в " + clazz.getSimpleName());
    }
    return namedValues;
  }
}
