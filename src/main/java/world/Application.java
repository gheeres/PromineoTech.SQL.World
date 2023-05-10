package world;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import world.dao.CityDao;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
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
    //CountryDao countryDao = new CountryMySqlDao();
    //CityDao cityDao = new CityMySqlDao();
    
    //this.service = new DefaultWorldService(countryDao, cityDao);
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    System.out.println("[Start]");

    try {
      System.out.println("Opening connection to database.");
      Connection connection = new CountryMySqlDao().getConnection();
      connection.close();
      System.out.println("Connection closed.");
    } catch (SQLException e) {
      System.out.println("Failed to open database connection.");
    }
    
    //List<CountryEntity> countries = service.getAllCountries();
    //for(CountryEntity country : countries) {
    //  List<CityEntity> cities = service.getCitiesForCountry(country.getCountryCode());
    //  for(CityEntity city : cities) {
    //  }
    //}
    
    System.out.println("[End]");
  }
}
