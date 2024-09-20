package world.entity;

public class InputCityEntity extends Entity {
  private String name;
  private Integer population;
  
  public InputCityEntity(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public InputCityEntity setName(String name) {
    this.name = name;
    return this;
  }

  public Integer getPopulation() {
    return population;
  }

  public InputCityEntity setPopulation(Integer population) {
    this.population = population;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("%s", getName());
  }
  
  /**
   * Checks to see if the data if valid.
   * @return True if valid, false if otherwise.
   */
  public boolean isValid() {
    return (this.getName() != null) && (! this.getName().isBlank()) &&
           (this.getPopulation() > 0);   
  }
}
