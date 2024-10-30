package world.service;

import java.util.List;
import world.dao.CityDao;
import world.dao.CountryDao;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  private CityDao cityDao;
  
  public DefaultWorldService(CountryDao countryDao, CityDao cityDao) {
    this.countryDao = countryDao;
    this.cityDao = cityDao;
  }
  
  @Override
  public List<CountryEntity> getAllCountries() {
    //List<CountryEntity> countries = countryDao.all();
    //return countries;
    return countryDao.all();
  }
  

  @Override
  public List<CountryEntity> getAllCountries(String continent) {
    if ((continent == null) || (continent.isEmpty())) {
      return getAllCountries();
    }

    return countryDao.all(continent);
  }

  @Override
  public CountryEntity getCountryByCode(String code) {
    if ((code == null) || (code.isEmpty())) {
      return null;
    }
    
    return countryDao.getByCode(code);
  }

  @Override
  public CityEntity createCity(CityInputEntity input) {
    if ((input == null) || (! input.isValid())) {
      return null;
    }
        
    return cityDao.create(input);
  }
}
