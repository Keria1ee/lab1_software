package com.src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.*;
import java.util.List;


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
            System.out.println("Words: " + Arrays.toString(words));
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

    public void DrawGraphics(){
        graph.DrawGraph();
    }



    /***
     * 从node1到node2的最短路径
     */
    public String calcShortestPath(String node1, String node2) {
        //如果graph未被初始化，内容为空
            if(graph.nodes.isEmpty()){
                System.out.println("Graph is empty, please build graph first");
                return null;
            }
            if(!graph.nodes.containsKey(node1)){
                System.out.println("Node1:"+node1+" not found in graph");
                return null;
            }
            if(!graph.nodes.containsKey(node2)){
                System.out.println("Node2:"+node2+" not found in graph");
                return null;
            }

            //迪杰斯特拉算法
            Map<String, Integer> distances = new HashMap<>();
            for (String node : graph.nodes.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
            }
            distances.put(node1, 0);

            Map<String, String> parent = new HashMap<>();
            parent.put(node1, null);

            PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
            queue.add(node1);

            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (current.equals(node2)) {
                    break;
                }
                for (Map.Entry<String, Integer> edge : graph.nodes.get(current).edges.entrySet()) {
                    String[] src_dest = edge.getKey().split("-");
                    String neighbor = src_dest[1];
                    int newDistance = distances.get(current) + edge.getValue();
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        parent.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

            if (!distances.containsKey(node2) || distances.get(node2) == Integer.MAX_VALUE) {
                System.out.println("No path found between " + node1 + " and " + node2);
                return null;
            }

            LinkedList<String> path = new LinkedList<>();
            String current = node2;
            while (current != null) {
                path.addFirst(current);
                current = parent.get(current);
            }
            return "Minimum path between " + node1 + " and " + node2 + " is: " + String.join("->", path);
    }

    /***
     * 从node1到所有节点的最短路径,重载方法
     */
    public String[] calcShortestPath(String node1){
        if(graph.nodes.isEmpty()){
            System.out.println("Graph is empty, please build graph first");
            return null;
        }
        if(!graph.nodes.containsKey(node1)){
            System.out.println("Node1:"+node1+" not found in graph");
            return null;
        }
        String[] result = new String[graph.nodes.size()];
        int count = 0;
        for (String node2 : graph.nodes.keySet()) {
            if (!node1.equals(node2)) {
                result[count++] = calcShortestPath(node1, node2);
            }
        }
        return result;
    }

    /***
     * 进入该功能时，程序随机的从图中选择一个节
     * 点，以此为起点沿出边进行随机遍历，记录经
     * 过的所有节点和边，直到出现第一条重复的边
     * 为止，或者进入的某个节点不存在出边为止。
     * 在遍历过程中，用户也可随时停止遍历。
     * ▪ 将遍历的节点输出为文本，并以文件形式写入
     * 磁盘。
     * no parameter
     */
    public String randomWalk() {
        if (graph.nodes.isEmpty()) {
            System.out.println("Graph is empty, please build graph first");
            return null;
        }
        Random random = new Random();
        List<String> nodes = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        String current = new ArrayList<>(graph.nodes.keySet()).get(random.nextInt(graph.nodes.size()));
        nodes.add(current);
        while (true) {
            if (!graph.nodes.containsKey(current)) {
                break;
            }
            List<String> neighbors = new ArrayList<>(graph.nodes.get(current).edges.keySet());//keySet()返回所有的键 为src-dest形式的字符串
            if (neighbors.isEmpty()) {
                break;
            }
            //出现第一条重复的边
            if (new HashSet<>(edges).containsAll(neighbors)) {
                break;
            }
            String next = neighbors.get(random.nextInt(neighbors.size()));
            String[] src_dest = next.split("-");
            String neighbor = src_dest[1];
            nodes.add(neighbor);
            edges.add(next);
            current = neighbor;
            if (nodes.size() % 10 == 0) {
                System.out.println("Nodes: " + nodes);
                System.out.println("Edges: " + edges);
                System.out.println("Continue? (y/n)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (!input.equals("y")) {
                    break;
                }
            }
        }
        //将遍历的节点组织为句子
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            builder.append(nodes.get(i));
            if (i < nodes.size() - 1) {
                builder.append(" ");
            }
        }
        //写文件
        try {
            String filename = "random_walk.txt";
            System.out.println("Writing to file: " + filename);
            FileWriter writer = new FileWriter(filename);
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
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
                if (bridgeWords.contains(",")) {
                    //debug:多个桥接词，随机选择一个
                    String[] bridgeWordsArray = bridgeWords.split(", ");
                    builder.append(bridgeWordsArray[new Random().nextInt(bridgeWordsArray.length)]).append(" ");
                } else {
                    builder.append(bridgeWords).append(" ");
                }
            }
        }
        builder.append(words[words.length - 1]);
        return builder.toString();
    }



}
