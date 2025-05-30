package world.service;

import world.dao.CountryDao;
import world.dao.CountryMySqlDao;
import world.entity.CountryEntity;

public class DefaultWorldService implements WorldService {
  private CountryDao countryDao;
  
  public DefaultWorldService() {
    countryDao = new CountryMySqlDao();    
  }
  
  @Override
  public CountryEntity createCountry(CountryEntity input) {
    if (input == null) {
      return null;
    }
    
    // Checks to see if the input has all the necessary fields.
    //if ((input.getCode() == null) || (input.getName() == null)) {
    //  return null;
    //}
    if (input.isValid()) {
      return null;
    }
    
    CountryEntity country = countryDao.create(input);
    return country;
  }
}
