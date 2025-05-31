package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private Graph<Country> graph;
  private HashMap<String, Country> countryNameMap;

  public MapEngine() {
    this.countryNameMap = new HashMap<>();
    this.graph = new Graph<>();
    loadMap();
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    HashSet<String> createdCountryNames = new HashSet<String>();

    for (int i = 0; i < countries.size() - 1; i++) {
      String[] countryInfo = countries.get(i).split(",");
      String countryName = countryInfo[0];
      String countryContinent = countryInfo[1];
      Integer countryFuelCost = Integer.parseInt(countryInfo[2]);
      String[] countryAdjacencies = adjacencies.get(i).split(",");

      Country newCountry = new Country(countryName, countryContinent, countryFuelCost);
      createdCountryNames.add(countryName);
      this.countryNameMap.put(countryName, newCountry);
      this.graph.addNode(newCountry);

      // Start generating correct-order adjacency string for printing later
      String adjacentCountriesString = "[";
      boolean firstCountryInString = true;

      for (int j = 1; j < countryAdjacencies.length; j++) {
        String adjacentCountryName = countryAdjacencies[j];
        // Add the name to the adjacency string
        if (!firstCountryInString) {
          adjacentCountriesString += ", ";
        } else {
          firstCountryInString = false;
        }
        adjacentCountriesString += adjacentCountryName;

        // Create an edge in the graph
        if (createdCountryNames.contains(adjacentCountryName)) {
          this.graph.addEdge(newCountry, this.countryNameMap.get(adjacentCountryName));
        }
      }
      adjacentCountriesString += "]";
      newCountry.setAdjacencyString(adjacentCountriesString);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String inputCountryName = Utils.scanner.nextLine();

    Country thisCountry = this.countryNameMap.get(inputCountryName);

    MessageCli.COUNTRY_INFO.printMessage(
        thisCountry.getName(),
        thisCountry.getContinent(),
        Integer.toString(thisCountry.getFuelCost()),
        thisCountry.getAdjacencyString());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
