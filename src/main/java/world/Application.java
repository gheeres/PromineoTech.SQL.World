package world;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import world.dao.CityDao;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
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
    CountryDao countryDao = new CountryMySqlDao();
    CityDao cityDao = new CityMySqlDao();
    
    this.service = new DefaultWorldService(countryDao, cityDao);
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");

    String continent = "South America";
    List<CountryEntity> countries = service.getAllCountries(continent);
    for(CountryEntity country : countries) {
      System.out.printf("[%s] %s%n", country.getCode(), country.getName());
      // List<CityEntity> cities = service.getCitiesForCountry(country.getCountryCode());
      // for(CityEntity city : cities) {
      // }
    }
    System.out.println("----------------------------------");
    
    Optional<CountryEntity> optMyIsland = service.getCountryByCode("ZZZ");
    if (optMyIsland.isPresent()) {
      CountryEntity myIsland = optMyIsland.get();  	
      System.out.printf("[%s] %s%n", myIsland.getCode(), myIsland.getName());
      
      System.out.println("> What do you want to name the island?");
      String name = input.nextLine();
      if (! name.isBlank()) {
    	//CountryEntity input = new CountryEntity(myIsland.getCode())
    	//		                                 .setCode2(myIsland.getCode2())
    	//		                                 .setName(name);
    	CountryEntity input = new CountryEntity(myIsland)
    			                               .setName(name);
    	Optional<CountryEntity> updateResult = service.updateCountry(myIsland.getCode(), input);
    	if (updateResult.isPresent()) {
    	  System.out.printf("Updated existing country. Changed name from '%s' to '%s'.%n",
    			            myIsland.getName(), updateResult.get().getName());	
    	}
      }
    }
    
    System.out.println("----------------------------------");
    System.out.println("> What country do you want to remove? (ISO 3166 / XXX or XX)");
    String countryCode = input.nextLine();
    if (! countryCode.isBlank()) {
      Optional<CountryEntity> optDeletedCountry = service.deleteCountry(countryCode);
      if (optDeletedCountry.isPresent()) {
        CountryEntity deletedCountry = optDeletedCountry.get();
    	System.out.printf("Removed country (%s). %s doesn't exist anymore.%n",
    			          deletedCountry.getCode(), deletedCountry.getName());  
      }
    }
    
    System.out.println("[End]");
  }
}
