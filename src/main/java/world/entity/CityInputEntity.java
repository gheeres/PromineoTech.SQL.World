package world.entity;

public class CityInputEntity extends Entity {
  private String name;
  private Integer population;
  
  public CityInputEntity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }
}
