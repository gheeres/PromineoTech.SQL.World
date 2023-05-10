package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import world.Configuration;
import world.exception.DbException;

public class WorldMySqlDatabase extends MySqlDao {
  private static final String SCHEMA_FILENAME = "myworld_schema.sql";
  private static final String DATA_FILENAME = "myworld_data.sql";
  
  /**
   * Creates the specified schema for the database.
   * @return The instance.
   */
  public WorldMySqlDatabase createSchema() {
    try (Connection connection = getConnection("allowMultiQueries=true")) {
      String sql = getSchema();
      //System.out.printf("Schema:%n%s", sql);
      try(Statement statement = connection.createStatement()) {
        statement.executeUpdate(sql);
      }
      System.out.println("Created schema.");
    }
    catch(SQLException e) {
      throw new DbException("Failed to create database schema", e);
    }
    return this;
  }

  /**
   * Reads the embedded schema.
   * @return The contents of the schema / resource.
   */
  private String getSchema() {
    return Configuration.getResourceAsString(SCHEMA_FILENAME);
  }
  
  /**
   * Load the initial data.
   * @return The instance.
   */
  public WorldMySqlDatabase initializeData() {
    try (Connection connection = getConnection("allowMultiQueries=true")) {
      String sql = getInitialData();
      //System.out.printf("Data:%n%s", sql);
      try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(sql);
      }
      System.out.println("Loaded initial data");
    }
    catch(SQLException e) {
      throw new DbException("Failed to load initial data.", e);
    }
    return this;
  }

  /**
   * Reads the embedded default / initial data.
   * @return The initial / configured data.
   */
  private String getInitialData() {
    return Configuration.getResourceAsString(DATA_FILENAME);
  }
}
