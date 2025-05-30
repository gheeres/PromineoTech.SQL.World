package world.dao;

import world.entity.CountryEntity;

public interface CountryDao {
  /**
   * Creates the specified country 
   * @param country The country to create
   * @return The country if created, null if otherwise
   */
  CountryEntity create(CountryEntity country);
}
