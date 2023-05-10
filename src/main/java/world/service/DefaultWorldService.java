package world.service;

import java.util.ArrayList;
import java.util.List;

import world.dao.CityDao;
import world.dao.CountryDao;
import world.dao.WorldMySqlDatabase;
import world.entity.CityEntity;
import world.entity.CountryEntity;

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

  @Override
  public List<CountryEntity> getAllCountries() {
	List<CountryEntity> countries = countryDao.all();
	// Business Logic logic / filtering?
	return countries;
  }

  @Override
  public List<CountryEntity> getAllCountries(String continent) {
	List<CountryEntity> countries = countryDao.all(continent);  
	return countries;
  }

  @Override
  public List<CityEntity> getAllCities() {
   return new ArrayList<CityEntity>();
  }

  @Override
  public List<CityEntity> getAllCities(String countryCode) {
	return new ArrayList<CityEntity>();
  }
}
