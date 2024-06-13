package com.src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    //by chinhitori
    private TextToGraph textToGraph;
    @BeforeEach
    void setUp() {
        textToGraph = new TextToGraph();
        textToGraph.buildGraph("src/test.txt");
    }
//    @Test
//    void queryBridgeWords() {
//        assertNull(Main.queryBridgeWords(textToGraph, "123", "servants"));
//    }
//    @Test
//    void testQueryBridgeWords2() {
//        assertEquals("his", textToGraph.queryBridgeWords("commanded", "servants"));
//        System.out.println("Bridge word is "+textToGraph.queryBridgeWords("commanded", "servants"));
//    }
//    @Test
//    void testQueryBridgeWords3() {
//        assertNull(textToGraph.queryBridgeWords("fell", "fathers"));
//        System.out.println(textToGraph.queryBridgeWords("fell", "fathers")+" returned");
//    }
//
//    @Test
//    void testQueryBridgeWords4() {
//        assertNull(textToGraph.queryBridgeWords("123", "34443"));
//    }
//    @Test
//    void testQueryBridgeWords5() {
//        assertEquals("his",textToGraph.queryBridgeWords("on", "servants"));
//        System.out.println("Bridge word is "+textToGraph.queryBridgeWords("on", "servants"));
//    }
//    @Test
//    void testQueryBridgeWords6() {
//        assertNull(textToGraph.queryBridgeWords("then", "dayxs"));
//        System.out.println(textToGraph.queryBridgeWords("then", "dayxs")+" returned");
//    }

    @Test
    void testCalcShortestPath() {
        assertNull(Main.calcShortestPath(textToGraph,"on","and"));
        System.out.println("Shortest path is "+textToGraph.calcShortestPath("commanded", "servants"));
    }

    @Test
    void testCalcShortestPath2() {
        assertNull(Main.calcShortestPath(textToGraph, null, "and"));
    }

    @Test
    void testCalcShortestPath3() {
        assertNull(Main.calcShortestPath(textToGraph, "and", null));
    }

    @Test
    void testCalcShortestPath4() {
        assertNull(Main.calcShortestPath(textToGraph, "dayxs", "on"));
    }

    @Test
    void testCalcShortestPath5(){
        String result = Main.calcShortestPath(textToGraph,"and","on");
        assertEquals("Minimum path between and and on is: and->joseph->fell->on",result);
        System.out.println(result);
    }
}