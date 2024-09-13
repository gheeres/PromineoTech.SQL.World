package world.service;

import java.util.List;
import world.dao.CityDao;
import world.dao.CountryDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.exception.CountryNotFoundException;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  private CityDao cityDao;
  
  public DefaultWorldService(CountryDao countryDao, CityDao cityDao) {
    //countryDao = new CountryMySqlDao();
    this.countryDao = countryDao;
    this.cityDao = cityDao;
  }
  
  @Override
  public List<CountryEntity> getAllCountries() {
    List<CountryEntity> countries = countryDao.all();
    return countries;
  }

  @Override
  public CountryEntity getCountryByCode(String code) {
    CountryEntity country = countryDao.getByCode(code);
    if (country == null) {
      throw new CountryNotFoundException(code);
    }

    return country;
  }

  @Override
  public List<CountryEntity> searchCountries(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<CityEntity> getCitiesForCountry(String code) {
    List<CityEntity> cities = cityDao.getCitiesForCountry(code);
    return cities;
  }
}
