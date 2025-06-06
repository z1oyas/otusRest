package api;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

public class GetPetApi extends BaseApi {

  public GetPetApi() {
    super();
  }

  @Override
  public ValidatableResponse makeRequest(Method method) {
    switch (method) {
      case GET: return makeGetRequest();
      default:
        throw new UnsupportedOperationException("Method not supported: " + method);
    }
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
