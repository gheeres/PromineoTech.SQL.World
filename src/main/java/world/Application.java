package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CityMySqlDao;
import world.dao.CountryMySqlDao;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;

public class Application {
  private Scanner input = new Scanner(System.in);
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }

  public Application() {
  }
  
  public CityInputEntity getNewCity() {
    System.out.println("What is the name of the city?");
    String name = input.nextLine();
    System.out.println("What is the population of the city?");
    Integer population = input.nextInt();
    
    CityInputEntity input = new CityInputEntity(name);
    input.setPopulation(population);
    return input;
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    CountryMySqlDao countryDao = new CountryMySqlDao();
    CityMySqlDao cityDao = new CityMySqlDao();

    /*
    System.out.println("Enter the id of the city you want to delete:");
    int id = input.nextInt();
    CityEntity deletedCity = cityDao.delete(id);
    if (deletedCity != null) {
      System.out.printf("!!! DELETED !!! - [%06d] %s (%d)%n", 
                        deletedCity.getId(), deletedCity.getName(), deletedCity.getPopulation());  
    }
    // */
    
    /*
    CityInputEntity input = getNewCity();
    CityEntity city = cityDao.add(input);
    if (city != null) {
      System.out.printf("[%06d] %s (%d)%n", city.getId(), city.getName(), city.getPopulation());  
    }
    else {
      System.out.println("ERR: Failed to create city.");
    }
    // */
    
    // /*
    System.out.println("Enter the code for the country: [*]");
    String request = input.nextLine();
    if ((request == "*") || (request.isEmpty())) {
      List<CountryEntity> countries = countryDao.all();
      for(CountryEntity country : countries) {
        System.out.printf("[%s] %s%n", country.getCode(), country.getName());
      }
    }
    else {
      CountryEntity country = countryDao.getById(request);
      if (country != null) {
        System.out.printf("[%s] %s%n", country.getCode(), country.getName());
        List<CityEntity> cities = cityDao.all(country.getCode());
        for(CityEntity city : cities) {
          System.out.printf("  [%06d] %s (%d)%n", city.getId(), city.getName(), city.getPopulation());
        }
      }
      else {
        System.out.printf("Country (%s) was not found.%n", request);
      }
    }
    // */
    
    System.out.println("[End]");
  }
}
