package org.example.model;

import org.example.algorithms.dfs.DepthFirstSearch;
import org.example.model.generators.AbstractTemplateGenerator;
import org.example.model.generators.RandomGraphGenerator;
import org.example.model.generators.UserInputGenerator;
import org.example.algorithms.dijsktra.DijkstraGraphAlgo;
import org.example.algorithms.dijsktra.NodeWrapper;
import org.example.algorithms.kruskal.KruskalMinimumSpanningTree;
import org.example.algorithms.bfs.BreadthFirstSearch;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * Class that holds the graph of the program and defines operations for the graph algorithms.
 *
 * @author David Nistor
 */
public class TheGraphModel {
    private Graph theGraph;
    private final Map<Node, NodeWrapper> mappingFunction;

    /**
     * Constructs the {@code TheGraphModel} object.
     */
    public TheGraphModel() {
        mappingFunction = new HashMap<>();
        initializeTheModel();
    }

    /**
     * Initializes {@code TheGraphModel} object.
     */
    private void initializeTheModel(){
        AbstractTemplateGenerator generator = getGenerator();
        theGraph = generator.generateGraph("TheGameGraph");
        Map<Node, Map<Node, Integer>> adjacentNodes = generator.getEdgesEachNode();

        theGraph.nodes().forEach(node -> mappingFunction.put(node, new NodeWrapper(node)));
        theGraph.nodes().forEach(node -> {
            Map<Node, Integer> nodeMap = adjacentNodes.get(node);
            Set<Node> nodes = nodeMap.keySet();
            nodes.forEach(adjacentNode -> {
                Integer weight = nodeMap.get(adjacentNode);
                myNode(node).addAdjacentNodes(myNode(adjacentNode), weight);
            });
        });
    }

    /**
     * Applies strategy design pattern, lets the user choose the strategy at runtime.
     *
     * @return {@code generator} the generator
     */
    private AbstractTemplateGenerator getGenerator(){
        Scanner sc = new Scanner(System.in);
        AbstractTemplateGenerator generator = null;

        while (generator == null) {
            System.out.println("Choose the number of nodes: random or user input");
            System.out.println("\"r\" for random, \"i\" for input");
            char input = sc.next().charAt(0);

            if (input == 'i') {
                generator = new UserInputGenerator();
            } else if (input == 'r') {
                generator = new RandomGraphGenerator();
            }
        }

        return generator;
    }

    /**
     * Gets the corresponding {@code NodeWrapper} node object from the {@code org.graphstream.graph.Node node}.
     *
     * @param node the {@code org.graphstream.graph.Node node}
     * @return {@code NodeWrapper} corresponding node
     */
    private NodeWrapper myNode(Node node) {
        return mappingFunction.get(node);
    }

    /**
     * Gets {@code theGraph} object.
     *
     * @return {@code theGraph} the graph
     */
    public Graph getTheGraph() {
        return theGraph;
    }

    /**
     * Calculates the shortest path to each node of {@code theGraph}, from a source {@code node}.
     *
     * @param node the source node
     */
    private void calculateShortestFromSource(Node node) {
        DijkstraGraphAlgo dijkstra = new DijkstraGraphAlgo();
        dijkstra.calculateShortestPath(myNode(node));
    }

    /**
     * Gets the shortest path to a node {@code node}.
     *
     * @param node the node the path ends in
     * @return {@code shortestPath} the list of nodes in the shortest path
     */
    private List<Node> getShortestPathToNode(Node node) {
        List<NodeWrapper> list1 = mappingFunction.get(node).getShortestPathToNode();
        List<Node> shortestPath = new ArrayList<>();
        list1.forEach(nodeInPath -> shortestPath.add(nodeInPath.getNode()));

        return shortestPath;
    }


    /**
     * Calculates the shortest path from {@code source} node to {@code target} node.
     *
     * @param source the source node
     * @param target the target node
     * @return {@code shortestPath} the list of nodes in the shortest path
     */
    public List<Node> calculateShortestPath(Node source, Node target) {
        calculateShortestFromSource(source);

        return getShortestPathToNode(target);
    }

    /**
     * Calculates the MST of {@code theGraph} and returns the list of edges in the MST.
     *
     * @return {@code minSTree} the list of edges in the MST
     */
    public List<Edge> calculateMST() {
        KruskalMinimumSpanningTree kmst = new KruskalMinimumSpanningTree(theGraph.getNodeCount(), theGraph);

        return kmst.calculateMST();
    }

    /**
     * Calculates the layers of a BFS starting at {@code node}.
     *
     * @param node the starting node
     * @return the list of layers of the BFS
     */
    public List<List<Node>> calculateBFS(Node node) {
        BreadthFirstSearch bfs = new BreadthFirstSearch(theGraph);
        bfs.startBFS(node);

        return bfs.calculateLayers();
    }

    /**
     * Calculates the paths of a DFS starting at {@code node}.
     *
     * @param node the starting node
     * @return the list of paths of the BFS
     */
    public List<List<Node>> calculateDFS(Node node) {
        DepthFirstSearch dfs = new DepthFirstSearch(theGraph);
        dfs.startDFS(node);

        return dfs.getAllPathsInDfs();
    }
}
