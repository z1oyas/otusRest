package PetTests;

import api.BaseApi;
import api.GetPetApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import template.IRequestPipeline;
import template.RequestPipeline;

@org.junit.jupiter.api.Tag("PetShopTests")
@org.junit.jupiter.api.Tag("GetPetByID")
public class GetPetByIDTest {
  IRequestPipeline pipeline;
  GetPetApi getPetApi;

  @BeforeEach
  public void setUp() {
    pipeline = new RequestPipeline();
    getPetApi = new GetPetApi();
    pipeline.setApi(getPetApi);
    pipeline.setRequestType(BaseApi.Method.GET);
  }
  @Test
  @DisplayName("Запрос к магазину по id питомца")
  void getPetByID() {
    pipeline
        .setApi(getPetApi)
        .setPath("/8")
        .hasRequestBody(false)
        .shouldValidate(true)
        .setStatusCode(200)
        .setExpectedHeaders("access-control-allow-methods", "GET, POST, DELETE, PUT")
        .setResponseBodySchemaPath("PetSchema.json")
        .execute();
  }
  @Test
  @DisplayName("Запрос к магазину c несуществующим id питомца")
  void getPetByNoNExistingID() {
    pipeline
        .setApi(getPetApi)
        .setRequestHeaders("api_key", "special-key")
        .setPath("/892198862731")
        .hasRequestBody(false)
        .shouldValidate(true)
        .setStatusCode(404)
        .execute();
  }

  @Test
  @DisplayName("Запрос к магазину c невалидным id питомца")
  void getPetByInvalidID() {
    pipeline
        .setApi(getPetApi)
        .setPath("/testId")
        .hasRequestBody(false)
        .shouldValidate(true)
        .setStatusCode(404)
        .SetExpectedFields("message", "java.lang.NumberFormatException: For input string: \"testId\"")
        .execute();
  }
}
