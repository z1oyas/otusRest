package pet;

import Validators.Validator;
import api.CreatePetApi;
import dto.CreatePet.CreatePetBodyRequest;
import dto.CreatePet.Category;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import api.BaseApi.Method;
public class CreateNewPetTest {

  //Создание карточки питомца в магазине с разными наборами полей
  @Test
  @DisplayName("Создание карточки питомца в магазине с разными наборами полей")
  void createUser() {
    CreatePetBodyRequest body = CreatePetBodyRequest.builder()
                                    .id(212123L)
                                    .category(Category.builder()
                                                  .id(212123L) //id категории
                                                  .name("MyDog1") //название категории
                                                  .build())   //объект категории
                                    .name("doggie") //имя питомца в магазине
                                    .photoUrls(null) //ссылки на фото питомца
                                    .status("available") // статус питомца
                                    .tags(null) // теги питомца
                                    .build();
    CreatePetApi createPetApi = new CreatePetApi(body);
    ValidatableResponse response = createPetApi.makeRequest(Method.POST);

    Validator validator = new Validator.ValidationParams()
                              .setResponse(response) //ответ от сервера
                              .setExpectedStatusCode(200) // ожидаемый статус код
                              .setSchemaPath("createPetSchema.json")  //путь к схеме валидации
                              .bulid();
    validator.validate();
  }

  @Test
  @DisplayName("Попытка создания карточки питомца в магазине без передачи тела запроса")
  void createUserInvalid() {
    CreatePetApi createPetApi = new CreatePetApi(null);
    ValidatableResponse response = createPetApi.makeRequest(Method.POST);

    Validator validator = new Validator.ValidationParams()
                              .setResponse(response) //ответ от сервера
                              .setExpectedStatusCode(405) // ожидаемый статус код
                              .setFieldValue( "type", "unknown") //ожидаемые значение полей
                              .setFieldValue( "message", "no data")
                              .bulid();
    validator.validate();
  }
}

