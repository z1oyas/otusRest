package pettests;

import annatations.ApiType;
import api.BaseApi;
import api.CreatePetApi;
import api.GetPetApi;
import dto.createpet.PetBodyRequest;
import dto.createpet.Category;
import dto.createpet.Tag;
import extentions.Extention;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import api.BaseApi.Method;
import org.junit.jupiter.api.extension.ExtendWith;
import template.IRequestPipeline;
import template.RequestPipeline;
import java.util.List;

@ExtendWith(Extention.class)
@org.junit.jupiter.api.Tag("PetShopTests")
@org.junit.jupiter.api.Tag("CreateNewPet")
@ApiType("createPetApi")
public class CreateNewPetTest {
  @Inject
  IRequestPipeline pipeline;
  //CreatePetApi createPetApi;
//  @Inject
//  IRequestPipeline getPipeline;
  //GetPetApi getPetApi;

//  @BeforeEach
//  public void setUp() {
//    pipeline = new RequestPipeline();
//    createPetApi = new CreatePetApi();
//    pipeline.setApi(createPetApi);
//    pipeline.setPath("");
//    pipeline.setRequestType(Method.POST);
//
//    getPipeline = new RequestPipeline();
//    getPetApi = new GetPetApi();
//    getPipeline.setApi(getPetApi);
//    getPipeline.setRequestType(BaseApi.Method.GET);
//  }

  @Test
  @DisplayName("Создание карточки питомца в магазине")
  void createUser() {
//    pipeline.setPath("");
//    pipeline.setRequestType(Method.POST);

    //getPipeline.setApi(getPetApi);
    //getPipeline.setRequestType(BaseApi.Method.GET);

    //сделать запрос на создание карточки питомца
    PetBodyRequest body = PetBodyRequest.builder()
                              .id(79219886274L)
                              .category(Category.builder()
                                            .id(79219886274L) //id категории
                                            .name("Dogs") //название категории
                                            .build())   //объект категории
                              .name("Potatos") //имя питомца в магазине
                              .photoUrls(List.of("https://gclnk.com/ynYpTObL")) //ссылки на фото питомца
                              .status("available") // статус питомца
                              .tags(List.of(Tag.builder()
                                                .id(79219886274L)
                                                .name("FamilyDogs")
                                                .build())) // теги питомца
                              .build();
    pipeline
        .setPath("")
        .setRequestType(Method.POST)
        .hasRequestBody(true) //есть тело запроса
        .setRequestBody(body) //тело запроса
        .shouldValidate(true) // нужна ли валидация ответа
        .setStatusCode(200) // ожидаемый код ответа
        .setResponseBodySchemaPath("PetSchema.json") //путь к схеме
        .execute(); //выполнить запрос и валидацию

    //сделать запрос на проверку, что карточка питомца заведена
//    getPipeline
//        .setPath("/79219886274") // id питомца
//        .shouldValidate(true) // валидация
//        .setStatusCode(200) // ожидаемый статус
//        .setExpectedFields("id", 79219886274L)
//        .setExpectedFields("category.name", "Dogs")
//        .setExpectedFields("name", "Potatos")
//        .setExpectedFields("status", "available")
//        .setExpectedFields("tags[0].name", "FamilyDogs")
//        .setExpectedFields("photoUrls[0]", "https://gclnk.com/ynYpTObL")
//        .setResponseBodySchemaPath("PetSchema.json") // путь к файлу схемы
//        .execute();  //выполнить запрос и валидацию
  }

  @Test
  @DisplayName("Попытка создания карточки питомца в магазине без передачи тела запроса")
  void createUserInvalid() {
    //сделать запрос на создание карточки питомца без передачи тела
    pipeline
        .setPath("")
        .setRequestType(Method.POST)
        .hasRequestBody(false) //есть тело запроса
        .shouldValidate(true) // нужна ли валидация ответа
        .setExpectedFields("message", "no data") // ожидаемое значение поля
        .setExpectedFields("type", "unknown") // ожидаемое значение поля
        .setStatusCode(405) // ожидаемый код ответа
        .execute(); //выполнить запрос и валидацию
  }

  @Test
  @DisplayName("Попытка запроса с неправильным методом")
  void getRequestCreation() {
    //сделать запрос с неправильным методом
    pipeline
        .hasRequestBody(false) //нет тела запроса
        .setRequestType(Method.GET) //неправильный метод
        .shouldValidate(true) // нужна ли валидация
        .setStatusCode(405)  // ожидаемый код ответа
        .execute();  //выполнить запрос и валидацию
  }
}

