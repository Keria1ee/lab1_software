package com.src;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
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
    public void DrawGraph() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        Map<String, Object> vertexMap = new HashMap<>(); // Map to store vertices
//        int nodeCount = nodes.size();
//        int rows = (int) Math.sqrt(nodeCount); // Calculate the number of rows in the grid
//        int cols = nodeCount / rows; // Calculate the number of columns in the grid
//        int i = 0;
        try {
            for (Node node : nodes.values()) { // Add nodes, parent, id, value, x, y, width, height
                //parent means the parent cell in the model
//                int x = (i % cols) * 800 / cols; // Calculate the x position of the node
//                int y = (i / cols) * 600 / rows; // Calculate the y position of the node
                Object vertex = graph.insertVertex(parent, null, node.name, 20, 20, 80, 30);
                vertexMap.put(node.name, vertex);
//                i++;
            }
            for (Node node : nodes.values()) { // Add edges
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
        mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);
        layout.setForceConstant(100);
        layout.execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);//Create a new component for the graph
        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);



    }

}

