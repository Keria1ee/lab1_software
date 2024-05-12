import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TextToGraph {
    private final Map<String, Integer> graph;

    public TextToGraph() {
        graph = new HashMap<>();
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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
