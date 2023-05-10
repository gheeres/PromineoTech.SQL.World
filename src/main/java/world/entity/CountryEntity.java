package world.entity;

/**
 * Represents a country of the world.
 */
public class CountryEntity {
  private String code;
  private String code2;
  private String name;
  
  /**
   * Creates an instance of the CountryEntity class.
   * @param code The unique id of the country.
   */
  public CountryEntity(String code) {
    this.code = code;
  }
  
  public String getCode() {
	return code;
  }
  public CountryEntity setCode(String code) {
  	this.code = code;
  	return this;
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
}
