package world.entity;

/**
 * Represents a city.
 */
public class CityEntity {
  private Long id;
  private String country;
  private String name;
  private Double latitude;
  private Double longitude;
  private Long population;
  
  /**
   * Creates an instance of the CityEntity class.
   * @param id The unique id of the city.
   * @param country The ISO3316 code for the country that the city resides in.
   * @param name The name of the city.
   */
  public CityEntity(Long id, String country, String name) {
    setId(id);
    setCountry(country);
    setName(name);
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
}
