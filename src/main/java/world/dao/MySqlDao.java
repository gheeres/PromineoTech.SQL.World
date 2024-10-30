package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import world.Configuration;

public abstract class MySqlDao {
  protected Connection getConnection() {
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
}
