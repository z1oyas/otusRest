package pettests;

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
    // сделать запрос на получение карточки питомца по id
    pipeline
        .setPath("/9") // id питомца
        .hasRequestBody(false) // нет тела
        .shouldValidate(true) // валидация
        .setStatusCode(200) // ожидаемый статус
        .setExpectedHeaders("access-control-allow-methods", "GET, POST, DELETE, PUT") // ожидаемые заголовки
        .setResponseBodySchemaPath("PetSchema.json") // путь к файлу схемы
        .execute();  //выполнить запрос и валидацию
  }

  @Test
  @DisplayName("Запрос к магазину c несуществующим id питомца")
  void getPetByNoNExistingID() {
    // сделать запрос на получение карточки питомца по несуществующему id
    pipeline
        .setRequestHeaders("api_key", "special-key") // заголовки запроса
        .setPath("/892198862731") // несуществующий id питомца
        .hasRequestBody(false) // нет тела
        .shouldValidate(true) // валидация
        .setStatusCode(404) // ожидаемый статус
        .execute(); //выполнить запрос и валидацию
  }

  @Test
  @DisplayName("Запрос к магазину c невалидным id питомца")
  void getPetByInvalidID() {
    // сделать запрос на получение карточки питомца по невалидному id
    pipeline
        .setPath("/testId") // невалидный id питомца
        .hasRequestBody(false) // нет тела
        .shouldValidate(true) // валидация
        .setStatusCode(404) // ожидаемый статус
        .setExpectedFields("message", "java.lang.NumberFormatException: For input string: \"testId\"") // ожидаемые заголовки
        .execute(); //выполнить запрос и валидацию
  }
}
