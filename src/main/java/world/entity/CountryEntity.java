package world.entity;

/**
 * Represents a country in the world.
 */
public class CountryEntity {
  private String code;
  private String code2;
  private String name;

  public CountryEntity(String code) {
    this.code = code;
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

  public String getCode() {
    return code;
  }
  
  @Override
  public String toString() {
    return String.format("[%s] %s", getCode(), getName());
  }
}
