package world;

import java.util.Scanner;
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
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    WorldService service = new DefaultWorldService();
    CountryEntity countryToCreate = new CountryEntity("USA", "United States of America");
    countryToCreate.setCode2("US");
    countryToCreate.setPopulation(320000000L);
    
    CountryEntity country = service.createCountry(countryToCreate);
    
    
    System.out.println("[End]");
  }
}
