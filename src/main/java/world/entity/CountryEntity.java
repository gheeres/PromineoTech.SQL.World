package world.entity;

public class CountryEntity {
  private String code;
  private String code2;
  private String name;
  private String continent;
  private CityEntity capital;
  private Long population;
  
  public CountryEntity(String code, String code2, String name) {
    setCode(code);
    setCode2(code2);
    setName(name);
  }
  
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getCode2() {
    return code2;
  }
  public void setCode2(String code2) {
    this.code2 = code2;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getContinent() {
    return continent;
  }
  public void setContinent(String continent) {
    this.continent = continent;
  }
  public CityEntity getCapital() {
    return capital;
  }
  public void setCapital(CityEntity capital) {
    this.capital = capital;
  }
  public Long getPopulation() {
    return population;
  }
  public void setPopulation(Long population) {
    this.population = population;
  }
}
