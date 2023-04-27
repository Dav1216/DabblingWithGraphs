package org.example.algorithms.dijsktra;

import org.graphstream.graph.Node;

import java.util.*;

/**
 * Acts as a wrapper class for {@code org.graphstream.graph.Node} to be used by the {@code DijsktraGraphAlgo} class.
 *
 * @author David Nistor
 */
public class NodeWrapper implements Comparable<NodeWrapper> {
    private final Node node;
    private final String name;
    private Integer distance = Integer.MAX_VALUE;
    private final Map<NodeWrapper, Integer> adjacentNodes;
    private List<NodeWrapper> shortestPathToNode;

    /**
     * Creates a new {@code NodeWrapper} object.
     *
     * @param name the name of the object
     * @param node the {@code org.graphstream.graph.Node} of the object
     */
    public NodeWrapper(String name, Node node) {
        this.node = node;
        this.name = name;
        this.adjacentNodes = new HashMap<>();
        this.shortestPathToNode =  new ArrayList<>();
    }

    /**
     * Add an {@code adjacentNode} to the adjacentNodes list.
     *
     * @modifies adjacentNodes
     * @param adjacentNode the {@code NodeWrapper} object
     * @param weight the weight from {@code this} to the {@code NodeWrapper} object
     */
    public void addAdjacentNodes(NodeWrapper adjacentNode, int weight) {
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
     * Gets the distance towards {@code this} from the {@code NodeWrapper} source node object.
     *
     * @return {@code distance} the distance to {@code this} object node
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Gets the adjacent {@code NodeWrapper} nodes of this object.
     *
     * @return {@code adjacentNodes} the adjacent {@code NodeWrapper} nodes of this object.
     */
    public Map<NodeWrapper, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Gets the shortest path of {@code NodeWrapper} nodes to this object node.
     *
     * @return {@code shortestPathToNode} the path of {@code NodeWrapper} nodes
     */
    public List<NodeWrapper> getShortestPathToNode() {
        return shortestPathToNode;
    }

    /**
     * Sets the distance to this {@code NodeWrapper} object node.
     *
     * @param distance the distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Sets the shortest path of {@code NodeWrapper} nodes to this object.
     *
     * @param shortestPathToNode the path of nodes
     */
    public void setShortestPathToNode(List<NodeWrapper> shortestPathToNode) {
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
     * Implements compareTo method of the Comparable interface to allow the comparison of {@code NodeWrapper}
     * nodes.
     *
     * @param o the {@code NodeWrapper} object to be compared to.
     */
    @Override
    public int compareTo(NodeWrapper o) {
        return Integer.compare(this.getDistance(), o.getDistance());
    }
}
