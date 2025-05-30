package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import world.entity.CountryEntity;
import world.exception.DbException;

public class CountryMySqlDao extends MySqlDao implements CountryDao {

  @Override
  public CountryEntity create(CountryEntity country) {
    // Setup Driver
    // Create Connection
    try (Connection connection = getConnection()) {
      String sql = "INSERT INTO country " +
                   "(country_code, country_code2, country_name, country_population) " +
                   "VALUES " + 
                   "(?, ?, ?, ?)"; // MSSQL: @country_code, @country_name / Oracle: :country_code, :country_name
      // Create Statement
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, country.getCode());
        statement.setString(2, country.getCode2());
        statement.setString(3, country.getName());
        statement.setLong(4, country.getPopulation());

        // Execute Statement
        int rows = statement.executeUpdate();
        if (rows == 1) {
          return country;
        }
        
        return null;
      }
      // Close
    }
    catch (SQLException e) {
      String message = String.format("SQL error occured. Error: %s", e.getMessage());
      System.out.println(message);
      throw new DbException(message);
    }
  }
}
