package world.service;

import java.util.List;
import world.dao.CountryDao;
import world.entity.CountryEntity;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  
  public DefaultWorldService(CountryDao countryDao) {
    this.countryDao = countryDao;
  }
  
  @Override
  public List<CountryEntity> getAllCountries() {
    //List<CountryEntity> countries = countryDao.all();
    //return countries;
    return countryDao.all();
  }

  @Override
  public CountryEntity getCountryByCode(String code) {
    return null;
  }
}
