package world.dao;

import java.util.List;
import java.util.stream.Stream;
import world.entity.CityEntity;
import world.entity.CityInputEntity;

public interface CityDao {
  /**
   * Retrieves all of the cities for a specified country.
   * @param code The unique id of the country.
   * @return The collection of cities located in the specified country.
   */
  Stream<CityEntity> all(String code);

  /**
   * Create a new city.
   * @param input The new city information.
   * @return The created city if successful, null if otherwise.
   */
  CityEntity save(CityInputEntity input);
}
