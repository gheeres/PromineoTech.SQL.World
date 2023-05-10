package world.service;

import world.dao.CityDao;
import world.dao.CountryDao;
import world.dao.WorldMySqlDatabase;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  private CityDao cityDao;
  
  public DefaultWorldService(CountryDao countryDao, CityDao cityDao) {
    this.countryDao = countryDao;
    this.cityDao = cityDao;
  }
  
  /**
   * Initializes or resets the service data.
   */
  public void initialize() {
    new WorldMySqlDatabase().createSchema()
                            .initializeData();
  }
}
