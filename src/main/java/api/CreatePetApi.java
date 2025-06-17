package api;

import static io.restassured.RestAssured.given;

import dto.createpet.PetBodyRequest;
import io.restassured.response.ValidatableResponse;

public class CreatePetApi extends BaseApi<PetBodyRequest> {


  public CreatePetApi() {
    super();
  }

  @Override
  public ValidatableResponse makeRequest(Method method) {
    switch (method) {
      case POST:
        if(bodyRequest!=null) {
          return makePostRequest(bodyRequest);
        }
        else if(bodyRequest==null) {
          return makeNoBodyPostRequest();
        }
      case GET: return makeGetRequest();
      default:
        throw new UnsupportedOperationException("Method not supported: " + method);
    }
  }


  private ValidatableResponse makePostRequest(PetBodyRequest body) {
    return given(spec)
               .basePath("/pet"+path)
               .headers(safeHeaders())
               .body(body)
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }

  private ValidatableResponse makeNoBodyPostRequest() {
    return given(spec)
               .basePath("/pet"+path)
               .headers(safeHeaders())
               .log().all()
               .when()
               .post()
               .then()
               .log().all();
  }

  private ValidatableResponse makeGetRequest() {
    return given(spec)
               .basePath("/pet"+path)
               .headers(safeHeaders())
               .log().all()
               .when()
               .get()
               .then()
               .log().all();
  }
}
