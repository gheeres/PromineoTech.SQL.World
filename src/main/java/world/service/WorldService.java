package world.service;

import java.util.List;
import world.entity.CityEntity;
import world.entity.CountryEntity;

public interface WorldService {
  /**
   * Gets all of the countries.
   * @return The collection of countries.
   */
  List<CountryEntity> getAllCountries();
  
  /**
   * Retrieves the country by the unique id.
   * @param code The unique code for the country.
   * @return The country if found, otherwise returns null.
   */
  CountryEntity getCountryByCode(String code);
  
  /**
   * Searches for countries that match the specified name.
   * @param name The name to search for.
   * @return The collection of countries that match.
   */
  List<CountryEntity> searchCountries(String name);
  
  /**
   * Retrieves all of the cities for the specified country.
   * @param code The unique code for the country.
   * @return The list of cities that were found.
   */
  List<CityEntity> getCitiesForCountry(String code);
}
