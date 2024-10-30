package world.service;

import java.util.List;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;

public interface WorldService {
  /**
   * Gets all of the available countries.
   * @return The collection of countries.
   */  
  List<CountryEntity> getAllCountries();

  /**
   * Gets all of the available countries.
   * @param continent The continent to limit results to.
   * @return The collection of countries.
   */  
  List<CountryEntity> getAllCountries(String continent);
  
  /**
   * Retrieve a country by it's unique ISO 3166-1 alpha-3 or ISO 3166-1 alpha-2 unique identifier.
   * @param countryCode The ISO3166 alpha-3 or alpha-2 unique identifier.
   * @return An optional containing the requested country.
   */  
  CountryEntity getCountryByCode(String code);
  
  /**
   * Creates a new city.
   * @param input The new city information.
   * @return The newly created city if successful, otherwise returns null.
   */
  CityEntity createCity(CityInputEntity input);
}
