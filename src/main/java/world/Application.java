package world;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import world.dao.WorldMySqlDao;
import world.entity.CountryEntity;

public class Application {
  public Application() {
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    WorldMySqlDao worldDao = new WorldMySqlDao();
    worldDao.createSchema()
            .initializeData();
    
    /*
    CityInputEntity input = new CityInputEntity()
        .setCountry("USA")
        .setName("Mosinee")
        .setLatitude(44.7930f)
        .setLongitude(-89.7032f)
        .setPopulation(3988);
    CityEntity city = worldDao.addCity(input);
    */
    
    List<CountryEntity> countries = worldDao.getCountries();
    for(CountryEntity country: countries) {
      System.out.println(country);
    }
    
    System.out.println("Enter the country (XXX): ");
    Scanner input = new Scanner(System.in);
    String code = input.nextLine();
    Optional<CountryEntity> optionalCountry = worldDao.getCountryByCode(code);
    if (optionalCountry.isPresent()) {
      CountryEntity country = optionalCountry.get();
      System.out.println(country);
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
