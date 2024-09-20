package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.entity.CityEntity;
import world.entity.InputCityEntity;
import world.exception.DbException;

public class CityMySqlDao extends MySqlDao implements CityDao {
  private CityEntity toCityEntity(ResultSet rs) throws SQLException
  {
    CityEntity entity = new CityEntity(rs.getInt("ID"), rs.getString("Name"))
                                      .setPopulation(rs.getInt("Population"));
    return entity;
  }
  
  @Override
  public List<CityEntity> getCitiesForCountry(String code) {
    //String sql = "SELECT ID, Name, Population "
    //           + "FROM city "
    //           + "WHERE CountryCode = ?"
    //           + "  OR CountryCode IN (SELECT Code FROM country WHERE Code2 = ?);";
    String sql = "SELECT city.ID, city.Name, city.Population "
               + "FROM city "
               + "INNER JOIN country ON city.CountryCode = country.Code "
               + "WHERE country.code2 = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        //statement.setString(2, code);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CityEntity> results = new ArrayList<>();
          while(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              results.add(city);
            }
          }
          return results;
        }
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }    
  }

  public CityEntity getById(int id) {
    String sql = "SELECT city.ID, city.Name, city.Population "
               + "FROM city "
               + "WHERE city.ID = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          while(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              return city;
            }
          }
          return null;
        }
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }
  
  @Override
  public CityEntity createCity(InputCityEntity input, String countryCode) {
    if ((input == null) || (! input.isValid())) {
      return null;
    }
    
    String sql = "INSERT INTO city (Name, CountryCode, Population) "
               + "VALUES(?, (SELECT Code FROM country WHERE (Code2 = ?) OR (Code = ?)), ?);";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, input.getName());
        statement.setString(2, countryCode);
        statement.setString(3, countryCode);
        statement.setInt(4, input.getPopulation());
        //System.out.printf("SQL: %s%n", statement.toString());
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          ResultSet keys = statement.getGeneratedKeys();
          if (keys.next()) {
            int id = keys.getInt(1);
            CityEntity result = getById(id);
            return result;
          }
        }
        
        return null;
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }

  @Override
  public CityEntity deleteCity(int id) {
    CityEntity existing = getById(id);
    if (existing == null) {
      return null;
    }
    
    String sql = "DELETE FROM city WHERE ID = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          return existing;
        }
        // return rowsModified == 1;
        
        return null;
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }

  @Override
  public CityEntity updateCity(int id, String name, int population) {
    String sql = "UPDATE city SET Name = ?, Population = ? WHERE ID = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, name);
        statement.setInt(2, population);
        statement.setInt(3, id);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          return getById(id);
        }
        // return rowsModified == 1;
        return null;
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }
}
