package world.entity;

/**
 * Represents a city.
 */
public class CityInputEntity {
  private String country;
  private String name;
  private Float latitude;
  private Float longitude;
  private Integer population;
  
  public CityInputEntity() {
  }
  
  public String getCountry() {
    return country;
  }
  public CityInputEntity setCountry(String country) {
    this.country = country;
    return this;
  }
  public String getName() {
    return name;
  }
  public CityInputEntity setName(String name) {
    this.name = name;
    return this;
  }
  public Float getLatitude() {
    return latitude;
  }
  public CityInputEntity setLatitude(Float latitude) {
    this.latitude = latitude;
    return this;
  }
  public Float getLongitude() {
    return longitude;
  }
  public CityInputEntity setLongitude(Float longitude) {
    this.longitude = longitude;
    return this;
  }
  public Integer getPopulation() {
    return population;
  }
  public CityInputEntity setPopulation(Integer population) {
    this.population = population;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("[NEW] %s (lat: %6.4f, long: %6.4f)",
                         getName(), 
                         getLatitude(), getLongitude());
  }
}
