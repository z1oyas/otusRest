package template;

import com.google.inject.Inject;
import validators.Validator;
import api.BaseApi;
import dto.ABodyRequest;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

public class RequestPipeline implements IRequestPipeline {
  private String path;
  private boolean shouldValidate;
  private BaseApi api;
  private BaseApi.Method method;
  private ABodyRequest body;
  private Map<String, String> headers;
  private Map<String, String> responseHeaders;
  private boolean hasBody;
  boolean validate;
  private Map<String, Object> expectedFields;

  private Integer code;
  private String schemaPath;

  public RequestPipeline() {
    setDefaultValues();
  }

  @Inject
  public RequestPipeline(BaseApi api) {
    setDefaultValues();
    this.api = api;
  }

  private void setDefaultValues(){
    path = "";
    this.expectedFields = new HashMap<>();
    shouldValidate = false;
    this.api = null;
    this.method = BaseApi.Method.GET;
    this.body = null;
    this.headers = new HashMap<>();
    this.responseHeaders = new HashMap<>();
    this.hasBody = false;
    this.validate = false;

    this.code = 200;

    this.schemaPath = "";
  }


  @Override
  public void execute() {
    this.api.setPath(this.path)
        .setHeaders(this.headers);
    if (hasBody) {
      this.api.setBody(this.body);
    }

    ValidatableResponse response = this.api.makeRequest(method);

    if (shouldValidate) {
      Validator validator = new Validator.ValidationParams()
                                .setResponse(response) //ответ от сервера
                                .setExpectedStatusCode(code) // ожидаемый статус код
                                .setFieldValue(expectedFields) // ожидаемые поля
                                .setSchemaPath(schemaPath) // путь к схеме ответа
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
  public RequestPipeline setRequestHeaders(String header, String value) {
    this.headers.put(header, value);
    return this;
  }


  @Override
  public RequestPipeline setExpectedFields(String field, Object value) {
    this.expectedFields.put(field, value);
    return this;
  }

  @Override
  public RequestPipeline setStatusCode(Integer code) {
    this.code = code;
    return this;
  }

  @Override
  public RequestPipeline setExpectedHeaders(String header, String value) {
    this.responseHeaders.put(header, value);
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
  public IRequestPipeline shouldValidate(boolean shouldValidate) {
    this.shouldValidate = shouldValidate;
    return this;
  }
}
