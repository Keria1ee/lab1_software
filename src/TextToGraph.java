import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
