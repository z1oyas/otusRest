package api;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {

  protected static final String BASE_URL = System.getProperty("base.url");
  protected RequestSpecification spec;

  public BaseApi() {
    spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
  }

  public enum Method {
    GET,
    POST,
    PUT,
    DELETE
  }

  /**
   * делаем запрос http get/post/put/delete
   */
  protected abstract ValidatableResponse makeRequest(Method method);


}
