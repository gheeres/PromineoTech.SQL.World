package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.entity.CountryEntity;
import world.exception.DbException;

public class CountryMySqlDao extends MySqlDao {
  /**
   * Serialized a ResultSet into an instance of CountryEntity
   * @param rs The ResultSet to convert.
   * @return The CountryEntity instance.
   * @throws SQLException
   */
  private CountryEntity toCountryEntity(ResultSet rs) throws SQLException {
    CountryEntity country = new CountryEntity(rs.getString("country_code"),
                                              rs.getString("country_name"));
    country.setContinent(rs.getString("continent"));
    country.setPopulation(rs.getInt("country_population"));   
    
    return country;
  }
  
  /**
   * Retrieves all of the countries.
   * @return All of the available countries.
   */
  public List<CountryEntity> all() {
    final String sql = "SELECT * FROM country ORDER BY continent, country_name;";
    
    try (Connection connection = getConnection()){
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        System.out.printf("SQL: %s%n", statement.toString());
       
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CountryEntity> countries = new ArrayList<>();
          while (rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if (country != null) {
              countries.add(country);
            }
          }
          return countries;
        }
      }
    }
    catch (SQLException exception) {
      throw new DbException(String.format("A unhandled error occured. Error: %s", 
                                          exception.getMessage()));
    }
  }
  
  /**
   * Retrieves the requested country by it's unique identifier.
   * @param countryCode The unique / internal id of the country.
   * @return The country if found, otherwise returns an null value.
   */
  public CountryEntity getById(String countryCode) {
    final String sql = "SELECT * FROM country WHERE country_code = ?";
    //final String sql = "SELECT * FROM country WHERE country_code = '" + countryCode + "'"; // BAD!!!
   
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, countryCode);
        
        System.out.printf("SQL: %s%n", statement.toString());

        try (ResultSet rs = statement.executeQuery()) {
          while (rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if (country != null) {
              return country;
            }
          }
          return null;
        }
      }
    }
    catch (SQLException exception) {
      throw new DbException(String.format("A unhandled error occured. Error: %s", 
                                          exception.getMessage()));
    }    
  }
}
