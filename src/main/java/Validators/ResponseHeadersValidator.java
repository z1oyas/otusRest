package Validators;

import io.restassured.response.ValidatableResponse;
import java.util.HashMap;

public class ResponseHeadersValidator {

  public ValidatableResponse validateResponseHeaders(ValidatableResponse response, HashMap<String,String> headers) {
    headers.entrySet().forEach(entry -> {
      response.header(entry.getKey(), entry.getValue());
    });
    return response;
  }
}
