package pettests;

import static io.qameta.allure.Allure.step;

import com.google.inject.Provider;
import com.google.inject.name.Named;
import dto.createpet.PetBodyRequest;
import dto.createpet.Category;
import dto.createpet.Tag;
import extentions.Extention;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import api.BaseApi.Method;
import org.junit.jupiter.api.extension.ExtendWith;
import template.IRequestPipeline;
import java.util.List;
import utilits.DataGenerator;

@ExtendWith(Extention.class)
@org.junit.jupiter.api.Tag("PetShopTests")
@org.junit.jupiter.api.Tag("CreateNewPet")

public class CreateNewPetTest {
  @Inject
  @Named("createPetApi")
  Provider<IRequestPipeline> pipelineProvider;

  @Inject
  @Named("getPetApi")
  Provider<IRequestPipeline> getPipelineProvider;


  @Test
  @DisplayName("Создание карточки питомца в магазине")
  void createUser() {
    final Long[] id = new Long[1];
    final String[] name = new String[1];
    step("создание карточки питомца с валидными данными",() -> {
      name[0] = DataGenerator.generateName(9);
      id[0] = DataGenerator.generateId();
    });
    //сделать запрос на создание карточки питомца
    PetBodyRequest[] body = new PetBodyRequest[1];
    step("создание валидного тела запроса",() -> {
      body[0] = PetBodyRequest.builder()
                                    .id(id[0])
                                    .category(Category.builder()
                                                  .id(id[0]) //id категории
                                                  .name("Dogs") //название категории
                                                  .build())   //объект категории
                                    .name(name[0]) //имя питомца в магазине
                                    .photoUrls(List.of("https://gclnk.com/ynYpTObL")) //ссылки на фото питомца
                                    .status("available") // статус питомца
                                    .tags(List.of(Tag.builder()
                                                      .id(id[0])
                                                      .name("FamilyDogs")
                                                      .build())) // теги питомца
                                    .build();
    });
    IRequestPipeline pipeline = pipelineProvider.get();
    step("создание карточки питомца с валидными данными",() -> {
      pipeline
              .hasRequestBody(true) //есть тело запроса
              .setRequestBody(body[0]) //тело запроса
              .shouldValidate(true) // нужна ли валидация ответа
              .setStatusCode(200) // ожидаемый код ответа
              .setResponseBodySchemaPath("PetSchema.json") //путь к схеме
              .execute(); //выполнить запрос и валидацию
    });

    //сделать запрос на проверку, что карточка питомца заведена
    IRequestPipeline getPipeline = getPipelineProvider.get(); // <- новый экземпляр!
    step("проверка, что карточка питомца заведена, ожидание записи 15 секунд",() -> {
      getPipeline
              .needWait(true, 15) // ожидание записи, секунды
              .setPath("/" + id[0]) // id питомца
              .shouldValidate(true) // валидация
              .setStatusCode(200) // ожидаемый статус
              .setExpectedFields("id", id[0])
              .setExpectedFields("category.name", "Dogs")
              .setExpectedFields("name", name[0])
              .setExpectedFields("status", "available")
              .setExpectedFields("tags[0].name", "FamilyDogs")
              .setExpectedFields("photoUrls[0]", "https://gclnk.com/ynYpTObL")
              .setResponseBodySchemaPath("PetSchema.json") // путь к файлу схемы
              .execute();  //выполнить запрос и валидацию
    });
    System.out.println("Thread: " + Thread.currentThread().getName());
  }

  @Test
  @DisplayName("Попытка создания карточки питомца в магазине без передачи тела запроса")
  void createUserInvalid() {
    //сделать запрос на создание карточки питомца без передачи тела
    IRequestPipeline pipeline = pipelineProvider.get(); // <- новый экземпляр!
    step("попытка создания карточки питомца без передачи тела",() -> {
      pipeline
              .hasRequestBody(false) //есть тело запроса
              .shouldValidate(true) // нужна ли валидация ответа
              .setExpectedFields("message", "no data") // ожидаемое значение поля
              .setExpectedFields("type", "unknown") // ожидаемое значение поля
              .setStatusCode(405) // ожидаемый код ответа
              .execute(); //выполнить запрос и валидацию
    });

    System.out.println("Thread: " + Thread.currentThread().getName());
  }

  @Test
  @DisplayName("Попытка запроса с неправильным методом")
  void getRequestCreation() {
    //сделать запрос с неправильным методом
    IRequestPipeline pipeline = pipelineProvider.get(); // <- новый экземпляр!
    step("попытка создания карточки с неправильным методом",() -> {
      pipeline
              .hasRequestBody(false) //нет тела запроса
              .setRequestType(Method.GET) //неправильный метод
              .shouldValidate(true) // нужна ли валидация
              .setStatusCode(405)  // ожидаемый код ответа
              .execute();  //выполнить запрос и валидацию
    });
    System.out.println("Thread: " + Thread.currentThread().getName());
  }
}

