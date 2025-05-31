package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private HashMap<String, Country> countryNameMap;

  public MapEngine() {
    this.countryNameMap = new HashMap<>();
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
      Integer countryFuelCost = Integer.getInteger(countryInfo[2]);
      String[] countryAdjacencies = adjacencies.get(i).split(",");

      Country newCountry = new Country(countryName, countryContinent, countryFuelCost);
      createdCountryNames.add(countryName);
      countryNameMap.put(countryName, newCountry);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {}

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
