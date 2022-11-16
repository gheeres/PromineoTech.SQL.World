package world;

import java.util.List;
import world.dao.WorldMySqlDao;
import world.entity.CityEntity;
import world.entity.CityInputEntity;

public class Application {
  public Application() {
  }
  
  /**
   * The instance run/entry point.
   * @param args The optional command line arguments.
   */
  public void run(String[] args) {
    WorldMySqlDao worldDao = new WorldMySqlDao();
    worldDao.createSchema()
            .initializeData();
    
    CityInputEntity input = new CityInputEntity()
        .setCountry("USA")
        .setName("Mosinee")
        .setLatitude(44.7930f)
        .setLongitude(-89.7032f)
        .setPopulation(3988);
    CityEntity addedCity = worldDao.addCity(input);
    
    List<CityEntity> cities = worldDao.getAllCities();
    for(CityEntity city: cities) {
      System.out.println(city);
    }
  }
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }
}
