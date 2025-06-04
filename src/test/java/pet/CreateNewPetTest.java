package pet;

import api.CreatePetApi;
import dto.CreatePet.CreatePetBodyRequest;
import dto.CreatePet.Category;
import org.junit.jupiter.api.Test;
import api.BaseApi.Method;
public class CreateNewPetTest {

  //Создание карточки питомца в магазине с разными наборами полей
  @Test
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
    createPetApi.makeRequest(Method.POST);
  }
}
