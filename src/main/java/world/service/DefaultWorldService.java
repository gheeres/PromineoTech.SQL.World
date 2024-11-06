package world.service;

import java.util.ArrayList;
import java.util.List;
import world.dao.CityDao;
import world.dao.CountryDao;
import world.dao.LanguageDao;
import world.dao.WeatherDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.entity.CountryInputEntity;
import world.exception.DbException;

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
    if ((code == null) || (code.isEmpty())) {
      return null;
    }
    
    return countryDao.getByCode(code);
  }
  
  public CountryEntity createCountry(CountryInputEntity input) {
    if ((input == null) || (! input.isValid())) {
      throw new DbException("Invalid or incomplete country provided.");   
    }
    
    CountryEntity existing = countryDao.getByCode(input.getCode());
    if (existing != null) {
      throw new DbException("Specified country already exists.");
    }
    
    return countryDao.save(input);
  }

  @Override
  public CountryEntity setCountryName(String code, String name) {
    if ((name == null) || (name.isEmpty())) {
      throw new DbException("Invalid or empty country name provided. Name is required.");   
    }
    
    CountryEntity existing = countryDao.getByCode(code);
    if (existing == null) {
      throw new DbException("Specified country does not exist.");
    }
    
    return countryDao.updateName(code, name);
  }

  @Override
  public CountryEntity deleteCountry(String code) {
    CountryEntity existing = countryDao.getByCode(code);
    if (existing == null) {
      throw new DbException("Specified country does not exist.");
    }
    
    if (countryDao.delete(existing.getCode())) {
      return existing;
    }
    return null;
  }

  @Override
  public List<CityEntity> getAllCities(String code) {
    if ((code == null) || (code.isEmpty())) {
      return new ArrayList<>();
    }
    
    return cityDao.all(code);
  }
}
