package world.entity;

/**
 * Represents a city.
 */
public class CityEntity {
  private int id;
  private CountryEntity country;
  private String name;
  private Float latitude;
  private Float longitude;
  private Integer population;
  
  public CityEntity(int id) {
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  public CountryEntity getCountry() {
    return country;
  }
  public CityEntity setCountry(CountryEntity country) {
    this.country = country;
    return this;
  }
  public String getName() {
    return name;
  }
  public CityEntity setName(String name) {
    this.name = name;
    return this;
  }
  public Float getLatitude() {
    return latitude;
  }
  public CityEntity setLatitude(Float latitude) {
    this.latitude = latitude;
    return this;
  }
  public Float getLongitude() {
    return longitude;
  }
  public CityEntity setLongitude(Float longitude) {
    this.longitude = longitude;
    return this;
  }
  public Integer getPopulation() {
    return population;
  }
  public CityEntity setPopulation(Integer population) {
    this.population = population;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("[%d] %s (lat: %6.4f, long: %6.4f)",
                         getId(), getName(), 
                         getLatitude(), getLongitude());
  }
}
