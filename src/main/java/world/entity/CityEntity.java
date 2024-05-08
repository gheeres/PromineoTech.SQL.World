package world.entity;

public class CityEntity extends Entity {
  private Integer id;
  private String name;
  private Double latitude;
  private Double longitude;
  private Integer population;
  
  public CityEntity(String name) {
    this.name = name;
  }
  
  public CityEntity(Integer id, String name) {
    this(name);
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }
}
