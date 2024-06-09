package com.src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private TextToGraph textToGraph;
    @BeforeEach
    void setUp() {
        textToGraph = new TextToGraph();
        textToGraph.buildGraph("src/test.txt");
    }
    @org.junit.jupiter.api.Test
    void queryBridgeWords() {
        assertNull(Main.queryBridgeWords(textToGraph, "123", "servants"));
    }
    @Test
    void testQueryBridgeWords2() {
        assertEquals("his", textToGraph.queryBridgeWords("commanded", "servants"));
        System.out.println("Bridge word is "+textToGraph.queryBridgeWords("commanded", "servants"));
    }
    @Test
    void testQueryBridgeWords3() {
        assertNull(textToGraph.queryBridgeWords("fell", "fathers"));
        System.out.println(textToGraph.queryBridgeWords("fell", "fathers")+" returned");
    }

    @Test
    void testQueryBridgeWords4() {
        assertNull(textToGraph.queryBridgeWords("123", "34443"));
    }
    @Test
    void testQueryBridgeWords5() {
        assertEquals("his",textToGraph.queryBridgeWords("on", "servants"));
        System.out.println("Bridge word is "+textToGraph.queryBridgeWords("on", "servants"));
    }
    @Test
    void testQueryBridgeWords6() {
        assertNull(textToGraph.queryBridgeWords("then", "dayxs"));
    }

}