package world.dao;

import java.util.List;

import world.entity.CountryEntity;

public interface CountryDao {
  /**
   * Retrieves all of the countries.
   * @return All of the available countries.
   */
  List<CountryEntity> all();

  /**
   * Retrieves all of the countries for the specified continent.
   * @param continent The continent.
   * @return The countries if found, otherwise returns an empty list.
   */
  List<CountryEntity> all(String continent);

  /**
   * Retrieves the requested country by it's unique identifier.
   * @param countryCode The unique / internal id of the country.
   * @return The country if found, otherwise returns an empty value.
   */
  //Optional<CountryEntity> getByCode(String countryCode);
  
  /**
   * Adds the specified country information.
   * @param input The country to create / add.
   * @return The created country if successful, false if otherwise.
   */
  //Optional<CountryEntity> add(CountryInputEntity input);

  /**
   * Updates or modifies the specified country information.
   * @param countryCode The unique / internal id of the country.
   * @param input The country data to modify or update.
   * @return The modified or updated country if successful, false if otherwise.
   */
  //Optional<CountryEntity> update(String countryCode, CountyInputEntity input);

  /**
   * Removes the specified country from the database.
   * @param countryCode The unique / internal id of the country.
   * @return The country if removed, otherwise returns an empty value.
   */
  //Optional<CountryEntity> delete(String countryCode);
}
