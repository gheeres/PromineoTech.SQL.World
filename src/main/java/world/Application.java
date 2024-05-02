package world;

import java.util.List;
import java.util.Scanner;
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
    
    CountryMySqlDao countryDao = new CountryMySqlDao();

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
      }
      else {
        System.out.printf("Country (%s) was not found.%n", request);
      }
    }
    
    System.out.println("[End]");
  }
}
