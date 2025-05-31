package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private Integer fuelCost;
  private String adjacencyString;

  public Country(String name, String continent, Integer fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.adjacencyString = "";
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

  public String getContinent() {
    return continent;
  }

  public void setContinent(String continent) {
    this.continent = continent;
  }

  public String getAdjacencyString() {
    return adjacencyString;
  }

  public void setAdjacencyString(String adjacencyString) {
    this.adjacencyString = adjacencyString;
  }
}
