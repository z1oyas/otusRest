package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import factory.ApiFactory;
import template.IRequestPipeline;
import template.RequestPipeline;
import java.util.List;

@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Модуль управляется DI, и его изменять извне не предполагается")
public class PipelineModule extends AbstractModule {
  private List<String> apiNames;

  public PipelineModule(List<String> apiNames) {
    this.apiNames = apiNames;

  }
  @Override
  protected void configure() {

    for (String apiName : apiNames) {
      bind(IRequestPipeline.class)
          .annotatedWith(Names.named(apiName))
          .toProvider(() -> new RequestPipeline(ApiFactory.create(apiName)))
          .in(Scopes.NO_SCOPE);
    }
  }
}
