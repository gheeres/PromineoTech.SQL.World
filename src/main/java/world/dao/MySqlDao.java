package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import world.Configuration;
import world.entity.CountryEntity;
import world.exception.DbException;

public abstract class MySqlDao {
  /**
   * Gets the connection to the database.
   * @return The connection.
   */
  protected Connection getConnection() {
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
  
  protected CountryEntity toCountryEntity(ResultSet rs) {
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
  
}
