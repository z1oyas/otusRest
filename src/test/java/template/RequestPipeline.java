package template;

import Validators.Validator;
import api.BaseApi;
import dto.ABodyRequest;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

public class RequestPipeline implements IRequestPipeline {
  private String path;
  private boolean needValidation;
  private BaseApi api;
  private BaseApi.Method method;
  private ABodyRequest body;
  private Map<String, String> headers;
  private boolean hasBody;
  boolean validate;
  private Map<String, String> ValidationFieldValue;

  private Integer code;
  private Map<String, String> responseHeaders;
  private String schemaPath;

  public RequestPipeline() {
    path = "";
    this.ValidationFieldValue =new HashMap<>();
    needValidation = false;
    this.api = null;
    this.method = BaseApi.Method.GET;
    this.body = null;
    this.headers = null;
    this.hasBody = false;
    this.validate = false;

    this.code = 200;
    this.responseHeaders = null;
    this.schemaPath ="";
  }

  @Override
  public void run() {
    if(hasBody){
      this.api.setBody(this.body);
    }
    else if(!hasBody) {
    }

    ValidatableResponse response = this.api.makeRequest(method, path);

    if(needValidation) {
      Validator validator = new Validator.ValidationParams()
                                .setResponse(response) //ответ от сервера
                                .setExpectedStatusCode(code) // ожидаемый статус код
                                .setFieldValue(ValidationFieldValue)
                                .setSchemaPath(schemaPath)
                                .setExpectedHeaders(responseHeaders)//ожидаемые значение полей
                                .bulid();
      validator.validate();
    }
  }

  @Override
  public IRequestPipeline setPath(String path) {
    this.path = path;
    return this;
  }


  @Override
  public RequestPipeline setRequestType(BaseApi.Method method) {
    this.method = method;
    return this;
  }

  @Override
  public RequestPipeline setRequestBody(ABodyRequest body) {
    this.body = body;
    return this;
  }

  @Override
  public RequestPipeline hasRequestBody(boolean hasBody) {
    this.hasBody = hasBody;
    return this;
  }

  @Override
  public RequestPipeline setRequestHeaders(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }


  @Override
  public RequestPipeline setFieldForValidation(String field, String value) {
    this.ValidationFieldValue.put(field, value);
//    this.ValidationField = field;
//    this.ValidationValue = value;
    return this;
  }

  @Override
  public RequestPipeline setStatusCode(Integer code) {
    this.code = code;
    return this;
  }

  @Override
  public RequestPipeline setResponseHeaders(Map<String, String> headers) {
    this.responseHeaders = headers;
    return this;
  }

  @Override
  public RequestPipeline setResponseBodySchemaPath(String schemaPath) {
    this.schemaPath = schemaPath;
    return this;
  }

  @Override
  public IRequestPipeline setApi(BaseApi api) {
    this.api = api;
    return this;
  }

  @Override
  public IRequestPipeline needValidation(boolean needValidation) {
    this.needValidation = needValidation;
    return this;
  }
}
