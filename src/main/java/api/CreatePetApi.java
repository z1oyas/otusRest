package api;

import static io.restassured.RestAssured.given;

import dto.ABodyRequest;
import io.restassured.response.ValidatableResponse;

public class CreatePetApi extends BaseApi {

  public CreatePetApi() {
    super();
  }

  @Override
  public ValidatableResponse makeRequest(Method method, String path) {
    switch (method) {
      case POST:
        if(bodyRequest!=null) {
          return makePostRequest(bodyRequest, path);
        }
        else if(bodyRequest==null) {
          return makeNoBodyPostRequest(path);
        }
      case GET: return makeGetRequest(path);
      default:
        return null;
    }
  }


  private ValidatableResponse makePostRequest(ABodyRequest body, String path) {
    return given(spec)
               .basePath("/pet"+path)
               .body(body)
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }

  private ValidatableResponse makeNoBodyPostRequest(String path) {
    return given(spec)
               .basePath("/pet"+path)
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }

  private ValidatableResponse makeGetRequest(String path) {
    return given(spec)
               .basePath("/pet"+path)
               .log().all()
               .when()
               .get()
               .then()
               .log().all();
  }
}
