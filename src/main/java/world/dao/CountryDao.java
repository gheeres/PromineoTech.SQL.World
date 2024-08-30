package world.dao;

import java.util.List;
import world.entity.CountryEntity;

public interface CountryDao {
  /**
   * Returns all of the available countries.
   * @return The collection of countries.
   */
  List<CountryEntity> all();
  
  /**
   * Returns all of the countries located in the specified continent.
   * @param continent The name of the continent.
   * @return The collection of countries.
   */
  List<CountryEntity> all(String continent);
  
  /**
   * Retrieves a country by it's unique id.
   * @param code The unique ISO 9660 country code.
   * @return The country if found, otherwise null.
   */
  CountryEntity getByCode(String code);
}
