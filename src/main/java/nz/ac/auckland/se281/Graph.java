package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {

  private Map<T, List<T>> adjNodes = new HashMap<>();

  public void addNode(T node) {
    adjNodes.putIfAbsent(node, new ArrayList<T>());
  }

  public void addEdge(T node1, T node2) {
    addNode(node1);
    addNode(node2);
    adjNodes.get(node1).add(node2);
    adjNodes.get(node2).add(node1);
  }

  public void removeNode(T node) {
    adjNodes.remove(node);
    for (T n : adjNodes.keySet()) {
      adjNodes.get(n).remove(node);
    }
  }

  public void removeEdge(T node1, T node2) {
    adjNodes.get(node1).remove(node2);
    adjNodes.get(node2).remove(node1);
  }

  public List<T> getAdjacentNodes(T node) {
    return adjNodes.get(node);
  }
}
