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
   * @param city The city to create / add.
   * @return The created city if successful, false if otherwise.
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
                .setCountry(new CountryEntity(input.getCountry()))
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
   * Returns all of the available countries.
   * @return The list of countries.
   */
  public List<CountryEntity> getCountries() {
    try (Connection connection = getConnection()) {
      String sql = "SELECT "
                 + "  country_code, "
                 + "  country_code2, "
                 + "  country_name, "
                 + "  continent, "
                 + "  country_capital, "
                 + "  country_population "
                 + "FROM "
                 + "  country "
                 + "ORDER BY "
                 + "  country_name;";
      //System.out.printf("SQL: %s%n", sql);
      try (PreparedStatement statement = 
                             connection.prepareStatement(sql)) {
        List<CountryEntity> countries = new ArrayList<CountryEntity>();
        // read row by row
        try(ResultSet rs = statement.executeQuery()) {
          while(rs.next()) {
            // serialize / convert rows and columns into a CountryEntity
            CountryEntity country = toCountry(rs);
            //if (country != null) {
            if (Objects.nonNull(country)) {
              countries.add(country);
            }
          }
        }
        
        return countries;
      }
    }
    catch (SQLException e) {
      throw new DbException("Failed to get all countries.", e);
    }
  }
  
  /**
   * Returns the specified country.
   * @param code The unique ISO3166 code for the country. 
   * @return The country if found, otherwise an empty optional.
   */
  public Optional<CountryEntity> getCountryByCode(String code) {
    try (Connection connection = getConnection()) {
      String sql = "SELECT "
                 + "  country_code, "
                 + "  country_code2, "
                 + "  country_name, "
                 + "  continent, "
                 + "  country_capital, "
                 + "  country_population "
                 + "FROM "
                 + "  country "
                 + "WHERE country_code = ? ;";
      //System.out.printf("SQL: %s%n", sql);
      try (PreparedStatement statement = 
                             connection.prepareStatement(sql)) {
        statement.setString(1, code);
        
        try(ResultSet rs = statement.executeQuery()) {
          if (rs.next()) {
            CountryEntity country = toCountry(rs);
            return Optional.ofNullable(country);
          }
        }
        
        return Optional.empty();
      }
    }
    catch (SQLException e) {
      String message = String.format("Failed to get requested country: %s", code);
      throw new DbException(message, e);
    }
  }
  
  /**
   * Serializes the current row into a CountryEntity instance.
   * @param rs The current row
   * @return The created instance.
   */
  protected CountryEntity toCountry(ResultSet rs) {
    try {
      //String code = rs.getString("country_code");
      //CountryEntity country = new CountryEntity(code);
      //country.setCode2(rs.getString("country_code2"));
      //country.setName(rs.getString("country_name"));
      //return country;
      return new CountryEntity(rs.getString("country_code"))
          .setCode2(rs.getString("country_code2"))
          .setName(rs.getString("country_name"));
    }
    catch(SQLException e) {
      throw new DbException("Failed to serialize row into CountryEntity.", e);
    }
  }
}
