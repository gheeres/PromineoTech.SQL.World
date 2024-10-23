package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.Configuration;
import world.entity.CountryEntity;

public class CountryMySqlDao implements CountryDao
{
  //String databaseConnection = "jdbc:mysql://localhost:3306/world?user=worlduser&password=world&allowPublicKeyRetrieval=true&useSSL=false";
  
  private Connection getConnection() {
    String url = Configuration.getProperty("datasource.url");
    try {
      Connection connection = DriverManager.getConnection(url);
      return connection;
    } catch(SQLException e) {
      System.out.printf("Error getting connection to database. Url: %s; Error: %s%n", 
                        url, e.getMessage());
      return null;
    }
  }
  
  @Override
  public List<CountryEntity> all() {
    Connection connection = getConnection();
    if (connection != null) {
      System.out.println("Connected to the database!");

      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("Failed to connect.");
    }
    return new ArrayList<>();
  }

  @Override
  public List<CountryEntity> all(String continent) {
    return new ArrayList<>();
  }

  @Override
  public CountryEntity getByCode(String code) {
    return null;
  }
}
