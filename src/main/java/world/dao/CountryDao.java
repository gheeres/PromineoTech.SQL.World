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
   * @param code The unique / internal id of the country.
   * @return The country if found, otherwise returns an empty value.
   */  
  CountryEntity getByCode(String code);
}
