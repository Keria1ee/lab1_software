import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Edge {
    String src, dest;
    int weight;

    public Edge(String src, String dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Node{
    String name;
    List<Edge> edges;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
}

class Graph {
    Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void AddNode(String name) {
        if (!nodes.containsKey(name)) {
            nodes.put(name, new Node(name));
        }
    }

    public void AddEdge(String src, String dest, int weight) {
        if (!nodes.containsKey(src)) {
            AddNode(src);
        }
        if (!nodes.containsKey(dest)) {
            AddNode(dest);
        }
        nodes.get(src).edges.add(new Edge(src, dest, weight));
    }

    public List<Edge> GetEdges(String name) {
        if (nodes.containsKey(name)) {
            return nodes.get(name).edges;
        }
        return null;
    }
}

