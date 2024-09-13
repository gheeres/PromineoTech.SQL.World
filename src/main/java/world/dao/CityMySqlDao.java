package world.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import world.entity.CityEntity;
import world.exception.DbException;

public class CityMySqlDao extends MySqlDao implements CityDao {
  private CityEntity toCityEntity(ResultSet rs) throws SQLException
  {
    CityEntity entity = new CityEntity(rs.getInt("ID"), rs.getString("Name"))
                                      .setPopulation(rs.getInt("Population"));
    return entity;
  }
  
  @Override
  public List<CityEntity> getCitiesForCountry(String code) {
    //String sql = "SELECT ID, Name, Population "
    //           + "FROM city "
    //           + "WHERE CountryCode = ?"
    //           + "  OR CountryCode IN (SELECT Code FROM country WHERE Code2 = ?);";
    String sql = "SELECT city.ID, city.Name, city.Population "
               + "FROM city "
               + "INNER JOIN country ON city.CountryCode = country.Code "
               + "WHERE country.code2 = ?;";
    try (Connection connection = getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, code);
        //statement.setString(2, code);
        //System.out.printf("SQL: %s%n", statement.toString());
        
        try (ResultSet rs = statement.executeQuery()) {
          ArrayList<CityEntity> results = new ArrayList<>();
          while(rs.next()) {
            CityEntity city = toCityEntity(rs);
            if (city != null) {
              results.add(city);
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
}
