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
    service = new DefaultWorldService(new CountryMySqlDao(), new CityMySqlDao());
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    System.out.println("What continent do you want to display the countries for? [North America]");
    System.out.println("Leave blank to display all countries.");
    String continent = input.nextLine();
    // (boolean) ? <true> : <false>
    List<CountryEntity> countries = ((continent == null) || continent.isEmpty()) 
       ? service.getAllCountries() 
       : service.getAllCountries(continent);
    for(CountryEntity country : countries) {
      System.out.printf("[%s] %s (%s)%n", country.getCode(), country.getName(), country.getContinent());
    }
    
    System.out.println("What country do you want to get more information about? [USA]");
    String countryCode = input.nextLine();
    CountryEntity existing = service.getCountryByCode(countryCode);
    if (existing != null) {
      System.out.printf("[%s/%s] %s (%d)%n", existing.getCode(), existing.getCode2(),
                                             existing.getName(), existing.getPopulation());

      System.out.printf("Let's create a new city for %s!%n", existing.getName());
      System.out.println("What is the name of the city?");
      String cityName = input.nextLine();
      System.out.println("What is the population of the city?");
      Long population = input.nextLong();
      input.nextLine();
      
      CityInputEntity input = new CityInputEntity(countryCode, cityName);
      input.setPopulation(population);
      CityEntity newCity = service.createCity(input);
    }
    
    System.out.println("[End]");
  }
}
