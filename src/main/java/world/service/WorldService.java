package world.service;

import world.entity.CountryEntity;

public interface WorldService {
  /**
   * Creates a new country.
   * @param input The new country to create.
   * @return The created country if successful, otherwise returns null.
   */
  CountryEntity createCountry(CountryEntity input);
}
