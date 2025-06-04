package Validators;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class ResponseBodyValidator {

  public ValidatableResponse validateResponse(ValidatableResponse response, String schemaPath) {
    return response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
  }
}
