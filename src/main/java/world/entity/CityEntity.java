package world.entity;

public class CityEntity {
  private Integer id;
  private String name;
  private Integer population;
  
  public CityEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public CityEntity setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public CityEntity setName(String name) {
    this.name = name;
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
    return String.format("%s (%d)", getName(), getId());
  }
}
