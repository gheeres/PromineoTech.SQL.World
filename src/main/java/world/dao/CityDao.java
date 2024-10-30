package world.dao;

import world.entity.CityEntity;
import world.entity.CityInputEntity;

public interface CityDao {
  /**
   * Creates a new city.
   * @param input The new city information.
   * @return The newly created city if successful, otherwise returns null.
   */
  CityEntity create(CityInputEntity input);
}
