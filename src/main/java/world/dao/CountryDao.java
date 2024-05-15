package world.dao;

import java.util.List;
import world.entity.CountryEntity;

public interface CountryDao {
  /**
   * Retrieves all of the countries.
   * @return All of the available countries.
   */
  List<CountryEntity> all();
}
