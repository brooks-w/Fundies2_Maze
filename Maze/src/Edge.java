// To represent an edge in a graph
class Edge {
  // The nodes this edge connects
  Node from;
  Node to;

  // The weight of this edge
  int weight;

  // Constructor
  Edge(Node from, Node to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }
}