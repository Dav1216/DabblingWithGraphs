package org.example.algorithms.dfs;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * Class that holds the DFS algorithm.
 *
 * @author David Nistor
 */
public class DepthFirstSearch {
        private final Graph theGraph;
        private List<List<Node>> allPaths;

        /**
         * Creates a new {@code DepthFirstSearch} object.
         *
         * @param theGraph the graph
         */
        public DepthFirstSearch(Graph theGraph) {
            this.theGraph = theGraph;
            this.allPaths = new ArrayList<>();
        }

        /**
         * Executes the DFS algorithm.
         *
         * @param startingNode the starting node of the algorithm
         */
        public void startDFS(Node startingNode) {
            Map<Node, Boolean> visited = new HashMap<>();
            theGraph.nodes().forEach(node -> visited.put(node, false));
            visited.put(startingNode, true);

            List<Node> list = new ArrayList<>();
            recursiveDFS(startingNode, visited, list);
        }

    /**
     * Helper method for {@code startDFS}, executes DFS recursively.
     *
     * @param node the input node
     * @param visited the visited nodes list
     * @param list the path
     */
        private void recursiveDFS(Node node, Map<Node, Boolean> visited, List<Node> list) {

            List<Node> localList = new ArrayList<>(list);
            localList.add(node);
            visited.put(node, true);
            List<Node> neighbours = node.neighborNodes().filter(neighbourNode -> !visited.get(neighbourNode)).toList();

            if(neighbours.size() == 0) {
                saveToAllPaths(localList);
            } else {
                for (Node neighbour: neighbours) {
                    if(!visited.get(neighbour)) {
                        recursiveDFS(neighbour, visited, localList);
                    }
                }
            }
        }

    /**
     * Helper method for {@code recursiveDFS}. When reaching an end point in the DFS, the path accumulated until that
     * node will be saved to the {@code allPaths} list.
     *
     * @param path the path to be saved
     */
    private void saveToAllPaths(List<Node> path) {
            allPaths.add(path);
        }

    /**
     * Gets the {@code allPaths} list with all the paths of the DFS.
     *
     * @return {@code allPaths}
     */
    public List<List<Node>> getAllPathsInDfs() {
        return allPaths;
    }
}
