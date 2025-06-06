package Validators;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

public class Validator {
  String schemaPath;
  ValidatableResponse response;
  private Integer code;
  Map<String,String> headers;
  Map<String,String> fieldValue;


  private Validator(ValidationParams validationParams) {
    this.response = validationParams.response;
    this.code = validationParams.code;
    this.headers = validationParams.headers;
    this.schemaPath = validationParams.schemaPath;
    this.fieldValue = validationParams.fieldValue;
  }

  public void validate(){
    if(schemaPath!=null){
      this.validateResponse();
    }
    if (code != null) {
      this.validateResponseCode();
    }
    if((headers != null && !headers.isEmpty())){
      this.validateResponseHeaders();
    }
    if((fieldValue != null && !fieldValue.isEmpty())){
      this.validateFieldsValues();
    }
  }

  private ValidatableResponse validateResponse() {
    return response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
  }

  private void validateFieldsValues() {
    fieldValue.forEach((key, value) -> response.body(key, equalTo(value)));
  }
  private ValidatableResponse validateResponseCode() {
      return response.statusCode(code);
  }

  private ValidatableResponse validateResponseHeaders() {
    headers.forEach((key, value) -> response.header(key, value));
    return response;
  }

  public static class ValidationParams {
    private String schemaPath;
    private ValidatableResponse response;
    private Integer code;
    private Map<String,String> headers = new HashMap<>();
    private Map<String,String> fieldValue = new HashMap<>();

    public ValidationParams() {
    }

    public ValidationParams setFieldValue(Map<String, String> fieldValue) {
      this.fieldValue = fieldValue;
      return this;
    }

    public ValidationParams setResponse(ValidatableResponse response) {
      this.response = response;
      return this;
    }

    public ValidationParams setExpectedStatusCode(Integer code) {
      this.code = code;
      return this;
    }

    public ValidationParams setExpectedHeaders(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public ValidationParams setSchemaPath(String schemaPath) {
      if(schemaPath.isEmpty()){
        return this;
      }
      else {
        this.schemaPath = "schema/" + schemaPath;
      }
      return this;
    }

    public Validator bulid(){
     return new Validator(this);
    }
  }

}
