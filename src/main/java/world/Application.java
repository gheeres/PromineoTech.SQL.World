package world;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import world.dao.WorldMySqlDao;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;
import world.entity.CountryLanguageEntity;

public class Application {
  public Application() {
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    Scanner input = new Scanner(System.in);

    WorldMySqlDao worldDao = new WorldMySqlDao();
    worldDao.createSchema()
            .initializeData();
    
    CityInputEntity newCity = new CityInputEntity()
        .setCountry("USA")
        .setName("Mosinee")
        .setLatitude(44.7930f)
        .setLongitude(-89.7032f)
        .setPopulation(3988);
    Optional<CityEntity> addedCity = worldDao.addCity(newCity);
    if (addedCity.isPresent()) {
      System.out.printf("Added new city: %s%n", addedCity.get());
    }
    
    //List<CountryEntity> countries = worldDao.getCountries();
    //for(CountryEntity country: countries) {
    //  System.out.println(country);
    //}
    
    System.out.println("Enter the country to edit (ISO 3166-3/XXX): ");
    String code = input.nextLine();
    Optional<CountryEntity> optionalCountry = worldDao.getCountryByCode(code);
    if (optionalCountry.isPresent()) {
      CountryEntity country = optionalCountry.get();
      System.out.println(country);
      
      System.out.println("Enter the new language for the country (ISO 639):");
      String languageCode = input.nextLine();

      Optional<CountryLanguageEntity> existingLanguage = 
          worldDao.getCountryLanguage(code, languageCode);
      if (existingLanguage.isPresent()) {
        System.out.println("This language already exists. Do you wan to delete it?");
        String prompt = input.nextLine();
        if ("Y".equalsIgnoreCase(prompt)) {
          Optional<CountryLanguageEntity> deletedLanguage =
                worldDao.deleteLanguage(code, languageCode);
          if (deletedLanguage.isPresent()) {
            System.out.printf("Language removed. Language: %s%n",
                              deletedLanguage.get());
          }
        }
      }
      else {
        boolean isOfficial = false;
        Float percentage = 0.1f;
        Optional<CountryLanguageEntity> addedLanguage = 
            worldDao.addLanguage(code, languageCode, 
                                 isOfficial, percentage);
        if (addedLanguage.isPresent()) {
          System.out.printf("Language added. Language: %s%n",
                            addedLanguage.get());
        }
      }

      System.out.printf("Enter a new capital for %s (mosinee:%d): %n",
                        code,
                        (addedCity.isPresent()) 
                          ? addedCity.get().getId()
                          : 0
                        );
      try {
        Integer cityId = Integer.parseInt(input.nextLine());
        Optional<CityEntity> city = worldDao.getCity(cityId);
        if (city.isPresent()) {
          Optional<CountryEntity> updated = worldDao.setCapital(code, cityId);
          if (updated.isPresent()) {
            System.out.println("Capital updated.");
            System.out.println(updated.get());
          }
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid city specified.");
      }
    }
    else {
      System.out.printf("Requested country was not found. Code: %s%n", code);
    }
  }
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }
}
