package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.entity.CountryEntity;
import world.exception.DbException;

public class CountryMySqlDao extends MySqlDao implements CountryDao {
  private CountryEntity toCountryEntity(ResultSet rs) throws SQLException {
    CountryEntity entity = new CountryEntity(rs.getString("Code2"), rs.getString("Name"))
                                            .setContinent(rs.getString("Continent"))
                                            .setPopulation(rs.getInt("Population"));
    return entity;
  }
  
  @Override
  public List<CountryEntity> all() {
    final String sql = "SELECT "
                     + "  Code,Code2,Name,Continent,Population "
                     + "FROM "
                     + "  country "
                     + "ORDER BY "
                     + "  Name";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CountryEntity> results = new ArrayList<>();
          while(rs.next()) {
            //CountryEntity entity = new CountryEntity(rs.getString("Code2"), rs.getString("Name"))
            //                                        .setContinent(rs.getString("Continent"))
            //                                        .setPopulation(rs.getInt("Population"));
            //CountryEntity entity = new CountryEntity(rs.getString("Code2"), rs.getString("Name"));
            //entity.setContinent(rs.getString("Continent"));
            //entity.setPopulation(rs.getInt("Population"));
            CountryEntity entity = toCountryEntity(rs);
            if (entity != null) {
              results.add(entity);
            }
          }
          return results;
        }
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }

  @Override
  public List<CountryEntity> all(String continent) {
    final String sql = "SELECT "
                     + "  Code,Code2,Name,Continent,Population "
                     + "FROM "
                     + "  country "
                     + "WHERE "
                     + "  Continent = ? " // Oracle = :My_Continent, MicrosoftSQL = @My_Continent
                     + "ORDER BY "
                     + "  Name";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, continent);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CountryEntity> results = new ArrayList<>();
          while(rs.next()) {
            CountryEntity entity = toCountryEntity(rs);
            if (entity != null) {
              results.add(entity);
            }
          }
          return results;
        }
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }

  @Override
  public CountryEntity getByCode(String code) {
    final String sql = "SELECT "
                     + "  Code,Code2,Name,Continent,Population "
                     + "FROM "
                     + "  country "
                     + "WHERE "
                     + "  Code = ? OR Code2 = ?; ";

    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        statement.setString(2, code);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          while(rs.next()) {
            CountryEntity entity = toCountryEntity(rs);
            if (entity != null) {
              return entity;
            }
          }
          return null;
        }
      }
    }
    catch (SQLException e) {
      throw new DbException(String.format("A unhandled error occurred. Error: %s", e.getMessage()));
    }
  }

}
