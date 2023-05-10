package world.dao;

import java.util.Optional;

/**
 * Represents the basic input / output operations available for the city entity.
 */
public interface CityDao {
  /**
   * Retrieves all of the cities.
   * @return All of the available cities.
   */
  //List<CityEntity> all();

  /**
   * Retrieves all of the cities for the specified country code.
   * @param countryCode The unique code for the country.
   * @return The cities if found, otherwise returns an empty list.
   */
  //List<CityEntity> all(String countryCode);

  /**
   * Retrieves the requested city by it's unique identifier.
   * @param cityid The unique / internal id of the city.
   * @return The city if found, otherwise returns an empty value.
   */
  //Optional<CityEntity> getById(int cityId);
  
  /**
   * Adds the specified city information.
   * @param input The city to create / add.
   * @return The created city if successful, false if otherwise.
   */
  //Optional<CityEntity> add(CityInputEntity input);

  /**
   * Adds the specified city information.
   * @param cityid The unique / internal id of the city.
   * @param input The city data to modify or update.
   * @return The modified or updated city if successful, false if otherwise.
   */
  //Optional<CityEntity> update(int cityId, CityInputEntity input);

  /**
   * Removes the specified city from the database.
   * @param cityid The unique / internal id of the city.
   * @return The city if removed, otherwise returns an empty value.
   */
  //Optional<CityEntity> delete(int cityId);
}
