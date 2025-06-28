package guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import factory.ApiFactory;
import template.IRequestPipeline;
import template.RequestPipeline;
import java.util.List;

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
          .toInstance(new RequestPipeline(ApiFactory.create(apiName)));
    }
  }
}
