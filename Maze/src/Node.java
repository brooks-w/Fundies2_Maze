import java.util.ArrayList;

// To represent a node in a graph
class Node {
  // The edges this coming from this node
  ArrayList<Edge> outEdges;

  // Constructor
  Node() {
    outEdges = new ArrayList<Edge>();
  }

  // Adds the given edge to this node's list of edges
  void addEdge(Edge edge) {
    this.outEdges.add(edge);
  }

  // Removes the given edge from this node's list of edges
  void removeEdge(Edge edge) {
    this.outEdges.remove(edge);
  }

  // Determines whether there is an edge connecting this node
  // and the given node
  public boolean edgeTo(Node that) {
    boolean nodeTo = false;

    for (Edge e : this.outEdges) {
      if ((e.to == that && e.from == this) || (e.to == this && e.from == that)) {
        nodeTo = true;
        break;
      }
    }

    return nodeTo;
  }
}