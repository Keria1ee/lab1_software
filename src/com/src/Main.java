package com.src;
import org.apache.commons.cli.*;

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
                System.out.println("Main 1.0");
            } else if(cmd.hasOption("f")) {
                System.out.println("Processing file: " + cmd.getOptionValue("f"));
                String filepath = cmd.getOptionValue("f");
                TextToGraph textToGraph = new TextToGraph();
                textToGraph.BuildGraph(filepath);
            } else
            {
                System.out.println("Hello, world!");
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }
}