package org.example.Model.Generators;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

/**
 * Abstract template class that defines skeleton the creation of a graph.
 *
 * @author David Nistor
 */
public abstract class AbstractTemplateGenerator {
    private List<Node> theNodes;
    private Map<Node, Map<Node, Integer>> adjacentNodes;

    public List<Node> getTheNodes() {
        return theNodes;
    }

    public Map<Node, Map<Node, Integer>> getEdgesEachNode() {
        return adjacentNodes;
    }

    /**
     * Generates graph.
     *
     * @param name the name of the graph
     * @return {@code graph} the generated graph
     */
    public Graph generateGraph(String name) {

        Graph graph = new SingleGraph(name);
        Random random = new Random();
        adjacentNodes = new HashMap<>();

        int sizeOfGraph = getSizeOfGraph();

        theNodes = createNodes(graph, sizeOfGraph);

        // initialize each node's edges
        theNodes.stream().forEach(node -> adjacentNodes.put(node, new HashMap<>()));
        // generate random edges for each node
        theNodes.stream().forEach(node ->
                createEdgesForNode(node, graph, random, sizeOfGraph, theNodes));

        return graph;
    }

    /**
     * Generates nodes for {@code graph}.
     *
     * @param graph the graph
     * @param sizeOfGraph the wanted number of nodes in the graph
     * @return {@code nodes} the list of nodes of the {@code graph}
     */
    private List<Node> createNodes(Graph graph, int sizeOfGraph) {

        List<Node> nodes = new ArrayList<>();

        for(int i = 0; i < sizeOfGraph; ++i) {
            String nodeName = "Node" + i;
            nodes.add(graph.addNode(nodeName));
        }

        return nodes;
    }

    /**
     * Generates edges for {@code node} of the {@code graph}.
     *
     * @param node the node to generate the edges for
     * @param graph the graph
     * @param random the {@code Random} object
     * @param sizeOfGraph the number of nodes of the graph
     * @param nodes the nodes of the graph
     */
    private void createEdgesForNode(Node node,
                                   Graph graph,
                                   Random random,
                                   int sizeOfGraph,
                                   List<Node> nodes) {

        List<Node> availableNodes = new ArrayList<>();
        nodes.stream().forEach(availableNodes::add);
        availableNodes.remove(node);

        int theNumberOfEdges = random.nextInt(0, sizeOfGraph / 3);

        for(int i = 0; i < theNumberOfEdges; i++) {

            Node toWhichNode = availableNodes.get(
                    random.nextInt(availableNodes.size())
            );

            String nameOfEdge = "Edge" + node.getId() + toWhichNode.getId();
            String reverseEdgeName = "Edge" + toWhichNode.getId() + node.getId();

            if(graph.getEdge(reverseEdgeName) == null) {

                Edge theEdge = graph.addEdge(nameOfEdge, node.getId(), toWhichNode.getId());
                int theRandomWeight = random.nextInt(1, 10);

                theEdge.setAttribute("weight", theRandomWeight);
                adjacentNodes.get(node).put(toWhichNode, theRandomWeight);

                if(node != toWhichNode) {
                    adjacentNodes.get(toWhichNode).put(node, theRandomWeight);
                }
            }

            availableNodes.remove(toWhichNode);
        }
    }

    /**
     * Gets the number of nodes for the graph.
     *
     * @return the to be size of the {@code graph}
     */
    protected abstract int getSizeOfGraph();
}
