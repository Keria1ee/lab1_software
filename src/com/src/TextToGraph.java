package com.src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.*;



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

    /***
     * 从node1到node2的最短路径
     * @param node1
     * @param node2
     */
    public void MinimumPath(String node1, String node2) {
        //如果graph未被初始化，内容为空
            if(graph.nodes.isEmpty()){
                System.out.println("Graph is empty, please build graph first");
                return;
            }
            if(!graph.nodes.containsKey(node1)){
                System.out.println("Node1:"+node1+" not found in graph");
                return;
            }
            if(!graph.nodes.containsKey(node2)){
                System.out.println("Node2:"+node2+" not found in graph");
                return;
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
                return;
            }

            LinkedList<String> path = new LinkedList<>();
            String current = node2;
            while (current != null) {
                path.addFirst(current);
                current = parent.get(current);
            }
        System.out.println("Minimum path between " + node1 + " and " + node2 + " is " + path);
    }

    /***
     * 从node1到所有节点的最短路径,重载方法
     * @param node1
     */
    public void MinimumPath(String node1){
        if(graph.nodes.isEmpty()){
            System.out.println("Graph is empty, please build graph first");
            return;
        }
        if(!graph.nodes.containsKey(node1)){
            System.out.println("Node1:"+node1+" not found in graph");
            return;
        }
        for (String node2 : graph.nodes.keySet()) {
            if (!node1.equals(node2)) {
                MinimumPath(node1, node2);//复用MinimumPath(node1, node2)方法
            }
        }
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
    public void WanderGraph() {
        if (graph.nodes.isEmpty()) {
            System.out.println("Graph is empty, please build graph first");
            return;
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
            if (edges.containsAll(neighbors)) {
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
        String sentence = builder.toString();
        try {
            String filename = "wander.txt";
            System.out.println("Writing to file: " + filename);
            FileWriter writer = new FileWriter(filename);
            System.out.println(sentence);
            writer.write(sentence);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
