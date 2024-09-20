package world.dao;

import java.util.List;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.entity.InputCityEntity;

public interface CityDao {
  /**
   * Gets all of the cities for the specified country.
   * @param code The unique id of the country.
   * @return The collection of cities.
   */
  List<CityEntity> getCitiesForCountry(String code);
  
  /**
   * Retrieves a city by its unique id.
   * @param id The unique id of the city.
   * @return The city if found, otherwise returns null;
   */
  CityEntity getById(int id);

  /**
   * Creates a new city in the country.
   * @param input The new city.
   * @param countryCode The unique code for the country.
   * @return The created city if successful, otherwise returns null.
   */
  CityEntity createCity(InputCityEntity input, String countryCode);  
  
  /**
   * Removes the specified city from the database.
   * @param existing The unique id of the city to remove.
   * @return The removed city.
   */
  CityEntity deleteCity(int id);  
  
  /**
   * Updates the city to the specified values.
   * @param id The unique id of the city.
   * @param name The new or existing name of the city.
   * @param population The new or existing population of the city.
   * @return The updated or modified city.
   */
  CityEntity updateCity(int id, String name, int population);  
}
