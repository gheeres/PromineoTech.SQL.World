package world.service;

import java.util.List;
import java.util.stream.Stream;
import world.dao.CityMySqlDao;
import world.dao.CountryDao;
import world.entity.CityEntity;
import world.entity.CountryEntity;

public class DefaultWorldService implements WorldService {
  private CityMySqlDao cityDao;
  private CountryDao countryDao;
  
  public DefaultWorldService(CountryDao countryDao, CityMySqlDao cityDao) {
    this.countryDao = countryDao;
    this.cityDao = cityDao;
  }
  
  @Override
  public Stream<CountryEntity> getAllCountries() {
    return countryDao.all().stream();
  }

  @Override
  public Stream<CountryEntity> getAllCountries(String continent) {
    return countryDao.all().stream().filter(c -> c.getContinent().equals(continent));
        // .filter(function(CountryEntity c) {
        //           return c.getContinent().equals(continent) 
        //         });
  }

  @Override
  public CountryEntity getCountryByCode(String countryCode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<CityEntity> getCitiesForCountry(String countryCode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CityEntity deleteCity(int cityId) {
    // TODO Auto-generated method stub
    return null;
  }
}
