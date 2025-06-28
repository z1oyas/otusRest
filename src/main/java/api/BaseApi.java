package api;

import static io.restassured.RestAssured.given;

import dto.ABodyRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseApi<T extends ABodyRequest> {

  protected static final String BASE_URL = System.getProperty("base.url");
  protected RequestSpecification spec;
  protected T bodyRequest;
  protected String path;
  protected Map<String, String> headers;

  public BaseApi() {
    bodyRequest = null;
    headers= new HashMap<>();
    path = "";
    spec = given()
               .baseUri(BASE_URL)
               .contentType(ContentType.JSON);
  }

  public BaseApi setBody(T bodyRequest){
    this.bodyRequest = bodyRequest;
    return this;
  }

  public BaseApi setPath(String path){
    this.path = path;
    return this;
  }

  public BaseApi setHeaders(Map<String, String> headers){
    this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
    return this;
  }
  protected Map<String, String> safeHeaders() {
    return headers != null ? headers : Collections.emptyMap();
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
  public abstract ValidatableResponse makeRequest(Method method);

  /**
   * Указывает дефолтный метод
   */
  public Method getDefaultMethod() {
    return Method.GET;
  }
  /**
   * Указывает дефолтный путь
   */
  public String getDefaultPath() {
    return "";
  }
}
