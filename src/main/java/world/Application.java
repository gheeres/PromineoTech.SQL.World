package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CountryMySqlDao;
import world.entity.CountryEntity;
import world.entity.CountryInputEntity;
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
    service = new DefaultWorldService(new CountryMySqlDao(), null, null, null);
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
    }
    
    
    //List<CountryEntity> countries = service.getAllCountries();
    //for(CountryEntity country : countries) {
    //  System.out.printf("[%s] %s%n", country.getCode(), country.getName());
    //}
    
    System.out.println("[End]");
  }
}
