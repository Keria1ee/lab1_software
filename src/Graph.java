import java.util.HashMap;
import java.util.Map;
class Node{
    String name;
    HashMap<String, Integer> edges;

    public Node(String name) {
        this.name = name;
        edges = new HashMap<>();
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
        String key = src + "-" + dest;
        if (nodes.get(src).edges.containsKey(key)) {
            nodes.get(src).edges.put(key, nodes.get(src).edges.get(key) + weight);
        } else {
            nodes.get(src).edges.put(key, weight);
        }
    }
    public void PrintGraph() {
        for (Node node : nodes.values()) {
            System.out.println("Node: " + node.name);
            for (Map.Entry<String, Integer> edge : node.edges.entrySet()) {
                System.out.println("Edge: " + edge.getKey() + " Weight: " + edge.getValue());
            }
        }
    }
}

