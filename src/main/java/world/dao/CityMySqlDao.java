package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;
import world.entity.CityEntity;
import world.entity.CityInputEntity;
import world.exception.DbException;

public class CityMySqlDao extends MySqlDao implements CityDao {

  protected CityEntity toCityEntity(ResultSet rs) {
    try {
      CityEntity entity = new CityEntity(rs.getLong("city_id"), toCountryEntity(rs), 
                                         rs.getString("city_name"));
      entity.setLatitude(rs.getDouble("latitude"));
      entity.setLongitude(rs.getDouble("longitude"));
      entity.setPopulation(rs.getLong("city_population"));
      return entity;
    } catch (SQLException e) {
      return null;
    }
  }
  
  
  public CityEntity getById(int id) {
    String sql = "SELECT city.city_id, city.country_code, city.city_name, "
               + "       city.latitude, city.longitude, city.city_name, city.city_population, "
               + "       country.country_code2, country.country_name, country.continent, country.country_population "
               + "FROM "
               + "  city "
               + "  INNER JOIN country "
               + "  ON city.country_code = country.country_code "
               + "WHERE city.city_id = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        
        try(ResultSet rs = statement.executeQuery() ) {
          if(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              return city;
            }
          }
          return null;
        }
      }
    }
    catch (SQLException exception) {
      throw new DbException(exception);
    }
  }
  
  @Override
  public Stream<CityEntity> all(String code) {
    String sql = "SELECT city.city_id, city.country_code, city.city_name, "
               + "       city.latitude, city.longitude, city.city_name, city.city_population, "
               + "       country.country_code2, country.country_name, country.continent, country.country_population "
               + "FROM "
               + "  city "
               + "  INNER JOIN country "
               + "  ON city.country_code = country.country_code "
               + "WHERE country.country_code = ? OR country.country_code2 = ? "
               + "ORDER BY city.city_name;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, code);
        
        try(ResultSet rs = statement.executeQuery() ) {
          ArrayList<CityEntity> cities = new ArrayList<>();
          while(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              cities.add(city);
            }
          }
          return cities.stream();
        }
      }
    }
    catch (SQLException exception) {
      throw new DbException(exception);
    }
  }


  @Override
  public CityEntity save(CityInputEntity input) {
    String sql = "INSERT INTO city (country_code, latitude, longitude, city_population, city_name) " +
                 "VALUES (?, ?, ?, ?, ?);";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, input.getCountry());
        statement.setDouble(2, input.getLatitude());
        statement.setDouble(3, input.getLongitude());
        statement.setLong(4, input.getPopulation());
        statement.setString(5, input.getName());
        
        int rows = statement.executeUpdate();
        if (rows == 1) {
          ResultSet keys = statement.getGeneratedKeys();
          if(keys.next()) {
            int id = keys.getInt(1);
            return getById(id);
          }
        }
        return null;
      }
    }
    catch (SQLException exception) {
      throw new DbException(exception);
    }
  }
}
