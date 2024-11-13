package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;
import world.entity.CountryEntity;
import world.entity.CountryInputEntity;
import world.exception.DbException;

public class CountryMySqlDao extends MySqlDao implements CountryDao {
  @Override
  public Stream<CountryEntity> all() {
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
          return countries.stream();
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

  @Override
  public CountryEntity updateName(String code, String name) {
    if ((name == null) || (name.isEmpty())) {
      throw new DbException("Invalid or missing country name. Name is required.");
    }
    
    String sql = "UPDATE country SET country_name = ? WHERE country_code = ? OR country_code2 = ?";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        System.out.printf("SQL: %s%n", statement.toString());
        statement.setString(1, name);
        statement.setString(2, code);
        statement.setString(3, code);
        
        int rows = statement.executeUpdate();
        if (rows == 1) {
          return getByCode(code);
        }
        
        return null;
      }
    }
    catch(SQLException e) {
      throw new DbException(e);
    }
  }

  @Override
  public boolean delete(String code) {
    String sql = "DELETE FROM country WHERE country_code = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        System.out.printf("SQL: %s%n", statement.toString());
        statement.setString(1, code);
        
        int rows = statement.executeUpdate();
        return (rows == 1);
      }
    }
    catch(SQLException e) {
      throw new DbException(e);
    }
  }
}
