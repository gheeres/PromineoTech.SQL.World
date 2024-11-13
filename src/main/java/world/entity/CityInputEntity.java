package world.entity;

public class CityInputEntity {
  private String country;
  private String name;
  private Double latitude;
  private Double longitude;
  private Long population;
  
  public CityInputEntity(String country, String name) {
    setCountry(country);
    setName(name);
  }
  
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
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
  
  public boolean isValid() {
    return ((getName() == null) || (getName().isEmpty()));
  }
}
