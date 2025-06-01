package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
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

    for (int i = 0; i < countries.size(); i++) {
      String[] countryInfo = countries.get(i).split(",");
      String countryName = countryInfo[0];
      String countryContinent = countryInfo[1];
      Integer countryFuelCost = Integer.parseInt(countryInfo[2]);

      Country newCountry = new Country(countryName, countryContinent, countryFuelCost);
      this.countryNameMap.put(countryName, newCountry);
      this.graph.addNode(newCountry);
    }

    for (int i = 1; i < countries.size(); i++) {
      String[] countryInfo = countries.get(i).split(",");
      String countryName = countryInfo[0];
      Country thisCountry = this.countryNameMap.get(countryName);
      String[] countryAdjacencies = adjacencies.get(i).split(",");

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

        this.graph.addEdge(thisCountry, this.countryNameMap.get(adjacentCountryName));
      }
      adjacentCountriesString += "]";
      thisCountry.setAdjacencyString(adjacentCountriesString);
    }
    List<Country> siamList = this.graph.getAdjacentNodes(this.countryNameMap.get("Siam"));
    for (Country c : siamList) {
      System.out.println(c.getName());
    }
  }

  private String getInputCountryName() throws InvalidCountryException {
    String inputCountryName = Utils.scanner.nextLine();

    // Correct lowercase first letter of each word to be uppercase
    boolean newWord = true;
    for (int i = 0; i < inputCountryName.length(); i++) {
      char thisChar = inputCountryName.charAt(i);
      if (newWord && Character.isLowerCase(thisChar)) {
        // Make the letter uppercase
        char[] charArray = inputCountryName.toCharArray();
        charArray[i] = Character.toUpperCase(thisChar);
        inputCountryName = String.valueOf(charArray);
      }

      // If the current char is a space then flag the next
      // iteration to be the start of a new word
      newWord = (thisChar == ' ');
    }

    if (this.countryNameMap.get(inputCountryName) == null) {
      throw new InvalidCountryException(inputCountryName);
    }

    return inputCountryName;
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();

    String inputCountryName;

    while (true) {
      try {
        inputCountryName = getInputCountryName();
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
        MessageCli.INSERT_COUNTRY.printMessage();
        continue;
      }
      break;
    }

    Country thisCountry = this.countryNameMap.get(inputCountryName);

    MessageCli.COUNTRY_INFO.printMessage(
        thisCountry.getName(),
        thisCountry.getContinent(),
        Integer.toString(thisCountry.getFuelCost()),
        thisCountry.getAdjacencyString());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // Prompt for the starting country name
    MessageCli.INSERT_SOURCE.printMessage();
    String startingCountryName;
    while (true) {
      try {
        startingCountryName = getInputCountryName();
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
        continue;
      }
      break;
    }
    Country startingCountry = this.countryNameMap.get(startingCountryName);

    // Prompt for the destination country name
    MessageCli.INSERT_DESTINATION.printMessage();
    String endingCountryName;
    while (true) {
      try {
        endingCountryName = getInputCountryName();
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
        continue;
      }
      break;
    }
    Country endingCountry = this.countryNameMap.get(endingCountryName);

    if (startingCountryName.equals(endingCountryName)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    List<Country> path = this.graph.bfsShortestPath(startingCountry, endingCountry);

    // Create string of fastest route from source to destination from path list
    String pathString = "[";
    Boolean firstNodeInPath = true;
    for (int i = 0; i < path.size(); i++) {
      if (!firstNodeInPath) {
        pathString += ", ";
      } else {
        firstNodeInPath = false;
      }
      pathString += path.get(i).getName();
    }
    pathString += "]";
    MessageCli.ROUTE_INFO.printMessage(pathString);

    // Print the continents passed through and their fuel cost
    List<String> continents = new ArrayList<>();
    List<Integer> continentFuel = new ArrayList<>();
    for (int i = 0; i < path.size(); i++) {
      Country thisCountry = path.get(i);
      String continent = thisCountry.getContinent();
      if (continents.contains(continent)) {
        int index = continents.indexOf(continent);
        if (i != 0 && i != path.size() - 1) {
          continentFuel.set(index, continentFuel.get(index) + thisCountry.getFuelCost());
        }
      } else {
        continents.add(continent);
        if (i != 0 && i != path.size() - 1) {
          continentFuel.add(thisCountry.getFuelCost());
        } else {
          continentFuel.add(0);
        }
      }
    }
    String continentString = "[";
    for (int i = 0; i < continents.size(); i++) {
      continentString += continents.get(i);
      continentString += " (";
      continentString += Integer.toString(continentFuel.get(i));
      continentString += ")";
      if (i != continents.size() - 1) {
        continentString += ", ";
      }
    }
    continentString += "]";
    MessageCli.CONTINENT_INFO.printMessage(continentString);
  }
}
