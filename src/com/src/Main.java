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

                } else if(cmd.hasOption("m")){
                    System.out.println("Processing file: " + cmd.getOptionValue("f"));
                    String filepath = cmd.getOptionValue("f");
                    TextToGraph textToGraph = new TextToGraph();
                    textToGraph.BuildGraph(filepath);
                    String node1 = cmd.getOptionValue("m").split(",")[0];
                    String node2 = cmd.getOptionValue("m").split(",")[1];
                    System.out.println("--------------------------------------------");
                    textToGraph.MinimumPath(node1,node2);
                    cmd = parser.parse(options, new String[0]);
                } else if (cmd.hasOption("f")) {
                    System.out.println("Processing file: " + cmd.getOptionValue("f"));
                    String filepath = cmd.getOptionValue("f");
                    TextToGraph textToGraph = new TextToGraph();
                    textToGraph.BuildGraph(filepath);
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
}