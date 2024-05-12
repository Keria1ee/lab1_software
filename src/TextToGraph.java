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
            System.out.println(Arrays.toString(words));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
