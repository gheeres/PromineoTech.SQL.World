package world;

import java.util.List;
import java.util.Scanner;
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
    service = new DefaultWorldService(new CountryMySqlDao(), null, null, null);
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");
    
    List<CountryEntity> countries = service.getAllCountries();
    for(CountryEntity country : countries) {
      System.out.printf("[%s] %s%n", country.getCode(), country.getName());
    }
    
    System.out.println("[End]");
  }
}
