package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.exception.DbException;

public class CityMySqlDao extends MySqlDao implements CityDao {
  @Override
  public CityEntity create(CityInputEntity input) {
    String sql = "";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        // Execute Statement
        // Get / Verify Results
        // Return new city
        return null;
      }
    }
    catch(SQLException e) {
      String message = String.format("SQL error occured. Error: %s", e.getMessage());
      System.out.println(message);
      throw new DbException(message, e);      
    }
  }
}
