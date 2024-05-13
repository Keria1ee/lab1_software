package com.src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TextToGraph {
    private Graph graph;

    public TextToGraph() {
        graph = new Graph();
    }
    public void BuildGraph(String filepath){
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append(line).append(" ");
            }
            String[] words = builder.toString().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            for (int i = 0; i < words.length -1; i++) {
                String word_1 = words[i];
                String word_2 = words[i+1];
                graph.AddNode(word_1);
                graph.AddNode(word_2);
                graph.AddEdge(word_1, word_2, 1);
            }
            graph.PrintGraph();
            graph.DrawGraph();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    String queryBridgeWords(String word1, String word2) {

        Map<String, Node> nodes = graph.nodes;

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

    String generateNewText(String inputText){
        String[] words = inputText.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length -1; i++) {
            String word_1 = words[i];
            String word_2 = words[i+1];
            builder.append(word_1).append(" ");
            String bridgeWords = queryBridgeWords(word_1, word_2);
            if (bridgeWords != null) {
                builder.append(bridgeWords).append(" ");
            }
        }
        builder.append(words[words.length - 1]);
        return builder.toString();
    }
}
