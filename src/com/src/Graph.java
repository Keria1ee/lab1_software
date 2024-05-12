package com.src;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    String queryBridgeWords(String word1, String word2) {
        if (!nodes.containsKey(word1)) {
            System.out.println("Word1 not found");
            return null;
        }
        if (!nodes.containsKey(word2)) {
            System.out.println("Word2 not found");
            return null;
        }
        List<String> bridgeWords = new ArrayList<>();
        Node node1 = nodes.get(word1);

        for (Map.Entry<String, Integer> edge : node1.edges.entrySet()) {
            String[] src_dest = edge.getKey().split("-");
            String bridge_word = src_dest[1];
            if (nodes.get(bridge_word).edges.containsKey(bridge_word + "-" + word2)) {
                bridgeWords.add(bridge_word);
            }
        }
        if (!bridgeWords.isEmpty()){
            return String.join(", ", bridgeWords);
        }
        return null;
    }
}

