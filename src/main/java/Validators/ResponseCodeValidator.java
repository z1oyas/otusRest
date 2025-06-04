package Validators;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

public class ResponseCodeValidator {

  public ValidatableResponse validateResponseCode(ValidatableResponse response, int code) {
    return response.statusCode(code);
  }
}
