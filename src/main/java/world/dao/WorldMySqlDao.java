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
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.entity.CountryEntity;
import world.exception.DbException;

public class WorldMySqlDao extends MySqlDao {
  private static final String SCHEMA_FILENAME = "myworld_schema.sql";
  private static final String DATA_FILENAME = "myworld_data.sql";
  
  /**
   * Creates the specified schema for the database.
   * @return The instance.
   */
  public WorldMySqlDao createSchema() {
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
  public WorldMySqlDao initializeData() {
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
  
  /**
   * Adds the specified city information.
   * @param input The city to create / add.
   * @return The created city if successful, null if otherwise.
   */
  public CityEntity addCity(CityInputEntity input) {
    if (Objects.nonNull(input))  {
      try (Connection connection = getConnection()) {
        String sql = "INSERT INTO city " +
                     "(country_code,city_name," +
                      "latitude,longitude,city_population)" +
                     "VALUES (?, ?, ?, ?, ?)";
        //System.out.printf("SQL: %s%n", sql);
        try (PreparedStatement statement = 
          connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
          statement.setString(1, input.getCountry());
          statement.setString(2, input.getName());
          statement.setObject(3, input.getLatitude(), Types.DECIMAL);
          statement.setObject(4, input.getLongitude(), Types.DECIMAL);
          statement.setObject(5, input.getPopulation(), Types.INTEGER);
         
          int rowsModified = statement.executeUpdate();
          if (rowsModified == 1) {
            int id = 0;
            try(ResultSet keys = statement.getGeneratedKeys()) {
              if (keys.next()) {
                id = keys.getInt(1);
              }
            }
            
            CityEntity result = new CityEntity(id)
                .setCountry(new CountryEntity())
                .setLatitude(input.getLatitude())
                .setLongitude(input.getLongitude())
                .setName(input.getName())
                .setPopulation(input.getPopulation());
            System.out.printf("Added city. City: %s%n", result);
            return result;
          }
        }
      }
      catch(SQLException e) {
        String message = String.format("Failed to create city. City: %s",
                                       input);
        throw new DbException(message, e);
      }
    }
    return null;
  }

  /**
   * Converts a result set into an instance of CityEntity.
   * @param rs The result set (i.e. current row)
   * @return The instance
   */
  protected CityEntity toCityEntity(ResultSet rs) {
    try {
      //if (rs != null) {
      //  if (rs.getBigDecimal("latitude") != null) {
      //    rs.getBigDecimal("latitude").floatValue();
      //  }
      //}
      // can be simplified to
      //if ((rs != null) && (rs.getBigDecimal("latitude") != null)) {
      //  rs.getBigDecimal("latitude").floatValue();
      //}

      // C#, null safe property/accessor
      // rs?.getBigDecimal("latitude")?.floatValue();

      // C, C++, Java, JavaScript, C#
      // VisualBasic,Perl then;
      
      return new CityEntity(rs.getInt("city_id"))
          .setName(rs.getString("city_name"))
          .setPopulation(rs.getInt("city_population"))
          .setLatitude((rs.getBigDecimal("latitude") != null)  // conditional
            ? rs.getBigDecimal("latitude").floatValue() // true
            : null)                                     // false
          .setLongitude(rs.getBigDecimal("longitude") != null
            ? rs.getBigDecimal("longitude").floatValue()
            : null);
    } catch (SQLException e) {
    }    
    return null;
  }
  
  /**
   * Returns all cities.
   * @return The collection of cities.
   */
  public List<CityEntity> getAllCities() {
    // Get connection
    try (Connection connection = getConnection()) {
      String sql = "SELECT "
          + "  city.city_id, "
          + "  city.country_code, "
          + "  country.country_name, "
          + "  city.city_name, "
          + "  city.latitude, "
          + "  city.longitude, "
          + "  city.city_population "
          + "FROM "
          + "  city "
          + "  INNER JOIN country "
          + "  ON city.country_code = country.country_code "
          + "ORDER BY"
          + "  city.city_name";
      System.out.printf("SQL: %s%n", sql);
      //System.out.println("SQL: " + sql);
      
      try(PreparedStatement statement = connection.prepareStatement(sql)) {
        try(ResultSet rs = statement.executeQuery()) {
          List<CityEntity> cities = new ArrayList<CityEntity>();
          while(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              cities.add(city);
            }
          }
          return cities;
        }
      }
    }
    catch(SQLException e) {
      throw new DbException("Failed to get all cities.", e);
    }
  }
  
  /**
   * Retrieves a city by its unique id.
   * @param cityId The unique id of the city.
   * @return The city if found, otherwise and empty optional.
   */
  public Optional<CityEntity> getCityById(int cityId) {
    // Get connection
    try (Connection connection = getConnection()) {
      String sql = "SELECT "
          + "  city.city_id, "
          + "  city.country_code, "
          + "  country.country_name, "
          + "  city.city_name, "
          + "  city.latitude, "
          + "  city.longitude, "
          + "  city.city_population "
          + "FROM "
          + "  city "
          + "  INNER JOIN country "
          + "  ON city.country_code = country.country_code "
          + "WHERE city.city_id = ? "
          + "ORDER BY"
          + "  city.city_name";
      System.out.printf("SQL: %s%n", sql);
      //System.out.println("SQL: " + sql);
      
      try(PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, cityId);
        
        try(ResultSet rs = statement.executeQuery()) {
          if(rs.next()) {
            CityEntity city = toCityEntity(rs);
            return Optional.ofNullable(city);
          }
        }
      }
    }
    catch(SQLException e) {
      throw new DbException("Failed to get all cities.", e);
    }
    return Optional.empty();
  }
  
