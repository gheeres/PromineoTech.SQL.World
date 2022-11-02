package world;

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
    CityEntity city = worldDao.addCity(input);
  }
  
  /**
   * Standard / main entry point for application.
   * @param args
   */
  public static void main(String[] args) {
    new Application().run(args);
  }
}
