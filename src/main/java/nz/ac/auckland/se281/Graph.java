package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph<T> {

  private Map<T, List<T>> adjNodes = new HashMap<>();

  public void addNode(T node) {
    adjNodes.putIfAbsent(node, new ArrayList<T>());
  }

  public void addEdge(T node1, T node2) {
    if (node1.equals(node2)) return;
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

  public List<T> bfsShortestPath(T root, T destination) {
    List<T> visited = new ArrayList<>();
    Queue<List<T>> queue = new LinkedList<>();
    List<T> rootPathList = new ArrayList<>();
    rootPathList.add(root);
    queue.add(rootPathList);
    visited.add(root);

    while (!queue.isEmpty()) {
      List<T> path = queue.poll();
      T node = path.getLast();

      if (node.equals(destination)) {
        return path;
      }

      for (T n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          List<T> newPath = new ArrayList<>(path);
          newPath.add(n);
          queue.add(newPath);
        }
      }
    }
    return null;
  }
}
