package world.entity;

public class CountryEntity extends Entity {
  private String code;
  private String code2;
  private String name;
  private Long population;
  
  //public CountryEntity() {
  //}
  
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
  public Long getPopulation() {
    return population;
  }
  public void setPopulation(Long population) {
    this.population = population;
  }
  
  /**
   * Checks to see if the model is valid, contains all required fields.
   * @return True if valid, false if otherwise.
   */
  public boolean isValid() {
    return ((getCode() == null) || (getName() == null));
  }
}
