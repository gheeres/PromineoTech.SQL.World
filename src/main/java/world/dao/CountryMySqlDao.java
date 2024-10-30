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
import world.entity.CountryInputEntity;
import world.exception.DbException;

public class CountryMySqlDao implements CountryDao {
  /**
   * Gets the connection to the database.
   * @return The connection.
   */
  public Connection getConnection() {
    String url = Configuration.getProperty("datasource.url");
    try {
      Connection connection = DriverManager.getConnection(url);
      return connection;
    } catch (SQLException e) {
      String message = String.format("Error getting connection: %s. Error: %s",
                                     url, e.getMessage());
      System.out.println(message);
      throw new DbException(message, e);
    }
  }
  
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
    
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        try(ResultSet rs = statement.executeQuery() ) {
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
    } catch (SQLException e) {
      throw new DbException(e);
    }
  }

  @Override
  public CountryEntity getByCode(String code) {
    String sql = "SELECT country_code, country_code2, country_name, continent, country_population FROM country "
               + "WHERE country_code = ? OR country_code2 = ?;";
    System.out.printf("SQL: %s%n", sql);
    
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, code);
        
        try(ResultSet rs = statement.executeQuery() ) {
          if (rs.next()) {
            CountryEntity country = toCountryEntity(rs);
            if (country != null) {
              return country;
            }
          }
          return null;
        }
      }
    } catch (SQLException e) {
      throw new DbException(e);
    }
  }

  @Override
  public CountryEntity save(CountryInputEntity input) {
    if ((input == null) || (! input.isValid())) {
      throw new DbException("Invalid or missing country information provided.");
    }
    
    String sql = "INSERT INTO country (country_code, country_code2, country_name, continent, country_population) "
               + "VALUES(?, ?, ?, ?, ?);";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, input.getCode());
        statement.setString(2, input.getCode2());
        statement.setString(3, input.getName());
        statement.setString(4, "North America");
        statement.setLong(5, input.getPopulation());
        
        int rows = statement.executeUpdate();
        if (rows == 1) {
          return getByCode(input.getCode());
        }
        return null;
      }
    } catch (SQLException e) {
      throw new DbException(e);
    }
  }
  
}
