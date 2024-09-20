package world.entity;

public class CountryEntity extends Entity {
  private String code2;
  private String continent;
  private Integer population;
  private String name;
  
  public CountryEntity(String code2, String name) {
    setCode2(code2); // this.code2 = code2;
    setName(name);
  }
  
  public String getCode2() {
    return code2;
  }
  public CountryEntity setCode2(String code2) {
    this.code2 = code2;
    return this;
  }

  public String getName() {
    return name;
  }
  public CountryEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getContinent() {
    return continent;
  }
  public CountryEntity setContinent(String continent) {
    this.continent = continent;
    return this;
  }

  public Integer getPopulation() {
    return population;
  }
  public CountryEntity setPopulation(Integer population) {
    this.population = population;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("[%s] %s", getCode2(), getName());
  }
}