  public Optional<CityEntity> updateCity(int cityId, 
                                         CityInputEntity input) {
    // Init Driver/Open Connection
    try (Connection connection = getConnection()) {
      String sql = "UPDATE city SET "
                 + "  city_name = ?, "
                 + "  city_population = ?, "
                 + "  country_code = ?, "
                 + "  latitude = ?, "
                 + "  longitude = ? "
                 + "WHERE city_id = ?;";
      try (PreparedStatement statement = 
                             connection.prepareStatement(sql)) {
        statement.setString(1, input.getName());
        //if (input.getPopulation() == null) {
        //  statement.setNull(2, Types.INTEGER);
        //}
        //else {
        //  statement.setInt(2, input.getPopulation());
        //}
        statement.setObject(2, input.getPopulation(), Types.INTEGER);
        statement.setString(3, input.getCountry());
        statement.setObject(4, input.getLatitude(), Types.DECIMAL);
        statement.setObject(5, input.getLongitude(), Types.DECIMAL);
        statement.setInt(6, cityId);
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          return getCityById(cityId);
        }
      }
      catch(Exception e) {
        throw new DbException("Error in SQL updating city", e);
      }
    }
    catch(SQLException e) {
      throw new DbException("Failed to update city", e);
    }
    return Optional.empty();
  }
  
  
  /**
   * Removes a city from the database by it's unique id.
   * @param cityId The unique id of the city.
   * @return The removed city if successful, otherwise if not, then returns an empty.
   */
  public Optional<CityEntity> deleteCity(int cityId) {
    Optional<CityEntity> existing = getCityById(cityId);
    if (existing.isPresent()) {
      try(Connection connection = getConnection()) {
        String sql = "DELETE FROM city WHERE city_id = ?";
        try(PreparedStatement statement = 
                              connection.prepareStatement(sql)) {
          statement.setInt(1, cityId);
          
          int rowsDeleted = statement.executeUpdate();
          if (rowsDeleted == 1) {
            return existing;
          }
        }
        catch(Exception e) {
          throw new DbException("Unable to delete due to unhandled error.", e);
        }
      }
      catch(SQLException e) {
        throw new DbException("Failed to remove city.", e);
      }
    }
    return Optional.empty();
  }
}
