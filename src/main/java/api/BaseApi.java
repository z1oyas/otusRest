package api;

import static io.restassured.RestAssured.given;

import dto.ABodyRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {

  protected static final String BASE_URL = System.getProperty("base.url");
  protected RequestSpecification spec;
  protected ABodyRequest bodyRequest;

  public BaseApi() {
    bodyRequest = null;
    spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
  }

  public void setBody(ABodyRequest bodyRequest){
    this.bodyRequest = bodyRequest;
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
  public abstract ValidatableResponse makeRequest(Method method, String path);


}
