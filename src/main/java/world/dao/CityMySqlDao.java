package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.exception.DbException;

public class CityMySqlDao extends MySqlDao {
  /**
   * Adds the specified city information.
   * @param input The city to create / add.
   * @return The created city if successful, null if otherwise.
   */
  public CityEntity add(CityInputEntity input) {
    final String sql = "INSERT INTO city (country_code, city_name, city_population) VALUES (?, ?, ?)";
        
    try (Connection connection = getConnection()){
      try (PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, "USA");
        statement.setString(2, input.getName());
        statement.setInt(3, input.getPopulation());
        
        System.out.printf("SQL: %s%n", statement.toString());
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          ResultSet keys = statement.getGeneratedKeys();
          if (keys.next()) {
            int id = keys.getInt(1);
            return getById(id);
          }
        }
      }
      return null;   
    }
    catch (SQLException exception) {
      throw new DbException(String.format("A unhandled error occured. Error: %s", 
                                          exception.getMessage()));
    }    
  }
  
  /**
   * Retrieves all of the cities for the specified country code.
   * @param countryCode The unique code for the country.
   * @return The cities if found, otherwise returns an empty list.
   */
  public List<CityEntity> all(String countryCode) {
    final String sql = "SELECT * FROM city WHERE country_code = ? ORDER BY city_name;";
    
    try (Connection connection = getConnection()){
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, countryCode);
        System.out.printf("SQL: %s%n", statement.toString());
       
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CityEntity> cities = new ArrayList<>();
          while (rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              cities.add(city);
            }
          }
          return cities;
        }
      }
    }
    catch (SQLException exception) {
      throw new DbException(String.format("A unhandled error occured. Error: %s", 
                                          exception.getMessage()));
    }    
  }
  
  /**
   * Retrieves a city by it's unique id.
   * @param id The unique id of the city.
   * @return The city if found, otherwise null.
   */
  public CityEntity getById(Integer id) {
    final String sql = "SELECT * FROM city WHERE city_id = ?;";
    
    try (Connection connection = getConnection()){
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        System.out.printf("SQL: %s%n", statement.toString());
       
        try (ResultSet rs = statement.executeQuery()) {
          while (rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              return city;
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
  
  private CityEntity toCityEntity(ResultSet rs) throws SQLException {
    CityEntity city = new CityEntity(rs.getInt("city_id"), rs.getString("city_name"));
    city.setLatitude(rs.getDouble("latitude"));
    city.setLongitude(rs.getDouble("longitude"));
    city.setPopulation(rs.getInt("city_population"));
    return city;
  }

  /**
   * Removes the specified city from the database.
   * @param cityid The unique / internal id of the city.
   * @return The city if removed, otherwise returns an empty value.
   */
  public CityEntity delete(int cityid) {
    CityEntity existing = getById(cityid);
    if (existing != null) {
      final String sql = "DELETE FROM city WHERE city_id = ?;";
      
      try (Connection connection = getConnection()){
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setInt(1, cityid);
          System.out.printf("SQL: %s%n", statement.toString());
         
          int rowsModified = statement.executeUpdate();
          if (rowsModified == 1) {
            return existing;
          }
          return null;
        }
      }
      catch (SQLException exception) {
        throw new DbException(String.format("A unhandled error occured. Error: %s", 
                                            exception.getMessage()));
      }  
    }
    
    // City doesn't exist.
    return null;
  }
}
