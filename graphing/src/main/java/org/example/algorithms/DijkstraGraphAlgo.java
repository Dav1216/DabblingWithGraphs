package org.example.algorithms;

import org.example.algorithms.NodeWrapper;

import java.util.*;
import java.util.stream.Stream;

/**
 * Class that holds the Dijsktra shortest path algorithm.
 *
 * @author David Nistor
 */
public class DijkstraGraphAlgo {
    /**
     * Calculate the shortest path of each {@code NodeWrapper} node of the connected graph that includes
     * {@code NodeWrapper} sourceNode.
     *
     * @param sourceNode the source {@code NodeWrapper} node
     */
    public void calculateShortestPath(NodeWrapper sourceNode) {
        sourceNode.setDistance(0);
        Set<NodeWrapper> settledNodes = new HashSet<>();
        Queue<NodeWrapper> unsettledNodes = new PriorityQueue<>(Collections.singleton(sourceNode));

        while(!unsettledNodes.isEmpty()) {
            NodeWrapper currentNode = unsettledNodes.poll();

            currentNode.getAdjacentNodes().entrySet().stream().filter(nodeIntegerEntry ->
                    !settledNodes.contains(nodeIntegerEntry.getKey()))
                    .forEach(nodeIntegerEntry -> {
                        evaluateDistanceAndPath(nodeIntegerEntry.getKey(), nodeIntegerEntry.getValue(), currentNode);
                        unsettledNodes.add(nodeIntegerEntry.getKey());
                    });

            settledNodes.add(currentNode);
        }
    }

    /**
     * Decides whether to include an edge in the shortest path to a node.
     *
     * @param adjacentNode the target {@code NodeWrapper} adjacentNode
     * @param edgeWeight the weight between the {@code sourceNode} and {@code adjacentNode}
     * @param sourceNode the source {@code NodeWrapper} node
     */
    private void evaluateDistanceAndPath(NodeWrapper adjacentNode, Integer edgeWeight, NodeWrapper sourceNode) {
        Integer newDistance = sourceNode.getDistance() + edgeWeight;

        if(newDistance < adjacentNode.getDistance()) {
            adjacentNode.setDistance(newDistance);
            adjacentNode.setShortestPathToNode(Stream.concat(sourceNode.getShortestPathToNode().stream(), Stream.of(sourceNode)).toList());
        }
    }
}

