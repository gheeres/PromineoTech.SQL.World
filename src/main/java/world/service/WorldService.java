package world.service;

import java.util.List;
import java.util.Optional;

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
   * Retrieve a country by it's unique ISO 3166-1 alpha-3 or ISO 3166-1 alpha-2 unique identifier.
   * @param countryCode The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return An optional containing the requested country.
   */
  Optional<CountryEntity> getCountryByCode(String countryCode);
  
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
  
  /**
   * Updates or modifies an existing country.
   * @param countryCode The unique id of the country.
   * @param input The update values to change.
   * @return The updated country if successful, otherwise returns an empty optional.
   */
  Optional<CountryEntity> updateCountry(String countryCode, CountryEntity input);
  
  /**
   * Removes a country by it's unique id.
   * @param countryCode The unique id of the country.
   * @return The removed country if successful, otherwise and empty optional.
   */
  Optional<CountryEntity> deleteCountry(String countryCode);
}
