package pet;

import api.BaseApi;
import api.GetPetApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import template.IRequestPipeline;
import template.RequestPipeline;

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
        .setPath("/89219886273")
        .hasRequestBody(false)
        .needValidation(true)
        .setStatusCode(200)
        .setResponseBodySchemaPath("PetSchema.json")
        .run();
  }
  @Test
  @DisplayName("Запрос к магазину c несуществующим id питомца")
  void getPetByNoNExistingID() {
    pipeline
        .setApi(getPetApi)
        .setPath("/892198862731")
        .hasRequestBody(false)
        .needValidation(true)
        .setStatusCode(404)
        .run();
  }

  @Test
  @DisplayName("Запрос к магазину c невалидным id питомца")
  void getPetByInvalidID() {
    pipeline
        .setApi(getPetApi)
        .setPath("/testId")
        .hasRequestBody(false)
        .needValidation(true)
        .setStatusCode(404)
        .setFieldForValidation("message", "java.lang.NumberFormatException: For input string: \"testId\"")
        .run();
  }
}
