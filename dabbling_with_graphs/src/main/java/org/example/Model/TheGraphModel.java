package org.example.Model;

import org.example.Model.Generators.AbstractTemplateGenerator;
import org.example.Model.Generators.RandomGraphGenerator;
import org.example.Model.Generators.UserInputGenerator;
import org.example.MyGraphElementsAlgorithms.Dijsktra.DijkstraGraphAlgo;
import org.example.MyGraphElementsAlgorithms.Dijsktra.NodeForDijkstra;
import org.example.MyGraphElementsAlgorithms.Kruskal.UnionFind.KruskalMinimumSpanningTree;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * Class that holds the graph of the program.
 *
 * @author David Nistor
 */
public class TheGraphModel {
    private Graph theGraph;
    private List<Node> nodes;
    private Map<Node, NodeForDijkstra> mappingFunction;

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

        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the number of nodes: random or user input");
        System.out.println("\"r\" for random, \"i\" for input");
        AbstractTemplateGenerator generator;
        char input = sc.next().charAt(0);

        if (input == 'i') {
            generator = new UserInputGenerator();
        } else if (input == 'r') {
            generator = new RandomGraphGenerator();
        } else {
            throw new IllegalArgumentException("You have neither inputed \"r\" nor \"i\" again, please" +
                    " run program again");
        }

        theGraph = generator.generateGraph("TheGameGraph");
        nodes = generator.getTheNodes();
        Map<Node, Map<Node, Integer>> adjacentNodes = generator.getEdgesEachNode();

        nodes.stream().forEach(node -> mappingFunction.put(node, new NodeForDijkstra(node.getId(), node)));
        nodes.stream().forEach(node ->
                {
                adjacentNodes.get(node).keySet().stream()
                        .forEach(adjacentNode ->
                                {
                                    myNode(node)
                                        .addAdjacentNodes(
                                            myNode(adjacentNode),
                                                adjacentNodes.get(node).get(adjacentNode));

                                }
                                );
                });
    }

    /**
     * Gets the corresponding {@code NodeForDijkstra} node object from the {@code org.graphstream.graph.Node node}.
     *
     * @param node the {@code org.graphstream.graph.Node node}
     * @return {@code NodeForDijkstra} corresponding node
     */
    private NodeForDijkstra myNode(Node node) {
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
     * Gets the nodes of {@code theGraph}.
     *
     * @return {@nodes} the list of nodes of {@code theGraph}
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Gets the shortest path to a node {@code node}.
     *
     * @param node the node the path ends in
     * @return {@code shortestPath} the list of nodes in the shortest path
     */
    private List<Node> getShortestPathToNode(Node node) {

        List<NodeForDijkstra> list1 = mappingFunction.get(node).getShortestPathToNode();
        List<Node> shortestPath = new ArrayList<>();
        list1.stream().forEach(nodeInPath -> shortestPath.add(nodeInPath.getNode()));

        return shortestPath;
    }

//    public Map<Node, Integer> getAdjacentNodes(Node node) {
//        Map<Node, Integer> result = new HashMap<>();
//        myNode(node).getAdjacentNodes().keySet().stream().forEach(n ->
//                result.put(n.getNode(), myNode(node).getAdjacentNodes().get(n)));
//        return result;
//    }

    /**
     * Calculates the shortest path from {@code source} node to {@code target} node.
     *
     * @param source the source node
     * @param target the target node
     * @return {@code shortestPath} the list of nodes in the shortest path
     */
    public List<Node> calculateShortestPath(Node source, Node target) {
        calculateShortestFromSource(source);
        List<Node> shortestPath = getShortestPathToNode(target);

        return shortestPath;
    }

    /**
     * Calculates the MST of {@code theGraph} and returns the list of edges in the MST.
     *
     * @return {@code minSTree} the list of edges in the MST
     */
    public List<Edge> calculateMST() {
        KruskalMinimumSpanningTree kmst = new KruskalMinimumSpanningTree(nodes.size(), theGraph);
        List<Edge> minSTree = kmst.calculateMST();

        return minSTree;
    }
 }
