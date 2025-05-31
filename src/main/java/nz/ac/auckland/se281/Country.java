package nz.ac.auckland.se281;

public class Country {
  private String name;
  private Integer fuelCost;

  public Country(String name, Integer fuelCost) {
    this.name = name;
    this.fuelCost = fuelCost;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getFuelCost() {
    return fuelCost;
  }

  public void setFuelCost(Integer fuelCost) {
    this.fuelCost = fuelCost;
  }
}
