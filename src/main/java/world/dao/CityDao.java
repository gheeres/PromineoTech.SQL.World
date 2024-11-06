package world.dao;

import java.util.List;
import world.entity.CityEntity;

public interface CityDao {
  /**
   * Retrieves all of the cities for a specified country.
   * @param code The unique id of the country.
   * @return The collection of cities located in the specified country.
   */
  List<CityEntity> all(String code);
}
