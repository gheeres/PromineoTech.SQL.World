package world.entity;

/**
 * The basic / minimal information about a country.
 */
public class CountryBaseEntity {
  private String code;
  private String name;
  
  public CountryBaseEntity(String code) {
    this.code = code;
  }
  
  public CountryBaseEntity(CountryBaseEntity country) {
    if (country != null) {
      this.code = country.getCode();
      setName(country.getName());
    }
  }

  public String getName() {
    return name;
  }

  public CountryBaseEntity setName(String name) {
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
