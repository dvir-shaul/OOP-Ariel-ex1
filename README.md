# OOP-Ariel-ex1
Hello, this project has been written by Dvir Shaul.

project name is ex1.
in this project i created a graph with nodes, and make actions on that graph.

NodeInfo represents the set of operations applicable on a node (vertex) in an (undirectional) weighted graph. 
each nodes has its unique key, tag that represent howmant distance the is between him and chosen node,
info that represent if he has been seen in bfs check, and a lists of the neighbour he has on HashMap data structure.

WGraph_DS represents an undirectional unweighted graph that can add nodes to the graph, remove them, 
connect and disconnect between 2 nodes. it can return the number of nodes in graph,the changes were made on the graph,
sum of edges between nodes,the sum of the edge between 2 nodes, a collection representing all the nodes in the graph and 
returns a collection containing all the nodes connected to a node_id by using HashMap data structure.

WGraph_Algo interface represents an Undirected (positive) Weighted Graph Theory algorithms including:
0. clone(); (copy)
1. init(graph)
2. isConnected() - return true if we can get from random node to every other node on graph .
3. double shortestPathDist(int src, int dest) - find the shortest way from one node to another by using dijkstra algorithm.
4. List<node_info> shortestPath(int src, int dest) - print that short path.
5. save(file) Saves this weighted
6. load(file) This method load a graph to this graph algorithm.

i used HashMap becuase its good and convenient store of the input data.
HashMap<Integer, node_info> ( mean <key,value> )
HashMap is Hash table based implementation of the Map interface. This implementation provides all of the optional 
map operations and connect between keys (Integer) to values (node_data).

i also used Hashmap like that HashMap<node_info, HashMap<node_info, Double>> mapNeighbour;
and there i saved the neighbours, as in every node has its own uniqe Hashmap.

i used BFS - breadth first search is an algorithm that use to "run" on all the nodes on the graph by checking one 
node, marking him as seen and then move to his neighnours, marking them as seen and then to their neighbours, etc.
its good and efficient "runing" on the graphs nodes, thats why i used it.
run time of bfs is: O( | V |+| E | ).  because every nodes checked ones and run from him to every edge to other
 
 i used dijkstra algorithm to find the shortest Path Distance between 2 given nodes, it is like bfs algorithm but instead of 
 a regualr Queue we used Priority Queue, and the node with preferred priority (mean that he has the shortest path to him)
 will searh his neighbours first, and that so until we went on every path there is on the graph.
