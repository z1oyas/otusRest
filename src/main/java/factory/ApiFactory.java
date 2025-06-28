package factory;

import api.BaseApi;
import api.CreatePetApi;
import api.GetPetApi;

public class ApiFactory {

  public static BaseApi create(String apiName) {
    switch (apiName) {
      case "createPetApi": return new CreatePetApi();
      case "getPetApi": return new GetPetApi();
      default: throw new UnsupportedOperationException("API not supported: " + apiName);
    }
  }

}
