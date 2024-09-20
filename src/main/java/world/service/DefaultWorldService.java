package world.service;

import java.util.List;
import world.dao.CityDao;
import world.dao.CountryDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;
import world.entity.InputCityEntity;
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

  @Override
  public CityEntity createCity(InputCityEntity input, CountryEntity country) {
    if ((input != null) && input.isValid()) { // input?.isValid()
      CityEntity result = cityDao.createCity(input, country.getCode2());
      return result;
    }
    return null;
  }

  @Override
  public CityEntity getCityById(int id) {
    if (id > 0) {
      CityEntity city = cityDao.getById(id);
      return city;
    }
    return null;
  }

  @Override
  public CityEntity removeCity(CityEntity existing) {
    if (existing != null) {
      CityEntity result = cityDao.deleteCity(existing.getId());
      return result;
    }
    return null;
  }

  @Override
  public String changeCityName(CityEntity existing, String name) {
    if ((existing == null) || (name == null) || (name.isBlank())) {
      return null;
    }
    
    CityEntity modified = cityDao.updateCity(existing.getId(), name, existing.getPopulation());
    if (modified != null) {
      return modified.getName();
    }
    return null;
  }
}
