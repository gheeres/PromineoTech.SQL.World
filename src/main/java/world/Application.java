package world;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;
import world.service.DefaultWorldService;
import world.service.WorldService;

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
    
    //CountryMySqlDao countryDao = new CountryMySqlDao();
    CountryDao countryDao = new CountryMySqlDao(); // CountryArrayListDao();
    CityMySqlDao cityDao = new CityMySqlDao();

    WorldService service = new DefaultWorldService(countryDao, cityDao);
    Stream<CountryEntity> countries = service.getAllCountries("South America");
    //for(CountryEntity country: countries.collect(Collectors.toList())) {
    //  System.out.println(country.toString());
    ///}
    countries.forEach(country -> System.out.println(country.toString()));
    
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
    System.out.println("Enter the id of the city you want to update:");
    int id = input.nextInt();
    input.nextLine();
    CityEntity cityToEdit = cityDao.getById(id);
    if (cityToEdit != null) {
      System.out.printf("New Name: [%s]%n", cityToEdit.getName());
      String name = input.nextLine();
      if (! name.isEmpty()) {
        cityToEdit.setName(name);
        CityEntity updatedCity = cityDao.update(cityToEdit.getId(), cityToEdit);
        if (updatedCity != null) {
          System.out.printf("[%d] %s%n", updatedCity.getId(), updatedCity.getName());
        }
      }
    }
    else {
      System.out.printf("Requested city (%d) was not found.%n", id);
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
    
    /*
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
