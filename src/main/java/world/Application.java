package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CityMySqlDao;
import world.dao.CountryMySqlDao;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;
import world.service.DefaultWorldService;
import world.service.WorldService;

public class Application {
  private Scanner input = new Scanner(System.in);
  private WorldService service;
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }

  public Application() {
    service = new DefaultWorldService(new CountryMySqlDao(), new CityMySqlDao(), null, null);
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    //System.out.println("What is the country name you want to create?");
    //String name = input.nextLine();
    //System.out.println("What is the unique 3 character code for your country? [XXX]");
    //String countryCode = input.nextLine();
    //System.out.println("What is the unique 2 character code for your country? [XX]");
    //String countryCode2 = input.nextLine();
    //System.out.println("What is the population of your country? [1]");
    //Long population = input.nextLong();
    //input.nextLine();
    
    //CountryInputEntity newCountry = new CountryInputEntity(countryCode, countryCode2, name);
    //newCountry.setPopulation(population);
    //CountryEntity createdCountry = service.createCountry(newCountry);
    
    System.out.println("Enter the country code you want to retrieve? [USA]");
    String countryCode = input.nextLine();
    CountryEntity existing = service.getCountryByCode(countryCode);
    if (existing != null) {
      System.out.println(existing.getName());
      
      System.out.println("Would you like to edit the country name? Y/N");
      String editResponse = input.nextLine();
      if ((editResponse != null) && editResponse.equalsIgnoreCase("y")) {
        System.out.printf("Enter the new country name: [%s]%n", existing.getName());
        String newCountryName = input.nextLine();
        if ((newCountryName != null) && (! newCountryName.isEmpty())) {
          CountryEntity updatedCountry = service.setCountryName(existing.getCode(), newCountryName);
          if (updatedCountry != null) {
            System.out.printf("Modified country name. [%s] %s => %s%n",
                              updatedCountry.getCode(), existing.getName(), updatedCountry.getName());
          }
          else {
            System.out.println("Failed to update country name.");
          }
        }
      }
      else {
        System.out.println("Do you want to delete the country? Y/N");
        String deleteResponse = input.nextLine();
        if ("y".contentEquals(deleteResponse)) {
          if (service.deleteCountry(existing.getCode()) != null) {
            System.out.println("Country deleted.");
          }
          else {
            System.out.println("Failed to delete country.");
          }
        }
      }
      
      System.out.println("Here are all the cities for the specified country: ");
      List<CityEntity> cities = service.getAllCities(existing.getCode());
      for(CityEntity city : cities) {
        System.out.printf("  - %s (lat: %f, long: %f): %d / %d (%f%%)%n",
                          city.getName(), city.getLatitude(), city.getLongitude(),
                          city.getPopulation(), city.getCountry().getPopulation(),
                          ((double) city.getPopulation() / city.getCountry().getPopulation()) * 100.0);
      }
      
     
      System.out.println("Do you want to add a new city? Y/N");
      String newCityResponse = input.nextLine();
      if ("y".contentEquals(newCityResponse)) {
        System.out.println("What is the name of the city?");
        String cityName = input.nextLine();
        
        CityInputEntity inputCity = new CityInputEntity(countryCode, cityName);
        inputCity.setLatitude(1.0);
        inputCity.setLongitude(1.0);
        inputCity.setPopulation(1L);
        
        CityEntity newCity = service.createCity(inputCity);
        if (newCity != null) {
          System.out.println("Created new city.");
          System.out.printf(" + %s (%d)%n", newCity.getName(), newCity.getId());
        }
      }
    }
    
    
    //List<CountryEntity> countries = service.getAllCountries();
    //for(CountryEntity country : countries) {
    //  System.out.printf("[%s] %s%n", country.getCode(), country.getName());
    //}
    
    System.out.println("[End]");
  }
}
