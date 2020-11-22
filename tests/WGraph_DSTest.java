package ex1.tests;

import org.junit.jupiter.api.Test;
import ex1.src.*;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void getNode() {
        //first check if null
        weighted_graph graph = new WGraph_DS();
        node_info node = graph.getNode(1);
        assertNull(node);
        //check if ok with 1 node
        graph.addNode(1);
        node = graph.getNode(1);
        assertEquals(1,node.getKey());
    }

    @Test
    void hasEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,1);
        //check connectivity
        assertTrue(graph.hasEdge(1,2));
        assertTrue(graph.hasEdge(2,1));
        assertFalse(graph.hasEdge(2,3));

    }

    @Test
    void getEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,1);
        assertEquals(1,graph.getEdge(1,2));
        assertEquals(-1,graph.getEdge(3,2));
        assertEquals(-1,graph.getEdge(1,1));
    }

    @Test
    void addNode() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        node_info node = graph.getNode(1);
        assertEquals(1, node.getKey());
    }

    @Test
    void connect() {
        weighted_graph graph = new WGraph_DS();
        assertFalse(graph.hasEdge(1,2));
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,1);
        assertTrue(graph.hasEdge(1,2));
        graph.removeNode(1);
        assertEquals(-1,graph.getEdge(1,2));
    }

    @Test
    void getV() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);

        Collection<node_info> nodesOnGraph= new LinkedList<>();
        nodesOnGraph.add(graph.getNode(1));
        nodesOnGraph.add(graph.getNode(2));
        nodesOnGraph.add(graph.getNode(3));
        nodesOnGraph.add(graph.getNode(4));
        nodesOnGraph.add(graph.getNode(5));

        assertEquals(nodesOnGraph.size(), graph.getV().size());

        graph.removeNode(5);
        assertNotEquals(nodesOnGraph.size(), graph.getV().size());

    }

    @Test
    void testGetV() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);

        graph.connect(1,2,3);
        graph.connect(1,4,3);
        graph.connect(5,1,3);
        graph.connect(3,2,3);
        Collection<node_info> nodesOnGraph= new LinkedList<>();
        nodesOnGraph.add(graph.getNode(2));
        nodesOnGraph.add(graph.getNode(4));
        nodesOnGraph.add(graph.getNode(5));
        assertEquals(nodesOnGraph.size(), graph.getV(1).size());
        graph.removeNode(4);
        assertNotEquals(nodesOnGraph.size(), graph.getV(1).size());

    }

    @Test
    void removeNode() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(1,2,3);
        graph.connect(1,3,8.2);

        assertEquals(3,graph.nodeSize());
        graph.removeNode(3);
        assertEquals(2,graph.nodeSize());


    }

    @Test
    void removeEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3);

        assertEquals(3,graph.getEdge(1,2));
        assertTrue(graph.hasEdge(1,2));

        graph.removeEdge(1,2);

        assertNotEquals(3,graph.getEdge(1,2));
        assertFalse(graph.hasEdge(1,2));
    }

    @Test
    void nodeSize() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        assertEquals(2,graph.nodeSize());
        graph.removeNode(2);
        assertEquals(1,graph.nodeSize());
        graph.removeNode(1);
        assertEquals(0,graph.nodeSize());

    }

    @Test
    void edgeSize() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3);
        assertEquals(1,graph.edgeSize());
        graph.connect(4,2,3); //false entry on value
        assertEquals(1,graph.edgeSize());
        graph.removeEdge(1,2);
        assertEquals(0,graph.edgeSize());

    }

    @Test
    void getMC() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3);
        assertEquals(3,graph.getMC());
        graph.connect(2,4,2);
        assertEquals(3,graph.getMC());
        graph.removeEdge(2,1);
        assertEquals(4,graph.getMC());

    }

}
