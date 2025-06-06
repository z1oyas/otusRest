package PetTests;

import api.CreatePetApi;
import dto.CreatePet.PetBodyRequest;
import dto.CreatePet.Category;
import dto.CreatePet.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import api.BaseApi.Method;
import template.IRequestPipeline;
import template.RequestPipeline;
import java.util.List;

@org.junit.jupiter.api.Tag("PetShopTests")
@org.junit.jupiter.api.Tag("CreateNewPet")
public class CreateNewPetTest {
  IRequestPipeline pipeline;
  CreatePetApi createPetApi;

  @BeforeEach
  public void setUp() {
    pipeline = new RequestPipeline();
    createPetApi = new CreatePetApi();
    pipeline.setApi(createPetApi);
    pipeline.setPath("");
    pipeline.setRequestType(Method.POST);
  }

  //Создание карточки питомца в магазине с разными наборами полей
  @Test
  @DisplayName("Создание карточки питомца в магазине с разными наборами полей")
  void createUser() {
    PetBodyRequest body = PetBodyRequest.builder()
                                    .id(89219886273L)
                                    .category(Category.builder()
                                                  .id(89219886273L) //id категории
                                                  .name("Dogs") //название категории
                                                  .build())   //объект категории
                                    .name("Banana") //имя питомца в магазине
                                    .photoUrls(List.of("https://gclnk.com/ynYpTObL")) //ссылки на фото питомца
                                    .status("available") // статус питомца
                                    .tags(List.of(Tag.builder()
                                              .id(89219886273L)
                                              .name("FamilyDogs")
                                              .build())) // теги питомца
                                    .build();
    pipeline
        .hasRequestBody(true)
        .setRequestBody(body)
        .shouldValidate(true)
        .setStatusCode(200)
        .setResponseBodySchemaPath("PetSchema.json")
        .execute();
  }

  @Test
  @DisplayName("Попытка создания карточки питомца в магазине без передачи тела запроса")
  void createUserInvalid() {
    pipeline
        .hasRequestBody(false)
        .shouldValidate(true)
        .SetExpectedFields("message", "no data")
        .SetExpectedFields("type", "unknown")
        .setStatusCode(405)
        .execute();
  }

  @Test
  @DisplayName("Попытка запроса с неправильным методом")
  void getRequestCreation() {
    pipeline
    .setApi(createPetApi)
    .hasRequestBody(false)
    .setRequestType(Method.GET)
    .shouldValidate(true)
    .setStatusCode(405)
    .execute();
  }
}

