package ex1.src;

import java.io.*;
import java.util.*;

/**
 * This interface represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author Dvir shaul
 */
public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {

    private weighted_graph graphs;

    public WGraph_Algo() {
        graphs = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */

    @Override
    public void init(weighted_graph g) {
        this.graphs = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graphs;
    }

    /**
     * Compute a deep copy of this graph.
     *
     * @return new copiedGraph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copiedGraph = new WGraph_DS((WGraph_DS) graphs);
        return copiedGraph;
    }

    /**
     * function to check if all graph nodes are connected to each other
     * by using breadth first search to move on every node over the graph
     * and counting them.
     *
     * @return true if the checked connected nodes has the same
     * amount of nodes as the graph has.
     * bfs working in a way that he get a node and them put all his nodes
     * in a "queue" and then mark that node as seen and moving to that
     * nodes neighbours and collect all their neighbour etc.
     * until run on all nodes that connected
     */
    @Override
    public boolean isConnected() {
        try {
            if ((this.graphs == null)) return false;
            if (this.graphs.nodeSize() == 1 || this.graphs.nodeSize() == 0) return true;
            int equals = this.graphs.nodeSize();
            int counter = 0;
            Iterator<node_info> first = this.graphs.getV().iterator();
            if (first.hasNext()) {
                node_info sec = first.next();
                for (node_info node : graphs.getV()) {
                    node.setInfo("unseen");
                }
                LinkedList<node_info> queue = new LinkedList<node_info>();
                queue.add(graphs.getNode(sec.getKey()));
                graphs.getNode(sec.getKey()).setInfo("seen"); //mark first node as seen
                while (!queue.isEmpty()) {
                    counter++; //checking counter on nodes
                    node_info neib = queue.remove();
                    for (node_info n : graphs.getV(neib.getKey())) { //run on every nodes neighbours
                        if (n.getInfo() != "seen") {
                            n.setInfo("seen");
                            queue.add(n); //add that node to the queue
                        }
                    }
                }
            }
            return equals == counter;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * this function work with dijkstra algorithm,
     * by sum the weight of the edge to the node to every node he run over
     * from src,
     * so close neighbour of src will have tag=edge,
     * and that neighbour neighbour if not connected
     * to src will have tag = neibs tag + edge
     * until all nodes are marked.
     * and all nodes have the shortest distance (that represent on the tag of node)
     * from src nodes to them.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return shortest path from src node to dest node
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        Iterator<node_info> first = this.graphs.getV().iterator();
        if (first.hasNext())
            for (node_info node : graphs.getV()) {
                node.setInfo("unseen");
                node.setTag(Double.POSITIVE_INFINITY);
            }
        PriorityQueue<node_info> pQueue = new PriorityQueue<node_info>();
        pQueue.add(this.graphs.getNode(src));
        double k = 0;
        graphs.getNode(src).setInfo("seen");
        graphs.getNode(src).setTag(0);
        while (!pQueue.isEmpty()) {
            node_info node2 = pQueue.remove();
            for (node_info node : graphs.getV(node2.getKey())) {
                if (node.getInfo() == "unseen") {
                    k = graphs.getEdge(node.getKey(), node2.getKey());
                    node.setTag(node2.getTag() + k);
                    node.setInfo("seen");
                    pQueue.add(node);

                } else if (node.getTag() > (node2.getTag() + graphs.getEdge(node.getKey(), node2.getKey())))
                    node.setTag(node2.getTag() + graphs.getEdge(node.getKey(), node2.getKey()));
            }
        }
        if (graphs.getNode(dest).getInfo() == "unseen") return -1;
        return graphs.getNode(dest).getTag();
    }

    /**
     * print the way from src node to dest node
     * this function work with dijkstra algorithm, by doing the shortestPathDist() function
     * and come back from dest node to src node by knowing the tag that he first came from
     * has cameFromNodeTag =thisNodeTag-edge(thisNodeTag,cameFromNodeTag)
     * until he met the src node and adding them to a list
     * of the way from src node to dest node
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return list of the way from src node to dest node
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        Double epsilon = 0.00001;
        LinkedList<node_info> roadMap = new LinkedList<node_info>();
        Stack<node_info> returnMap = new Stack<node_info>();
        Iterator<node_info> first = this.graphs.getV().iterator();
        if (first.hasNext())
            for (node_info node : graphs.getV()) {
                node.setInfo("unseen");
                node.setTag(Double.POSITIVE_INFINITY);
            }
        if (graphs.getNode(dest).getInfo() == "unseen") {
            if (shortestPathDist(src, dest) != -1) {
                roadMap.addFirst(graphs.getNode(dest));
                returnMap.add(graphs.getNode(dest));
                while (!returnMap.isEmpty()) {
                    node_info k = returnMap.pop();
                    for (node_info node : graphs.getV(k.getKey())) {
                        if (k.getTag() == node.getTag() + graphs.getEdge(node.getKey(), k.getKey())) {
                            roadMap.addFirst(node);
                            returnMap.push(node);
                        }
                    }
                } //end of while
            }
        }
        if (roadMap.size() == 0) return null;
        return roadMap;
    }

    @Override
    public String toString() {
        return "" + this.getGraph();
    }

    /**
     * function save is saving the graph to a .txt file
     * by using FileOutputStream and ObjectOutputStream.
     *
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        System.out.println("Starting Serialize  " + file + "\n");
        try {
            FileOutputStream file1 = new FileOutputStream(file);
            ObjectOutputStream outS = new ObjectOutputStream(file1);
            outS.writeObject(this.graphs);
            outS.close();
            file1.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("End of Serialization \n\n");
        return true;
    }

    /**
     * function save is saving the graph to a .txt file
     * by using FileInputStream and ObjectInputStream.
     *
     * @param file - file name
     * @return
     */
    public boolean load(String file) {

        try {
            System.out.println("start of load" + "\n");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            weighted_graph grpah = (WGraph_DS) in.readObject();
            in.close();
            fileIn.close();
            init(grpah);
            System.out.println("end of load");
        } catch (Exception i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }

}


