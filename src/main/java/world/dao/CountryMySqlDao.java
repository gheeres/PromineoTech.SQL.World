package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import world.entity.CountryEntity;
import world.exception.DbException;

/**
 * Implementation of the CountryDao using a MySQL backend.
 */
public class CountryMySqlDao extends MySqlDao implements CountryDao {

  @Override
  public List<CountryEntity> all() {
	//return List.of(
	//  new CountryEntity("USA").setName("United States"),
	//  new CountryEntity("CAN").setName("Canada")
    //);
	
	try (Connection connection = getConnection()) {
	  String sql = "SELECT country_code, country_code2, country_name "
	  		     + "FROM country;";
	  try (PreparedStatement statement = connection.prepareStatement(sql)) {
		try (ResultSet rs = statement.executeQuery()) {
		  List<CountryEntity> countries = new ArrayList<CountryEntity>();	
		  while(rs.next()) {
   		    CountryEntity item = toCountryEntity(rs);
			if (item != null) {
	          countries.add(item);
			}
		  }
		  return countries;
		}
	  }
	} catch(SQLException e) {
	  throw new DbException("Failed to get all countries");	
	}
  }

  @Override
  public List<CountryEntity> all(String continent) {
	try (Connection connection = getConnection()) {
	  String sql = "SELECT country_code, country_code2, country_name "
	  		     + "FROM country "
	  		     + "WHERE continent = ?;";
	  try (PreparedStatement statement = connection.prepareStatement(sql)) {
		statement.setString(1, continent);
		
		try (ResultSet rs = statement.executeQuery()) {
		  List<CountryEntity> countries = new ArrayList<CountryEntity>();	
		  while(rs.next()) {
			CountryEntity item = toCountryEntity(rs);
			if (item != null) {
              countries.add(item);
			}
		  }
		  return countries;
		}
	  }
	} catch(SQLException e) {
	  throw new DbException("Failed to get all countries");	
	}
  }
  
  @Override
  public CountryEntity getByCode(String countryCode) {
	try (Connection connection = getConnection()) {
	  String sql = "SELECT country_code, country_code2, country_name "
	  		     + "FROM country "
	  		     + "WHERE country_code = ? OR country_code = ?;";
	  try (PreparedStatement statement = connection.prepareStatement(sql)) {
		statement.setString(1, countryCode);
		statement.setString(2, countryCode);
			
		try (ResultSet rs = statement.executeQuery()) {
		  if (rs.next()) {
			CountryEntity item = toCountryEntity(rs);
			return item;
	      }
		  return null;
 		}
	  }
	} catch(SQLException e) {
	  throw new DbException(String.format("Failed to get country by it's unique id. Code: %s", countryCode));	
	}
  }

  /**
   * Updates or modifies the specified country information.
   * @param countryCode The unique / internal id of the country.
   * @param input The country data to modify or update.
   * @return The modified or updated country if successful, otherwise returns null.
   */  
  @Override
  public CountryEntity update(String countryCode, CountryEntity input) {
	try (Connection connection = getConnection()) {
	  String sql = "UPDATE country SET " +
	               "  country_code = ?," +
	               "  country_code2 = ?," +
	               "  country_name = ? " +
	               "WHERE country_code = ?";
	  try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, input.getCode());
        statement.setString(2, input.getCode2());
        statement.setString(3, input.getName());
        statement.setString(4, countryCode);
        
        int rowsModified = statement.executeUpdate();
        if (rowsModified == 1) {
          return getByCode(input.getCode());	
        }
        return null;
	  }
	} catch(SQLException e) {
      throw new DbException(String.format("Failed to update country. Country: %s", countryCode));	
	}
  }
  
  @Override
  public boolean delete(String countryCode) {
	try (Connection connection = getConnection()) {
	  String sql = "DELETE FROM country " +
	               "WHERE country_code = ? OR country_code2 = ?;";
	  try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, countryCode);
	    statement.setString(2, countryCode);
		        
	    int rowsModified = statement.executeUpdate();
	    return (rowsModified == 1);
	    //if (rowsModified == 1) {
	    //  return true;	
	    //}
	    //return false;
	  }
	} catch(SQLException e) {
	 throw new DbException(String.format("Failed to delete country. Country: %s", countryCode));	
	}
  }

  /**
   * Parses a result set and converts it into an instance of CountryEntity.
   * @param rs The result set
   * @return The instance if successful, otherwise returns null.
   */
  private CountryEntity toCountryEntity(ResultSet rs) {
	try {
      return new CountryEntity(rs.getString("country_code"))
		         .setCode2(rs.getString("country_code2"))
		 	     .setName(rs.getString("country_name"));
	} catch (SQLException e) {
	  return null;	
	}
  }
}
