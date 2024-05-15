package world.entity;

public class CountryEntity extends Entity {
  private String code;
  private String name;
  private String continent;
  private int population;
  
  public CountryEntity(String code, String name) {
    setCode(code);
    setName(name);
  }
  
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
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
  
  public int getPopulation() {
    return population;
  }
  public void setPopulation(int population) {
    this.population = population;
  }
  
  @Override
  public String toString() {
    return String.format("[%s] %s (%d)", getCode(), getName(), getPopulation());
  }
}
