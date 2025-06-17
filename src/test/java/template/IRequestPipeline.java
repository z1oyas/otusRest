package template;

import api.BaseApi;
import api.BaseApi.Method;
import dto.ABodyRequest;

public interface IRequestPipeline {

  void execute();

  IRequestPipeline setPath(String path);

  IRequestPipeline setRequestType(Method method);

  IRequestPipeline setRequestBody(ABodyRequest body);

  IRequestPipeline hasRequestBody(boolean hasBody);

  IRequestPipeline setRequestHeaders(String header, String value);

  IRequestPipeline setExpectedFields(String field, String value);

  IRequestPipeline setStatusCode(Integer code);

  IRequestPipeline setExpectedHeaders(String header, String value);

  IRequestPipeline setResponseBodySchemaPath(String schemaPath);

  IRequestPipeline setApi(BaseApi api);

  IRequestPipeline shouldValidate(boolean shouldValidate);

}
