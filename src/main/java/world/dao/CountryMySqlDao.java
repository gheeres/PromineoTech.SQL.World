package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.Configuration;
import world.entity.CountryEntity;
import world.exception.DbException;

public class CountryMySqlDao extends MySqlDao implements CountryDao
{
  private CountryEntity toCountryEntity(ResultSet rs) {
    try {
      CountryEntity country = new CountryEntity(rs.getString("country_code"),
                                                rs.getString("country_code2"),
                                                rs.getString("country_name"));
      country.setContinent(rs.getString("continent"));
      country.setPopulation(rs.getLong("country_population"));
      return country;
    } catch (SQLException e) {
    }
    return null;
  }
  
  @Override
  public List<CountryEntity> all() {
    String sql = "SELECT country_code, country_code2, country_name, continent, country_population "
               + "FROM country;";
    
    try (Connection connection = getConnection()) { // try-with-resource
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        System.out.printf("SQL: %s%n", statement.toString());

        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CountryEntity> countries = new ArrayList<>();
          while(rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if (country != null) {
              countries.add(country);
            }
          }
          return countries;
        }
      }
    }
    catch(SQLException e) {
      String message = String.format("SQL error occured. Error: %s", e.getMessage());
      System.out.println(message);
      throw new DbException(message, e);
    }
  }

  @Override
  public List<CountryEntity> all(String continent) {
    String sql = "SELECT country_code, country_code2, country_name, continent, country_population "
               + "FROM country "
               + "WHERE continent = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, continent);
        System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CountryEntity> countries = new ArrayList<>();
          while(rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if(country != null) {
              countries.add(country);
            }
          }
          return countries;          
        }
      }
    }
    catch(SQLException e) {
      String message = String.format("SQL error occured. Error: %s", e.getMessage());
      System.out.println(message);
      throw new DbException(message, e);
    }
  }

  @Override
  public CountryEntity getByCode(String code) {
    String sql = "SELECT country_code, country_code2, country_name, continent, country_population "
               + "FROM country "
               + "WHERE country_code = ? OR country_code2 = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, code);
        System.out.printf("SQL: %s%n", statement.toString());
 
        try (ResultSet rs = statement.executeQuery()) {
          while(rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if(country != null) {
              return country;
            }
          }
          return null;
        }
      }
    }
    catch(SQLException e) {
      String message = String.format("SQL error occured. Error: %s", e.getMessage());
      System.out.println(message);
      throw new DbException(message, e);
    }
  }
}
