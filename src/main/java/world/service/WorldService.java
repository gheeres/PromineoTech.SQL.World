package world.service;

import java.util.List;
import java.util.stream.Stream;
import world.entity.CityEntity;
import world.entity.CountryEntity;

/**
 * Business / logic layer for application.
 */
public interface WorldService {
  /**
   * Gets all of the available countries.
   * @return The collection of countries.
   */
  Stream<CountryEntity> getAllCountries();
  
  /**
   * Gets all of the available countries for a continent.
   * @param continent The name of the continent.
   * @return The collection of countries.
   */
  Stream<CountryEntity> getAllCountries(String continent);
  
  /**
   * Retrieve a country by it's unique ISO 3166-1 alpha-3 or ISO 3166-1 alpha-2 unique identifier.
   * @param countryCode The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return An optional containing the requested country.
   */
  CountryEntity getCountryByCode(String countryCode);
  
  /**
   * Gets all cities for a particular country. 
   * @param countryCode The unique id of the country.
   * @return The collection of cities.
   */
  List<CityEntity> getCitiesForCountry(String countryCode);
  
  /**
   * Removes the specified city.
   * @param cityId The unique id of the city.
   * @return The removed city if successful, null if otherwise.
   */
  CityEntity deleteCity(int cityId);
}
