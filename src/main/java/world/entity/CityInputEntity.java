package world.entity;

public class CityInputEntity {
  private String countryCode;
  private String name;
  private Double latitude;
  private Double longitude;
  private Long population;
  
  public CityInputEntity(String countryCode, String name) {
    setCountryCode(countryCode);
    setName(name);
  }
  
  public String getCountryCode() {
    return countryCode;
  }
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Double getLatitude() {
    return latitude;
  }
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
  public Double getLongitude() {
    return longitude;
  }
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
  public Long getPopulation() {
    return population;
  }
  public void setPopulation(Long population) {
    this.population = population;
  }
  
  /**
   * Checks to see if the minimum required field are available.
   * @return True if valid, false if otherwise.
   */
  public boolean isValid() {
    return (getCountryCode() != null) && (! getCountryCode().isEmpty()) &&
           (getName() != null) || (! getName().isEmpty());
  }
}
