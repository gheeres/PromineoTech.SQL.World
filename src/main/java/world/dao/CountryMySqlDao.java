package world.dao;

import java.util.ArrayList;
import java.util.List;
import world.entity.CountryEntity;

public class CountryMySqlDao implements CountryDao {

  @Override
  public List<CountryEntity> all() {
    System.out.println("I'm the repository... I need to go get the data. But I don't know how yet...");
    return new ArrayList<>();
  }

  @Override
  public CountryEntity getByCode(String code) {
    return null;
  }

}
