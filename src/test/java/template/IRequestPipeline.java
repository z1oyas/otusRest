package template;

import api.BaseApi;
import api.BaseApi.Method;
import dto.ABodyRequest;
import java.util.Map;

public interface IRequestPipeline {

   void run();

   IRequestPipeline setPath(String path);

   IRequestPipeline setRequestType(Method method);

   IRequestPipeline setRequestBody(ABodyRequest body);

   IRequestPipeline hasRequestBody(boolean hasBody);

   IRequestPipeline setRequestHeaders(Map<String, String> headers);

   IRequestPipeline setFieldForValidation(String field, String value);

   IRequestPipeline setStatusCode(Integer code);

   IRequestPipeline setResponseHeaders(Map<String, String> headers);

   IRequestPipeline setResponseBodySchemaPath(String schemaPath);

  IRequestPipeline setApi(BaseApi api);

   IRequestPipeline needValidation(boolean needValidation);

}
