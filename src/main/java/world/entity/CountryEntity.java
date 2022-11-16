package world.entity;

/**
 * Represents a country in the world.
 */
public class CountryEntity extends CountryBaseEntity {
  private String code2;
  private Integer population;

  public CountryEntity(String code) {
    super(code);
  }

  public CountryEntity(CountryBaseEntity country) {
    super(country);
  }

  public Integer getPopulation() {
    return population;
  }

  public CountryEntity setPopulation(Integer population) {
    this.population = population;
    return this;
  }

  public String getCode2() {
    return code2;
  }

  public CountryEntity setCode2(String code2) {
    this.code2 = code2;
    return this;
  }
}
