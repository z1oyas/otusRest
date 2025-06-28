package guice;

import api.BaseApi;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import template.IRequestPipeline;
import template.RequestPipeline;

public class PipelineModule extends AbstractModule {
  private BaseApi api;

  public PipelineModule(BaseApi api) {
    this.api = api;

  }
  @Override
  protected void configure() {
    bind(IRequestPipeline.class).to(RequestPipeline.class).in(Scopes.NO_SCOPE);
  }

  @Provides
  public BaseApi provideApi() {
    return api;
  }
}
