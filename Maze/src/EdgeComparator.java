import java.util.Comparator;

// A Comparator to compare two graph edges for the purpose of
// sorting a list of edges for Kruskal's algorithm
class EdgeComparator implements Comparator<Edge> {
  // Compares two edges by subtracting their weights
  public int compare(Edge edge1, Edge edge2) {
    return edge1.weight - edge2.weight;
  }
}