package world.service;

import java.util.List;

import world.entity.CityEntity;
import world.entity.CountryEntity;

/**
 * Business / logic layer for application.
 */
public interface WorldService {
  /**
   * Initializes or resets the service data.
   */
  void initialize();
  
  /**
   * Gets all of the available countries.
   * @return The collection of countries.
   */
  List<CountryEntity> getAllCountries();

  /**
   * Gets all of the available countries for a continent.
   * @param continent
   * @return The collection of countries located in the continent.
   */
  List<CountryEntity> getAllCountries(String continent);
  
  /**
   * Gets all cities. 
   * @return The collection of cities.
   */
  List<CityEntity> getAllCities();
  
  /**
   * Gets all cities for a particular country. 
   * @param countryCode The unique id of the country.
   * @return The collection of cities.
   */
  List<CityEntity> getAllCities(String countryCode);
}
