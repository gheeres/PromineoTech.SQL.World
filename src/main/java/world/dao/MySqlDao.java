package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import world.Configuration;
import world.exception.DbException;

public abstract class MySqlDao {
  /**
   * Retrieves a connection to the configured database.
   * @return The Connection instance.
   */
  public Connection getConnection() {
    return getConnection(null); 
  }
  
  /**
  * Retrieves a connection to the configured database.
  * @param properties The optional properties / settings to append to the connection string.
  * @return The Connection instance.
  */
  public Connection getConnection(String properties) {
   String url = Configuration.getProperty("datasource.url");
   if ((properties != null) && (! properties.isEmpty())) {
     url = String.format("%s%s%s", url,
                         (url.indexOf("?") >= 0) ? "&" : "?",
                         properties);
   }
   try {
     Connection connection = DriverManager.getConnection(url);
     return connection;
   }
   catch (SQLException e) {
     String message = String.format("Error getting connection: %s. Error: %s%n",
                                    url, e.getMessage());
     System.out.println(message); 
     throw new DbException(message, e);
   }
  }
}
