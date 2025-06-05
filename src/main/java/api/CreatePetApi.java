package api;

import static io.restassured.RestAssured.given;

import dto.CreatePet.CreatePetBodyRequest;
import io.restassured.response.ValidatableResponse;

public class CreatePetApi extends BaseApi {
  private CreatePetBodyRequest body;

  public CreatePetApi(CreatePetBodyRequest body) {
    this.body = body;
  }

  @Override
  public ValidatableResponse makeRequest(Method method) {
    switch (method) {
      case POST:
        if(body!=null) {
          return makePostRequest(body);
        }
        else if(body==null) {
          return makeNoBodyPostRequest();
        }
      default:
        return null;
    }
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

  private ValidatableResponse makeNoBodyPostRequest() {
    return given(spec)
               .basePath("/pet")
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }
}
