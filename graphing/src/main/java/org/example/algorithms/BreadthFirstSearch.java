package org.example.algorithms;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * Class that holds the BFS algorithm.
 *
 * @author David Nistor
 */
public class BreadthFirstSearch {
    private Queue<Node> theQueue;
    private final Graph theGraph;

    /**
     * Creates a new {@code BreadthFirstSearch} object.
     *
     * @param theGraph the graph
     */
    public BreadthFirstSearch(Graph theGraph) {
        this.theGraph = theGraph;
        this.theQueue = new LinkedList<>();
    }

    /**
     * Executes the BFS algorithm.
     *
     * @param startingNode the starting node of the algorithm
     * @return the list of layers of the BFS
     */
    public List<List<Node>> startBFS(Node startingNode) {
        List<Node> allQueueHistory = new ArrayList<>();
        theQueue.add(startingNode);
        allQueueHistory.add(startingNode);

        Map<Node, Boolean> visited = new HashMap<>();
        theGraph.nodes().forEach(node -> visited.put(node, false));
        visited.put(startingNode, true);

        while(!theQueue.isEmpty()) {
            Node node = theQueue.poll();

            node.neighborNodes().forEach(neighbourNode ->
            {
                if(!visited.get(neighbourNode)) {
                    allQueueHistory.add(neighbourNode);
                    theQueue.add(neighbourNode);
                    visited.put(neighbourNode, true);
                }
            });
        }

        return calculateLayers(allQueueHistory);
    }

    /**
     * Calculates the layers of the BFS.
     *
     * @param allQueueHistory a list containing all BFS {@code theQueue} history
     * @return the list of layers of the BFS
     */
    private List<List<Node>> calculateLayers(List<Node> allQueueHistory) {
        List<List<Node>> result = new ArrayList<>();
        List<Node> currentLayer = new ArrayList<>();
        currentLayer.add(allQueueHistory.get(0));
        allQueueHistory.remove(allQueueHistory.get(0));
        result.add(currentLayer);

        while(!currentLayer.isEmpty()) {
            List<Node> nextCurrentLayer = new ArrayList<>();

            currentLayer.forEach(node ->  node.neighborNodes().forEach(neighbourNode -> {
                if(allQueueHistory.contains(neighbourNode)) {
                    nextCurrentLayer.add(neighbourNode);
                    allQueueHistory.remove(neighbourNode);
             }
            }));

            if(nextCurrentLayer.size() != 0) {
                result.add(nextCurrentLayer);
            }

            currentLayer = nextCurrentLayer;
        }

        return result;
    }
}
