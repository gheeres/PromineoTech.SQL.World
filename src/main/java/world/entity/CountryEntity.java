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
  
  public CountryEntity(CountryEntity existing) {
    // this(existing.getCode());
	if (existing != null) {
      code = existing.getCode();
	  code2 = existing.getCode2();
	  name = existing.getName();
	}
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
  
  /**
   * Checks to see if the values are correct or valid.
   * @return True if valid, false if otherwise.
   */
  public boolean isValid() {
	if (getCode() == null) return false;
	if (getCode().isEmpty()) return false;
	if (getCode2() == null) return false;
	if (getCode2().isEmpty()) return false;
	if (getName() == null) return false;
	if (getName().isEmpty()) return false;
	
	return true;
    //return (getCode() != null) && (! getCode().isBlank()) &&
    //       (getCode2() != null) && (! getCode2().isBlank()) &&
    //	     (getName() != null) && (! getName().isBlank());
  }
}
