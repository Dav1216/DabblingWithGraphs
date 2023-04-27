package org.example.MyGraphElementsAlgorithms.Dijsktra;

import org.graphstream.graph.Node;

import java.util.*;

/**
 * Acts as a wrapper class for {@code org.graphstream.graph.Node} to be used by the {@code DijsktraGraphAlgo} class.
 *
 * @author David Nistor
 */
public class NodeForDijkstra implements Comparable<NodeForDijkstra> {
    private final Node node;
    private final String name;
    private Integer distance = Integer.MAX_VALUE;
    private final Map<NodeForDijkstra, Integer> adjacentNodes;
    private List<NodeForDijkstra> shortestPathToNode;

    /**
     * Creates a new NodeForDijkstra object.
     *
     * @param name the name of the object
     * @param node the {@code org.graphstream.graph.Node} of the object
     */
    public NodeForDijkstra(String name, Node node) {
        this.node = node;
        this.name = name;
        this.adjacentNodes = new HashMap<>();
        this.shortestPathToNode =  new ArrayList<>();
    }

    /**
     * Add an {@code adjacentNode} to the adjacentNodes list.
     *
     * @modifies adjacentNodes
     * @param adjacentNode the {@code NodeForDijkstra} object
     * @param weight the weight from {@code this} to the {@code NodeForDijkstra} object
     */
    public void addAdjacentNodes(NodeForDijkstra adjacentNode, int weight) {
        adjacentNodes.put(adjacentNode, weight);
    }

    /**
     * Gets the {@code org.graphstream.graph.Node} of the object.
     *
     * @return {@code org.graphstream.graph.Node} of the object
     */
    public Node getNode() {
       return node;
    }

    /**
     * Gets the distance towards {@code this} from the {@code NodeForDijkstra} source node object.
     *
     * @return {@code distance} the distance to {@code this} object node
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Gets the adjacent {@code NodeForDijkstra} nodes of this object.
     *
     * @return {@code adjacentNodes} the adjacent {@code NodeForDijkstra} nodes of this object.
     */
    public Map<NodeForDijkstra, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Gets the shortest path of {@code NodeForDijkstra} nodes to this object node.
     *
     * @return {@code shortestPathToNode} the path of {@code NodeForDijkstra} nodes
     */
    public List<NodeForDijkstra> getShortestPathToNode() {
        return shortestPathToNode;
    }

    /**
     * Sets the distance to this {@code NodeForDijkstra} object node.
     *
     * @param distance the distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Sets the shortest path of {@code NodeForDijkstra} nodes to this object.
     *
     * @param shortestPathToNode the path of nodes
     */
    public void setShortestPathToNode(List<NodeForDijkstra> shortestPathToNode) {
        this.shortestPathToNode = shortestPathToNode;
    }

    /**
     * Gets the name of this object.
     *
     * @return {@code name} the name
     */
    public String getName() {
        return name;
    }

    /**
     * Implements compareTo method of the Comparable interface to allow the comparison of {@code NodeForDijkstra}
     * nodes.
     *
     * @param o the {@code NodeForDijkstra} object to be compared to.
     */
    @Override
    public int compareTo(NodeForDijkstra o) {
        return Integer.compare(this.getDistance(), o.getDistance());
    }
}
