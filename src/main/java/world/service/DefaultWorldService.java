package world.service;

import java.util.ArrayList;
import java.util.List;
import world.dao.CityDao;
import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
import world.dao.LanguageDao;
import world.dao.WeatherDao;
import world.entity.CountryEntity;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  private CityDao cityDao;
  private LanguageDao languageDao;
  private WeatherDao weatherDao;
  
  public DefaultWorldService(CountryDao countryDao, CityDao cityDao, 
                             LanguageDao languageDao, WeatherDao weatherDao) {
    this.countryDao = countryDao;
    this.cityDao = cityDao;
    this.languageDao = languageDao;
    this.weatherDao = weatherDao;
  }
  
  @Override
  public List<CountryEntity> getAllCountries() {
    // List<CountryEntity> countries = countryDao.all();
    // return countries;
    return countryDao.all();
  }

  @Override
  public List<CountryEntity> getAllCountries(String continent) {
    return new ArrayList<>();
  }

  @Override
  public CountryEntity getCountryByCode(String code) {
    return null;
  }
}
