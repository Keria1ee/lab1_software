package com.src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void queryBridgeWords() {
        TextToGraph textToGraph = new TextToGraph();
        textToGraph.buildGraph("src/test.txt");
        assertNull(textToGraph.queryBridgeWords("123", "4423"));
        assertEquals("his", textToGraph.queryBridgeWords("commanded", "servants"));
        assertNull(textToGraph.queryBridgeWords("fell", "fathers"));
    }

}