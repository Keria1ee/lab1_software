package com.src;
import org.apache.commons.cli.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("h", "help", false, "Print help");
        options.addOption("v", "version", false, "Print version");
        options.addOption("f", "file", true, "File to process");
        options.addOption("m","minimum_path",true,"Minimum path between two nodes");
        options.addOption("w","wander_graph",false,"Wander graph");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        while(true) {
            try {
                if (cmd.hasOption("h")) {
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("Main", options);
                    cmd = parser.parse(options, new String[0]);
                } else if (cmd.hasOption("v")) {
                    System.out.println("Main 1.0");
                    cmd = parser.parse(options, new String[0]);

                } else if(cmd.hasOption("m") && cmd.hasOption("f")){
                    TextToGraph textToGraph = getTextGraphFromCmd(cmd);
                    String[] strings = cmd.getOptionValue("m").split(",");

                    if (strings.length==2) {
                        String node1 = strings[0];
                        String node2 = strings[1];
                        System.out.println("--------------------------------------------");
                        textToGraph.MinimumPath(node1, node2);
                    } else if (strings.length==1) {
                        String node1 = strings[0];
                        textToGraph.MinimumPath(node1);
                    } else {
                        System.out.println("Please input node(s) to find the minimum path");
                    }
                    cmd = parser.parse(options, new String[0]);
                } else if (cmd.hasOption("w") && cmd.hasOption("f")) {
                    TextToGraph textToGraph = getTextGraphFromCmd(cmd);
                    textToGraph.WanderGraph();
                    cmd = parser.parse(options, new String[0]);

                } else if (cmd.hasOption("f")) {
                    getTextGraphFromCmd(cmd);
                    cmd = parser.parse(options, new String[0]);
                }
                else {
                    System.out.print("New command:");
                    //从命令行scanner输入作为参数
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();
                    String[] inputArgs = input.split(" ");
                    cmd = parser.parse(options, inputArgs);
                }
            } catch (ParseException e) {
                System.err.println("Parsing failed.  Reason: " + e.getMessage());
            }
        }
    }

    private static TextToGraph getTextGraphFromCmd(CommandLine cmd) {
        System.out.println("Processing file: " + cmd.getOptionValue("f"));
        String filepath = cmd.getOptionValue("f");
        TextToGraph textToGraph = new TextToGraph();
        textToGraph.BuildGraph(filepath);
        return textToGraph;
    }
}