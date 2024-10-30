package world.service;

import java.util.List;
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
}
