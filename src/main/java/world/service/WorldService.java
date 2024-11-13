package world.service;

import java.util.List;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;
import world.entity.CountryInputEntity;

public interface WorldService {
  /**
   * Gets all of the available countries.
   * @return The collection of countries.
   */
  List<CountryEntity> getAllCountries();
  
  /**
   * Gets all of the available countries for a continent.
   * @param continent The continent to filter by.
   * @return The collection of countries located in the continent.
   */
  List<CountryEntity> getAllCountries(String continent);
  
  /**
   * Retrieve a country by it's unique ISO 3166-1 alpha-3 or ISO 3166-1 alpha-2 unique identifier.
   * @param countryCode The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return An optional containing the requested country.
   */  
  CountryEntity getCountryByCode(String code);
  
  /**
   * Creates a new country.
   * @param input The new country information.
   * @return The created country if successful, otherwise returns null.
   */
  CountryEntity createCountry(CountryInputEntity input);
  
  /**
   * Updates or changes the name of the specified country.
   * @param code The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @param name The new name of the country.
   * @return The updated country if successful, otherwise returns null.
   */
  CountryEntity setCountryName(String code, String name);
  
  /**
   * Removes the specified country.
   * @param code The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return The deleted country if successful, otherwise returns null.
   */
  CountryEntity deleteCountry(String code);
  
  /**
   * Retrieves all of the cities for the specified country.
   * @param code The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return The cities.
   */
  List<CityEntity> getAllCities(String code);
  
  /**
   * Creates a new city.
   * @param input The new city information.
   * @return The created city if successful, otherwise returns null.
   */
  CityEntity createCity(CityInputEntity input);  
}
