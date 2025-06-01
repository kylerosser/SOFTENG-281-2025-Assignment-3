package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {

  private Map<T, List<T>> adjNodes = new HashMap<>();

  public void addNode(T node) {
    adjNodes.putIfAbsent(node, new ArrayList<T>());
  }

  public void addEdge(T node1, T node2) {
    if (node1.equals(node2)) {
      return;
    }
    addNode(node1);
    addNode(node2);
    if (adjNodes.get(node1).contains(node2)) {
      return;
    }
    adjNodes.get(node1).add(node2);
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
    // Handle the case of the root equalling the destination
    if (root.equals(destination)) {
      List<T> path = new ArrayList<>();
      path.add(root);
      return path;
    }

    // Track visited nodes
    Set<T> visited = new HashSet<>();

    // A queue of nodes to visit + the path leading up to that node
    // (we visit nodes in the order we encounter them/breadth first.)
    Queue<List<T>> queue = new LinkedList<>();

    // Assign the first path to the queue (only contains root node)
    List<T> rootPathList = new ArrayList<>();
    rootPathList.add(root);
    queue.add(rootPathList);

    // Mark the root node as visited
    visited.add(root);

    while (!queue.isEmpty()) {
      List<T> path = queue.poll();
      T node = path.getLast();
      System.out.println();

      // We have found the destination, return the shortest path
      if (node.equals(destination)) {
        return path;
      }

      for (T n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          // Add a new path to the queue with this adjacent node included
          List<T> newPath = new ArrayList<>(path);
          newPath.add(n);
          queue.add(newPath);
        }
      }
    }
    return null;
  }
}
