package factory;

import template.IRequestPipeline;
import template.RequestPipeline;

public class PipelineFactory {

  public static IRequestPipeline create() {
    return new RequestPipeline();
    }
}
