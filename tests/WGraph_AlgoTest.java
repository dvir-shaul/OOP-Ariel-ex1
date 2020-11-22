package ex1.tests;

import org.junit.jupiter.api.Test;
import ex1.src.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void init() {
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

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);

        assertEquals(graph,ga.getGraph());
        assertFalse(ga.getGraph().hasEdge(1, 8));

    }

    @Test
    void getGraph() {
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

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);

        assertEquals(graph,ga.getGraph());
    }

    @Test
    void copy() {
        weighted_graph graph = new WGraph_DS();
        weighted_graph graph1 = new WGraph_DS();

        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);

        graph.connect(1,2,3);
        graph.connect(1,4,3);
        graph.connect(5,1,3);
        graph.connect(3,2,3);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        graph1 = ga.copy();
        //check if the same
        assertEquals(graph,graph1);
        assertEquals(graph.edgeSize(),graph1.edgeSize());
        assertEquals(graph.nodeSize(),graph1.nodeSize());
        assertEquals(graph.getMC(),graph1.getMC());
        graph.addNode(6);
        //false cuase not the same
        assertNotEquals(graph,graph1);
    }

    @Test
    void isConnected() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        graph.connect(1,2,3);
        graph.connect(1,3,3);
        graph.connect(5,1,3);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        assertTrue(ga.isConnected()); //true
        graph.removeNode(1);
        assertFalse(ga.isConnected()); //false
        graph.connect(2,3,6);
        assertTrue(ga.isConnected()); //true

    }

    @Test
    void shortestPathDist() {
        weighted_graph graph = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);

        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);

        graph.connect(1,2,1.2);
        graph.connect(1,4,40.9);
        graph.connect(5,1,20);
        graph.connect(3,2,5.8);
        graph.connect(4,2,5.6);
        double distance = ga.shortestPathDist(1,3);
        assertEquals(7,ga.shortestPathDist(1,3));
        assertEquals(-1,ga.shortestPathDist(1,6));

    }

    @Test
    void shortestPath() {
        weighted_graph graph = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);

        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);

        graph.connect(1,2,1.2);
        graph.connect(1,4,40.9);
        graph.connect(5,1,20);
        graph.connect(3,2,5.8);
        graph.connect(4,2,5.6);

        LinkedList<node_info> trip = new LinkedList<>();
        trip.add(graph.getNode(1));
        trip.add(graph.getNode(2));
        trip.add(graph.getNode(3));
        assertEquals(trip,ga.shortestPath(1,3));
        LinkedList<node_info> noTrip = null;
        assertEquals(noTrip,ga.shortestPath(1,6));

    }

    @Test
    void save() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        String str = "g.obj";
        ga.save(str);

        weighted_graph g1 = new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);

        ga.load(str);
        assertEquals(ga.getGraph(),g1);
    }

    @Test
    void load() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(2,1,0.5);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        String str = "g.obj";
        ga.save(str);

        weighted_graph newGraph = new WGraph_DS();
        newGraph.addNode(1);
        newGraph.addNode(2);
        newGraph.connect(2,1,0.5);
        assertEquals(ga.getGraph(),newGraph);
        ga.load(str);
        assertEquals(graph, ga.getGraph());
        graph.removeNode(1);
        assertNotEquals(graph, ga.getGraph());
    }
}
