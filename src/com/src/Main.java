package com.src;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "Print help");
        options.addOption("v", "version", false, "Print version");
        options.addOption("f", "file", true, "File to process");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Main", options);
            } else if (cmd.hasOption("v")) {
                System.out.println("Software Lab 1.0.0");
            } else if(cmd.hasOption("f")) {
                System.out.println("Processing file: " + cmd.getOptionValue("f"));
                String filepath = cmd.getOptionValue("f");
                TextToGraph textToGraph = new TextToGraph();
                textToGraph.buildGraph(filepath);
                Menu(textToGraph);
            } else
            {
                System.out.println("Hello, world!");
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }


    static String queryBridgeWords(TextToGraph textToGraph, String word1, String word2){

        Map<String, Node> nodes = textToGraph.graph.nodes;

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

    static void createDirectedGraph(String filepath){
        TextToGraph textToGraph = new TextToGraph();
        textToGraph.buildGraph(filepath);
    }


    static  String randomWalk(TextToGraph G){
        return G.randomWalk();
    }

    static String calcShortestPath(TextToGraph G, String word1, String word2){
        if (G == null){
            System.out.println("Graph is empty, please build graph first");
            return null;
        }
            return G.calcShortestPath(word1, word2);
    }






    private static void Menu(TextToGraph textToGraph){
        while (true){
            System.out.println("1. Show Graph");
            System.out.println("2. Search Bridge Words");
            System.out.println("3. Generate New Text");
            System.out.println("4. Show Shortest Path");
            System.out.println("5. Random Walk");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            //边界处理
            while (!scanner.hasNextInt()){
                System.out.println("Invalid choice, please enter a number(choice 1-6):");
                scanner.next();
            }
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    textToGraph.drawGraphics();
                    break;
                case 2:
                    System.out.println("Enter word1:");
                    String word1 = scanner.next();
                    System.out.println("Enter word2:");
                    String word2 = scanner.next();
                    String result = queryBridgeWords(textToGraph,word1, word2);
                    if (result != null)
                        System.out.println("The bridge words from "+ word1 + " to "+ word2 + ":"+ result);
                    else
                        System.out.println("No bridge words from "+ word1 +" to "+ word2);
                    System.out.println();
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Enter text:");
                    String text = scanner.nextLine();
                    System.out.println("New text: " + textToGraph.generateNewText(text));
                    System.out.println();
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.println("Enter word1:");
                    String node1 = scanner.nextLine();
                    System.out.println("Enter word2(can be null to calc all shortest path to all nodes):");
                    String node2 = scanner.nextLine();

                    if (!Objects.equals(node2, "") && node2!=null &&textToGraph.calcShortestPath(node1, node2) != null) {
                        System.out.println(textToGraph.calcShortestPath(node1, node2));
                    } else if(Objects.equals(node2, "") || node2==null){
                        String[] result1 = textToGraph.calcShortestPath(node1);
                        if (result1 != null) {
                            for (String s : result1) {
                                System.out.println(s);
                            }
                        }
                    }
                    else {
                        System.out.println("No path from " + node1 + " to " + node2);
                    }
                    break;
                case 5:
                    System.out.println(textToGraph.randomWalk());
                    System.out.println();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

