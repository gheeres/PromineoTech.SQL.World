package world.dao;

import java.util.List;
import world.entity.CityEntity;

public interface CityDao {
  /**
   * Gets all of the cities for the specified country.
   * @param code The unique id of the country.
   * @return The collection of cities.
   */
  List<CityEntity> getCitiesForCountry(String code);
}
