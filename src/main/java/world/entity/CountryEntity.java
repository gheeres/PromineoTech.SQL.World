package world.entity;

public class CountryEntity {
  private String code2;
  private String name;
  
  public CountryEntity(String code2, String name) {
    setCode2(code2); // this.code2 = code2;
    setName(name);
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
}
