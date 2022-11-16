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
import world.entity.CountryBaseEntity;
import world.entity.CountryEntity;
import world.entity.CountryLanguageEntity;
import world.entity.LanguageBaseEntity;
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
  public Optional<CityEntity> addCity(CityInputEntity input) {
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
            return getCity(id);
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
   * Retrieves the requested city by it's unique identifier.
   * @param cityid The unique / internal id of the city.
   * @return The city if found, otherwise returns an empty value.
   */
  public Optional<CityEntity> getCity(int cityId) {
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
      //System.out.printf("SQL: %s%n", sql);
      try (PreparedStatement statement = 
           connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, cityId);
       
        try (ResultSet rs = statement.executeQuery()) {
          if (rs.next()) {
            return Optional.ofNullable(toCity(rs));
          }
        }
        return Optional.empty();
      }
      catch(Exception e) {
        throw new DbException("Failed to get requested city.", e);
      }
    }
    catch(SQLException e) {
      String message = String.format("Failed to get city. City: %s",
                                     cityId);
      throw new DbException(message, e);
    }
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
   * Adds a new language for a country.
   * @param code The unique id of the country.
   * @param languageCode The unique language code.
   * @param isOfficial Indicates if the language is an officially recognized language.
   * @param percentage The amount or percentage of people that speak the language in the country.
   * @return The added CountryLanguageEntity if successful, otherwise returns an empty value.
   */
  public Optional<CountryLanguageEntity> addLanguage(String code, 
                                                     String languageCode,
                                                     boolean isOfficial, 
                                                     Float percentage) {
    // try-with-resource, automatically closes IClosable
    // try, need to manually close when done
    try (Connection connection = getConnection()) {
      startTransaction(connection);
      
      String sql = "INSERT INTO country_language "
               + " (country_code, language_code, "
               + "  is_official, language_percentage) "
               + " VALUES (?, ?, ?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, languageCode);
        statement.setString(3, isOfficial ? "T" : "F");
        statement.setObject(4, percentage, Types.DECIMAL);
        // (condition) ? (if true) : (else false)
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          commitTransaction(connection);
          return getCountryLanguage(code, languageCode);
        }
        
        return Optional.empty();
      }
      catch(Exception e) {
        rollbackTransaction(connection);
        throw new DbException("Failed to add language.", e);
      }
    }
    catch (SQLException e) {
      String message = String.format("Failed to add language (%s) "
                                   + "to country. Country: %s",
                                     code, languageCode);
      throw new DbException(message, e);
    }
  }

  /**
   * Removes a language code for a country.
   * @param code The unique id of the country.
   * @param languageCode The unique language code.
   * @return The deleted / removed CountryLanguageEntity if successful, otherwise returns an empty value.
   */
  public Optional<CountryLanguageEntity> deleteLanguage(String code, 
                                                        String languageCode) {
    try (Connection connection = getConnection()) {
      // Get the existing language before we delete 
      // it so that the consumer can use it if they want.
      Optional<CountryLanguageEntity> existingLanguage = getCountryLanguage(code, languageCode);
      if (! existingLanguage.isPresent()) {
        return existingLanguage;
      }
      startTransaction(connection);
      
      String sql = "DELETE FROM country_language "
                + " WHERE country_code = ? "
                + "   AND language_code = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, languageCode);
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          commitTransaction(connection);
          return existingLanguage;
        }
        return Optional.empty();
      }
      catch(Exception e) {
        rollbackTransaction(connection);
        throw new DbException("Failed to add language.", e);
      }
    }
    catch (SQLException e) {
      String message = String.format("Failed to add language (%s) "
                                   + "to country. Country: %s",
                                     code, languageCode);
      throw new DbException(message, e);
    }
  }

  /**
   * Sets the capital of the country to the specified city id.
   * @param code The unique id of the country.
   * @param cityId The unique id of the city. If null, then the capital city is removed.
   * @return The updated or resulting CountryEntity if successful, otherwise returns an empty value.
   */
  public Optional<CountryEntity> setCapital(String code, Integer cityId) {
    try (Connection connection = getConnection()) {
      startTransaction(connection);
      
      String sql = "UPDATE country "
                + " SET country_capital = ? "
                + " WHERE country_code = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setObject(1, cityId, Types.INTEGER);
        statement.setString(2, code);
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          commitTransaction(connection);
          return getCountryByCode(code);
        }
        return Optional.empty();
      }
      catch(Exception e) {
        rollbackTransaction(connection);
        throw new DbException("Failed to set capital for country.", e);
      }
    }
    catch (SQLException e) {
      String message = String.format("Failed to set capital (%d)"
                                   + "for country. Country: %s",
                                     cityId, code);
      throw new DbException(message, e);
    }
  }

  /**
   * Retrieves details about a spoken language in a country.
   * @param code The unique id of the country.
   * @param languageCode The unique id of the language.
   * @return The language details if found, otherwise an empty value.
   */
  public Optional<CountryLanguageEntity> getCountryLanguage(String code, 
                                                            String languageCode) {
    try (Connection connection = getConnection()) {
      String sql = "SELECT "
          + "  country_language.country_code, "
          + "  country.country_name, "
          + "  country_language.language_code, "
          + "  language.language_name, "
          + "  country_language.is_official, "
          + "  country_language.language_percentage "
          + "FROM "
          + "  country_language "
          + "  INNER JOIN country "
          + "  ON country_language.country_code = country.country_code "
          + "  INNER JOIN language "
          + "  ON country_language.language_code = language.language_code "
          + "WHERE country_language.country_code = ? "
          + "  AND country_language.language_code = ?;";
      //System.out.printf("SQL: %s%n", sql);
      try (PreparedStatement statement = 
                             connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, languageCode);
        
        try(ResultSet rs = statement.executeQuery()) {
          if (rs.next()) {
            CountryLanguageEntity language = toCountryLanguage(rs);
            return Optional.ofNullable(language);
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
   * Converts or serialized a row/column into an instance of CountryLanguageEntity
   * @param rs The result to process
   * @return The serialized value.
   */
  private CountryLanguageEntity toCountryLanguage(ResultSet rs) {
    try {
      return new CountryLanguageEntity()
          .setCountry(toCountryBase(rs))
          .setLanguage(toLanguageBase(rs))
          .setOfficial("T".equalsIgnoreCase(rs.getString("is_official")))
          .setPercentage((rs.getBigDecimal("language_percentage") != null) 
              ? rs.getBigDecimal("language_percentage").floatValue() 
              : null);
    } catch (SQLException e) {
      throw new DbException("Failed to serialize CountryLanguageEntity.", e);
    }
  }

  /**
   * Converts or serialized a row/column into an instance of LanguageBaseEntity
   * @param rs The result to process
   * @return The serialized value.
   */
  private LanguageBaseEntity toLanguageBase(ResultSet rs) {
    try {
      return new LanguageBaseEntity(rs.getString("language_code"))
                           .setName(rs.getString("language_name"));
    } catch (SQLException e) {
      throw new DbException("Failed to serialize LanguageBase.", e);
    }
  }

  /**
   * Converts or serialized a row/column into an instance of CountryBaseEntity
   * @param rs The result to process
   * @return The serialized value.
   */
  private CountryBaseEntity toCountryBase(ResultSet rs) {
    try {
      return new CountryBaseEntity(rs.getString("country_code"))
                          .setName(rs.getString("country_name"));
    } catch (SQLException e) {
      throw new DbException("Failed to serialize CountryBase.", e);
    }
  }
  
  /**
   * Serializes the current row into a CountryEntity instance.
   * @param rs The current row
   * @return The created instance.
   */
  protected CountryEntity toCountry(ResultSet rs) {
    try {
      return new CountryEntity(
        new CountryBaseEntity(rs.getString("country_code"))
                             .setName(rs.getString("country_name"))
      ).setCode2(rs.getString("country_code2"))
       .setPopulation(rs.getInt("country_population"));
    }
    catch(SQLException e) {
      throw new DbException("Failed to serialize row into CountryEntity.", e);
    }
  }

  /**
   * Converts or serialized a column/row into an instance of CityEntity
   * @param rs The current row
   * @return The converted/serialized instance.
   */
  protected CityEntity toCity(ResultSet rs) {
    try {
      return new CityEntity(rs.getInt("city_id"))
          .setCountry(toCountryBase(rs))
          .setName(rs.getString("city_name"))
          .setLatitude((rs.getBigDecimal("latitude") != null) 
            ? rs.getBigDecimal("latitude").floatValue() 
            : null)
          .setLongitude((rs.getBigDecimal("longitude") != null)
            ? rs.getBigDecimal("longitude").floatValue()
            : null)
          .setPopulation((Integer) rs.getObject("city_population"));
    } catch (SQLException e) {
      throw new DbException("Failed to serialize row into City.", e);
    }
  }    
}
