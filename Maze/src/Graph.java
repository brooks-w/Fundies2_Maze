import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// To represent an undirected graph, including both the graph from which the
// minimum spanning tree is created, as well as the minimum spanning tree itself
class Graph {
  // The nodes in this graph
  ArrayList<Node> nodes;
  // The edges in this graph
  ArrayList<Edge> edges;

  // Constructor that makes a new random rectangular graph with the given
  // width and height from the given random number generator
  Graph(int width, int height, Random rdm) {
    // Throw an exception if the graph dimensions are too small
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException(
              "Invalid graph dimensions: " + width + "x" + height);
    }

    nodes = new ArrayList<Node>();
    edges = new ArrayList<Edge>();

    // Filling the graph with nodes
    for (int i = 0; i < width * height; i++) {
      this.nodes.add(new Node());
    }

    // Connecting each node to its adjacent nodes
    for (int i = 0; i < width * height; i++) {
      if ((i + 1) % width != 0) {
        this.connect(i, i + 1, rdm);
      }
    }

    for (int i = 0; i < width * (height - 1); i++) {
      this.connect(i, i + width, rdm);
    }
  }

  // Returns the node at the given index in this graph's list of nodes
  public Node getNode(int index) {
    return this.nodes.get(index);
  }

  // Connects the two nodes in this graph indexed at the given integers with an edge
  void connect(int index1, int index2, Random rdm) {
    if ((index1 >= 0 && index1 < this.nodes.size())
            && (index2 >= 0 && index2 < this.nodes.size())) {
      Node node1 = this.nodes.get(index1);
      Node node2 = this.nodes.get(index2);

      Edge nextEdge = new Edge(node1, node2, rdm.nextInt(100000));

      this.edges.add(nextEdge);

      node1.addEdge(nextEdge);
      node2.addEdge(nextEdge);
    }
  }

  // Determines whether there is an edge between the nodes
  // indexed at the two given integers
  public boolean edgeBetween(int index1, int index2) {
    if ((index1 >= 0 && index1 < this.nodes.size())
            && (index2 >= 0 && index2 < this.nodes.size())) {
      Node node1 = this.nodes.get(index1);
      Node node2 = this.nodes.get(index2);

      return node1.edgeTo(node2);
    }

    return false;
  }

  // Creates a minimum spanning tree from this graph using Kruskal's algorithm
  void kruskal() {
    // Creating a sorted list of the edges in this graph
    ArrayList<Edge> sortedEdges = new ArrayList<Edge>(this.edges);
    sortedEdges.sort(new EdgeComparator());

    // The final list of edges in the MST
    ArrayList<Edge> treeEdges = new ArrayList<Edge>();

    // Creating a HashMap that maps the nodes in this graph to their representative
    HashMap<Integer, Integer> representatives = new HashMap<Integer, Integer>();

    for (int i = 0; i < this.nodes.size(); i++) {
      representatives.put(i, i);
    }

    // The next edge to be considered for the MST
    Edge nextEdge;

    Integer fromRep;
    Integer toRep;

    while (treeEdges.size() < this.nodes.size() - 1) {
      nextEdge = sortedEdges.remove(0);

      // Getting the representatives of the nodes on each side of the edge
      fromRep = representatives.get(this.nodes.indexOf(nextEdge.from));
      while (fromRep != representatives.get(fromRep)) {
        fromRep = representatives.get(fromRep);
      }

      toRep = representatives.get(this.nodes.indexOf(nextEdge.to));
      while (toRep != representatives.get(toRep)) {
        toRep = representatives.get(toRep);
      }

      // Adding the edge to the MST if it does not create a loop
      if (fromRep != toRep) {
        treeEdges.add(nextEdge);
        representatives.replace(fromRep, toRep);
      }
      else {
        // Removing edges that will not appear in the MST
        nextEdge.from.removeEdge(nextEdge);
        nextEdge.to.removeEdge(nextEdge);
      }
    }

    // Removing the edges that remain in the list
    for (Edge e : sortedEdges) {
      e.from.removeEdge(e);
      e.to.removeEdge(e);
    }

    this.edges = treeEdges;
  }
}