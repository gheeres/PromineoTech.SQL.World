package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CityDao;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.service.DefaultWorldService;
import world.service.WorldService;

public class Application {
  private WorldService service;
  private Scanner input = new Scanner(System.in);
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }

  public Application() {
    CountryDao countryDao = new CountryMySqlDao();
    CityDao cityDao = new CityMySqlDao();
    service = new DefaultWorldService(countryDao, cityDao); // new CountryMySqlDao();
  }
  
  /**
   * Prompts the user to entry or select a country.
   * @return The country that was selected.
   */
  public String promptForCountry() {
    System.out.println("What country do you want to view the cities for? Enter to view all the countries.");
    String countryCode = input.nextLine();
    if (countryCode.isEmpty()) {
      List<CountryEntity> countries = service.getAllCountries();
      for(CountryEntity country : countries) {
        System.out.println(country);
      }
      return promptForCountry();
    }
    
    return countryCode;
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");

    String countryCode = promptForCountry();
    if (! countryCode.isEmpty()) {
      CountryEntity country = service.getCountryByCode(countryCode);
      if (country != null) {
        System.out.println(country);

        List<CityEntity> cities = service.getCitiesForCountry(country.getCode2());
        for(CityEntity city : cities) {
          System.out.printf(" - %s%n", city);
        }
      }
    }
    
    System.out.println("[End]");
  }
}
