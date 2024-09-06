package world;

import java.util.List;
import java.util.Scanner;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
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
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    System.out.println("Enter the continent: ");
    String continent = input.nextLine();
    
    CountryDao countryDao = new CountryMySqlDao();
    //List<CountryEntity> countries = countryDao.all();
    List<CountryEntity> countries = countryDao.all(continent);
    for(CountryEntity country: countries) {
      // [US] United States of America (10000000)
      System.out.printf("[%s] %s (%d)%n", country.getCode2(), country.getName(), country.getPopulation());
    }
    System.out.println("Count: " + countries.size());
    
    
    System.out.println("Enter Country (ISO-9660 / US,USA,etc.): ");
    String countryCode = input.nextLine();
    CountryEntity country = countryDao.getByCode(countryCode);
    if (country != null) {
      System.out.printf("[%s] %s (%d)%n", country.getContinent(), country.getName(), country.getPopulation());
    }
    else {
      //System.out.println("Country '" + countryCode + "' not found.");
      System.out.printf("Country '%s' not found.%n", countryCode);
    }
    
    System.out.println("[End]");
  }
}
