package api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import dto.CreatePet.CreatePetBodyRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import java.util.HashMap;

public class CreatePetApi extends BaseApi {
  private CreatePetBodyRequest body;

  public CreatePetApi(CreatePetBodyRequest body) {
    this.body = body;
  }

  @Override
  public ValidatableResponse makeRequest(Method method) {
    switch (method) {
      case POST:
        return makePostRequest(body);
      default:
        return null;
    }
  }

  @Override
  public void validateResponse(ValidatableResponse response, HashMap<String,String> params) {
    response.statusCode(HttpStatus.SC_OK)
        //.time(lessThan(1000L))
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
        .body("type", equalTo("unknown"))
        .body("code", equalTo(200))
        .body("message", equalTo("408"));
  }

  private ValidatableResponse makePostRequest(CreatePetBodyRequest body) {
    return given(spec)
               .basePath("/pet")
               .body(body)
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }
}
