package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import world.Configuration;
import world.exception.DbException;

public abstract class MySqlDao {
  public Connection getConnection() {
    String url = Configuration.getProperty("datasource.url");
    //System.out.println("Connecting to: " + url);
    
    try {
      Connection connection = DriverManager.getConnection(url);
      return connection;
    } catch (SQLException e) {
      String message = String.format("Error getting connection %s. Error: %s", url, e.getMessage());
      System.out.println(message);
      throw new DbException(message);
    }
  }
}
