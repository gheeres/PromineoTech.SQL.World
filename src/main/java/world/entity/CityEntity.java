package world.entity;

public class CityEntity {
  private Long id;
  private String countryCode;
  private String name;
  private Double latitude;
  private Double longitude;
  private Long population;
  
  public CityEntity(Long id, String countryCode, String name) {
    setId(id);
    setCountryCode(countryCode);
    setName(name);
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
}
