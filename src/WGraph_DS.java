package ex1.src;

import java.util.*;

/**
 *  * This interface represents an undirectional weighted graph.
 */
public class WGraph_DS implements weighted_graph, java.io.Serializable {

    private HashMap<Integer, node_info> map2;
    private HashMap<node_info, HashMap<node_info, Double>> mapNeighbour;
    private int NONodesOnGraph;
    private int ChangesOnGraph;
    private int NumOfEdges;

    /**
     * constructor for new graph
     */
    public WGraph_DS() {
        this.map2 = new HashMap();
        this.mapNeighbour = new HashMap();
        this.NONodesOnGraph = 0;
        this.ChangesOnGraph = 0;
        this.NumOfEdges = 0;
    }

    /**
     * constructor for new graph that copy all g data
     *
     * @param g
     */
    public WGraph_DS(WGraph_DS g) {
        this.map2 = new HashMap<>(); // creates new hash map
        this.mapNeighbour = new HashMap<>();
        for (node_info node : g.getV()) { // run over all nodes and stores them on this hashmap
            node_info n = new NodeInfo(node.getKey());
            map2.put(n.getKey(), n);
            mapNeighbour.put(n, new HashMap<>());

        }
        for (node_info node : g.getV()) { // run on all nodes and connect between them if needed
            for (node_info node1 : g.getV(node.getKey()))
                connect(node1.getKey(), node.getKey(), g.getEdge(node1.getKey(), node.getKey()));
        }
        this.NONodesOnGraph = g.nodeSize();
        this.NumOfEdges = g.edgeSize();
        this.ChangesOnGraph = g.edgeSize() + g.nodeSize();
    }

    /**
     * @param key - the node_id
     * @return node_data "key"
     */
    @Override
    public node_info getNode(int key) {
        return map2.get((key));
    }

    /**
     * this function check the edge between 2 nodes
     * check if 1 connected to 2 and 2 connected to 1
     * 2 simple checks in o(1) run time
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (mapNeighbour.get(getNode(node1)) == null || mapNeighbour.get(getNode(node1)).get(getNode(node2)) == null)
            return false;
        return true;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (mapNeighbour.get(getNode(node1)) != null && mapNeighbour.get(getNode(node1)).get(getNode(node2)) != null)
            return mapNeighbour.get(getNode(node1)).get(getNode(node2)).doubleValue();
        return -1;
    }

    /**
     * this funtion add new node to  the graph
     * there is also a counter for how many nodes on graph
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!map2.containsKey(key)) {
            map2.put(key, new NodeInfo(key));
            mapNeighbour.put(getNode(key), new HashMap<>());
            this.NONodesOnGraph++;
            this.ChangesOnGraph++;
        }
    }

    /**
     * this function Connect an edge between node1 and node2.
     * first check if edge already exist, if so do nothing.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (!(node1 == node2) && (getNode(node1) != null && getNode(node2) != null))
            if (!hasEdge(node1, node2)) {
                this.mapNeighbour.get(getNode(node1)).put(getNode(node2), w);
                this.mapNeighbour.get(getNode(node2)).put(getNode(node1), w);
                this.ChangesOnGraph++;
                this.NumOfEdges++;
            }
    }

    /**
     * @return all nodes on graph
     */
    @Override
    public Collection<node_info> getV() {
        if (map2 == null) return null;
        return this.map2.values();
    }

    /**
     * func return a collection of all nodes that consider neighbour
     * to the node_id node. by using hashmap set of functions to see if
     * there is a node_id node on the graph.
     *
     * @param node_id
     * @return return its neighbour by get from hashmap function
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (getNode(node_id) == null) return null;
        if (mapNeighbour.containsKey(getNode(node_id))) {
            return mapNeighbour.get(getNode(node_id)).keySet();
        }
        return null;
    }

    /**
     * this function check if node exist on graph and if so Delete the node
     * (with the given ID) from the graph and removes all
     * edges which starts or ends at this node.
     *
     * @param key
     * @return the node data and delete from graph o(1)
     * run time: first check if exist is o(1), while run on every nodes neighbours
     * by using for each over a collection and removes the edge, max neighbours n.
     * ChangesOnGraph++ is o(1);  NONodesOnGraph-- is o(1)
     * function run time is: o(n).
     */
    @Override
    public node_info removeNode(int key) {
        if (getNode(key) == null) return null;
        int[] arr = new int[getV(key).size()];
        int i = 0;
        for (node_info k : getV(key)) {
            arr[i] = k.getKey();
            i++;
        }
        for (int j = 0; j < arr.length; j++) {
            removeEdge(key, arr[j]);
        }
        this.mapNeighbour.remove(getNode(key));

        node_info n = (getNode(key));
        this.map2.remove(key);
        this.ChangesOnGraph++;
        this.NONodesOnGraph--;
        return n;
    }

    /**
     * function removes edge bu using mapNeighbour hashamp to
     * delete the edge on both sides of node
     *
     * @param node1
     * @param node2 4 simple o(1) action. run time: o(1)
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (mapNeighbour.get(getNode(node1)).get(getNode(node2)) != null && mapNeighbour.get(getNode(node2)).get(getNode(node1)) != null) {
            mapNeighbour.get(getNode(node1)).remove(getNode(node2));
            mapNeighbour.get(getNode(node2)).remove(getNode(node1));
            this.ChangesOnGraph++;
            this.NumOfEdges--;
        }
    }

    @Override
    public String toString() {
        return map2.toString() + mapNeighbour.toString();
    }

    /**
     * @return number of nodes on graph
     * by counting adding nodes and minus deleting nodes
     */
    @Override
    public int nodeSize() {
        return this.NONodesOnGraph;
    }

    /**
     * @return number of edges
     */
    @Override
    public int edgeSize() {
        return this.NumOfEdges;
    }

    /**
     * counting changes on graph
     *
     * @return the number of changes
     */

    @Override
    public int getMC() {
        return ChangesOnGraph;
    }

    /**
     * function equals that compare two object
     *
     * @param o
     * @return true if the same object are really equals by their numbers
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        WGraph_DS g = (WGraph_DS) o;
        if (o == null || getClass() != o.getClass()) return false;
        //compare the two HashMap
        if (!this.map2.equals(map2)) return false;
        if (!this.mapNeighbour.equals(mapNeighbour)) return false;
        //compare num of changes
        if (this.ChangesOnGraph != g.getMC()) return false;
        return true;
    }

    private class NodeInfo implements node_info, Comparable<node_info>, java.io.Serializable {

        private int key;
        private double tag = 0;
        private int NumOfNode = 0;
        private String info;

        /**
         * constructor for new node
         */
        public NodeInfo() {
            this.key = NumOfNode++;
            this.tag = 0;
            this.info = "";
        }

        public NodeInfo(int key) {
            this.key = key;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }


        public String toString() {

            return "" + key;
        }

        /**
         * this function check if the tag of node_info o
         * bigger than this nodes tag,
         * made for priority queue
         *
         * @param o
         * @return 1 || -1
         */
        @Override
        public int compareTo(node_info o) {
            if (o.getTag() > this.getTag()) return -1;
            return 1;
        }
    }


}
