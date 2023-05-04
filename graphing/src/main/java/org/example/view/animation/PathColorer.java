package org.example.view.animation;

import org.example.services.MyServices;
import org.graphstream.graph.Node;
import java.util.List;

/**
 * Class that colors the nodes and edges of a graph in a DFS traversal manner.
 *
 * @author David Nistor
 */
public class PathColorer implements Runnable {
    private final List<List<Node>> paths;
    private boolean[] coloredAlready;

    /**
     * Creates a new {@code PathColorer} object.
     *
     * @param paths the paths
     */
    public PathColorer(List<List<Node>> paths, long numberOfNodes) {
        this.paths = paths;
        coloredAlready = new boolean[(int) numberOfNodes];
    }

    /**
     * Paints the nodes in the {@code path}.
     *
     * @param path the list of nodes
     * @param color the desired color
     */
    private void paintPath(List<Node> path, String color){
        for (int i = 0; i < path.size(); ++i) {
            if(i >= 1) {
                Node node1 = path.get(i - 1);
                Node node2 = path.get(i);

                // this is to skip the already colored nodes in the paths where nodes which were already colored exist
                if(coloredAlready[MyServices.getNrFromNode(node2)]) {
                    continue;
                }

                node2.setAttribute("ui.style", "fill-color: " + color + ";");
                coloredAlready[MyServices.getNrFromNode(node2)] = true;
                paintEdges(node1, node2, color);
                sleep();
            }
             else {
                path.get(i).setAttribute("ui.style", "fill-color: " + color + ";");
            }
        }
    }

    /**
     * Paints the edge linking two nodes in the path.
     *
     * @param color the desired color
     * @param node1 the first node
     * @param node2 the second node
     */
    private void paintEdges(Node node1, Node node2, String color) {
        node1.getGraph().edges().filter(edge -> (edge.getNode0() == node1 && edge.getNode1() == node2)
                || (edge.getNode0() == node2 && edge.getNode1() == node1)).findAny()
                .ifPresent(edge -> edge.setAttribute("ui.style", "fill-color: " + color + ";"));
    }

    /**
     * Defines the run behaviour for when the PathColorer is called in a new thread, here for all paths of {@code Node}
     * objects in {@code paths}, it colors the nodes in each path sequentially.
     */
    @Override
    public void run() {
        paths.forEach(path -> paintPath(path, "orange"));
    }

    /**
     * Method to be called to pause the current {@code Thread} execution.
     */
    private void sleep() {
        try {
            Thread.sleep(650);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
