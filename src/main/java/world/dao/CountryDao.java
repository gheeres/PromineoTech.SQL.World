package world.dao;

import java.util.List;
import world.entity.CountryEntity;
import world.entity.CountryInputEntity;

public interface CountryDao {
  /**
   * Retrieves all of the countries.
   * @return All of the available countries.
   */
  List<CountryEntity> all();
  
  /**
   * Retrieves the requested country by it's unique identifier.
   * @param code The unique / internal id of the country.
   * @return The country if found, otherwise returns an empty value.
   */
  CountryEntity getByCode(String code);
  
  /**
   * Saves or creates the country in the database.
   * @param input The new country information.
   * @return The created country if successful, false if otherwise.
   */
  CountryEntity save(CountryInputEntity input);
  
  /**
   * Updates or modifies the name of the country.
   * @param code The unique / internal id of the country.
   * @param name The new name.
   * @return The updated country if successful, otherwise returns null.
   */
  CountryEntity updateName(String code, String name);
  
  /**
   * Deletes the requested country.
   * @param code The unique / internal id of the country.
   * @return True if deleted, false if otherwise.
   */
  boolean delete(String code);
}
