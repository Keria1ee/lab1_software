package com.src;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import java.awt.BorderLayout;



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

    public  boolean hasNode(String name){
        return nodes.containsKey(name);
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
    public void drawGraph() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        Map<String, Object> vertexMap = new HashMap<>();
        try {
            for (Node node : nodes.values()) {
                Object vertex = graph.insertVertex(parent, null, node.name, 35, 35, 70, 70);
                vertexMap.put(node.name, vertex);
            }
            for (Node node : nodes.values()) {
                for (Map.Entry<String, Integer> edge : node.edges.entrySet()) {
                    String[] src_dest = edge.getKey().split("-");
                    Object v1 = vertexMap.get(src_dest[0]);
                    Object v2 = vertexMap.get(src_dest[1]);
                    graph.insertEdge(parent, null, edge.getValue(), v1, v2);
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }

        // layout
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.setVisible(true);

    }


}

