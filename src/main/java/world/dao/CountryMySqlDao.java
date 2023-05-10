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
