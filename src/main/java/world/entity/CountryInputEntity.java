package world.entity;

public class CountryInputEntity {
  private String code;
  private String code2;
  private String name;
  private String continent;
  private Long population;
  
  public CountryInputEntity(String code, String code2, String name) {
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
  public Long getPopulation() {
    return population;
  }
  public void setPopulation(Long population) {
    this.population = population;
  }
  
  /**
   * Checks to see if the required data has been entered or provided.
   * @return True if valid, false if otherwise.
   */
  public boolean isValid() {
    // if ((getCode() == null) || (getCode().isEmpty())) {
    //   return false;
    // }
    // if ((getCode2() == null) || (getCode2().isEmpty())) {
    //   return false;
    // }
    // if ((getName() == null) || (getName().isEmpty())) {
    //   return false;
    // }
    // return true;
    
    return (getCode() != null && ! getCode().isEmpty() ||
            getCode2() != null && ! getCode2().isEmpty() || 
            getName() != null && ! getName().isEmpty());
  }
}
