package world.dao;

import java.util.ArrayList;
import java.util.List;
import world.entity.CountryEntity;

public class CountryArrayListDao implements CountryDao {
  private List<CountryEntity> entities = new ArrayList<>();
  
  public CountryArrayListDao() {
    CountryEntity country = new CountryEntity("TST", "Test Country");
    country.setPopulation(1);
    entities.add(country);
  }
  
  @Override
  public List<CountryEntity> all() {
    return entities;
  }
}
