package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CityDao;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.entity.InputCityEntity;
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
   * Prompts the user with a yes/no question and retrieves the result.
   * @param prompt The question.
   * @param defaultValue If an empty response, this is the return.
   * @return The answer.
   */
  public boolean promptYesNo(String prompt, boolean defaultValue) {
    System.out.println(prompt);
    String answer = input.nextLine();
    if ((answer == null) || (answer.isBlank())) {
      return defaultValue;
    }
    
    return (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"));
  }
  
  
  /**
   * Prompts the user for new city information.
   * @param country The country associated with the city.
   * @return The new city information.
   */
  public InputCityEntity promptForNewCity(CountryEntity country) {
    System.out.println("Name: ");
    String name = input.nextLine();
    System.out.println("Population: ");
    int population = input.nextInt();
    
    InputCityEntity city = new InputCityEntity(name)
                               .setPopulation(population);
    return city;
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
      
      if (promptYesNo("Do you want to add a new city to " + country.getName() + "? [y/N]", false)) {
        InputCityEntity input = promptForNewCity(country);
        if ((input != null) && input.isValid()) {
          CityEntity city = service.createCity(input, country);
          if (city != null) {
            System.out.println("City created. " + city.toString());
          }
          else {
            System.out.println("City NOT created. Error occured.");
          }
        }
      }
      else if (promptYesNo("Do you want to modify an existing city? [y/N]", false)) {
        System.out.println("What is the ID of the city you want to modify?");
        int cityId = input.nextInt();
        input.nextLine();
        
        CityEntity existing = service.getCityById(cityId);
        if (existing != null) {
          System.out.println("New name: [" + existing.getName() + "]");
          String name = input.nextLine();
          if ((name != null) && (! name.isEmpty())) {
            String modifiedName = service.changeCityName(existing, name);
          }
        }
        else {
          System.out.println("City was not found.");
        }
      }
      else if (promptYesNo("Do you want to delete a city from " + country.getName() + "? [y/N]", false)) {
        System.out.println("What is the ID of the city you want to delete?");
        int cityId = input.nextInt();
        CityEntity existing = service.getCityById(cityId);
        if (existing != null) {
          if (service.removeCity(existing) != null) {
            System.out.println("City deleted. " + existing.toString());
          }
          else {
            System.out.println("Unable to delete city. Error occured.");
          }
        }
        else {
          System.out.println("City was not found.");
        }
      }
    }
    
    System.out.println("[End]");
  }
}
