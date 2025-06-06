package api;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

public class GetPetApi extends BaseApi {

  public GetPetApi() {
    super();
  }

  @Override
  public ValidatableResponse makeRequest(Method method, String path) {
    switch (method) {
      case GET: return makeGetRequest(path);
      default:
        return null;
    }
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
