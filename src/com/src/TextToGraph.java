package com.src;
import java.io.BufferedReader;
import java.io.FileReader;
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

}
