package world.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
  
  /**
   * Starts a transaction on the specified connection.
   * @param connection The connection to run transactions on.
   * @return The original / modified connection.
   */
  public Connection startTransaction(Connection connection) {
    try {
      if (connection.getAutoCommit()) {
        connection.setAutoCommit(false);
      }
      return connection;
    }
    catch (SQLException e) {
      throw new DbException("Failed to disable auto commit mode.");
    }
  }

  /**
   * Commits or writes the specified transaction.
   * @param connection The transaction to finalize.
   * @return The connection instance.
   */
  public Connection commitTransaction(Connection connection) {
    try {
      if (! connection.getAutoCommit()) {
        connection.commit();
      }
      return connection;
    }
    catch(SQLException e) {
      throw new DbException("Failed to commit transaction.");
    }
  }
  
  /**
   * Rolls back undoes the previous modifications.
   * @param connection The transaction to roll back.
   * @return The connection instance.
   */
  public Connection rollbackTransaction(Connection connection) {
    try {
      if (! connection.getAutoCommit()) {
        connection.rollback();
      }
      return connection;
    }
    catch(SQLException e) {
      throw new DbException("Failed to commit transaction.");
    }    
  }
}
