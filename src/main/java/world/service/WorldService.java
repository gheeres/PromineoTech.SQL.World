package world.service;

import java.util.List;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.entity.InputCityEntity;

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
 
  /**
   * Retrieves a city by it's unique id.
   * @param id The unique id of the city.
   * @return The city if found, otherwise returns null.
   */
  CityEntity getCityById(int id);
  
  /**
   * Creates a new city in the country.
   * @param input The new city.
   * @param country The country that the city is located in.
   * @return The created city if successful, otherwise returns null.
   */
  CityEntity createCity(InputCityEntity input, CountryEntity country);
  
  /**
   * Removes the specified city from the database.
   * @param existing The city to remove.
   * @return The removed city.
   */
  CityEntity removeCity(CityEntity existing);
  
  /**
   * Updates or changes the name of the city.
   * @param existing The existing city to be modified.
   * @param name The new name.
   * @return The updated or final name.
   */
  String changeCityName(CityEntity existing, String name);
}
