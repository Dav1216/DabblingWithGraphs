package org.example.view;

import org.example.view.animation.AnimationController;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.SwingViewer;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class that holds the view of the program.
 *
 * @author David Nistor
 */
public class MyView {
    private final Graph theGraph;

    /**
     * Creates {@code MyView} object.
     * @param theGraph the graph
     */
    public MyView(Graph theGraph) {
        this.theGraph = theGraph;
        setNodeLabels(theGraph.nodes());
        setEdgesLabels(theGraph.edges());
        startGUI();
    }

    /**
     * Creates the {@code SwingViewer} object.
     */
    private void startGUI() {
        SwingViewer viewer = new SwingViewer(theGraph, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(SwingViewer.CloseFramePolicy.EXIT);
        viewer.addDefaultView(true);
    }

    /**
     * Plots the minimum spanning tree of the weighted graph.
     *
     * @param minSTree the edges of the minimum spanning tree
     */
    public void showMST(List<Edge> minSTree) {
        System.out.println("The MST has been shown");
        resetGraphAppearence();
        minSTree.forEach(edge -> edge.setAttribute("ui.style", "fill-color: orange;"));
    }

    /**
     * Plots the shortest path, consisting of the nodes in the {@code shortestPath} plus {@code target} node.
     *
     * @param shortestPath the nodes in the shortest path
     * @param target the target node
     */
    public void showShortestPath(List<Node> shortestPath, final Node target) {
        resetGraphAppearence();
        System.out.println("A path exists, it has been shown");
        int size = shortestPath.size();

        for(int i = 0; i < size; ++i) {
            Node node1 = shortestPath.get(i);
            Node node2 = (i != size - 1) ? shortestPath.get(i + 1) : target;
            theGraph.edges().filter(edge -> (edge.getNode1() == node1 && edge.getNode0() == node2)
                            || (edge.getNode0() == node1 && edge.getNode1() == node2))
                    .findAny().ifPresent(edge -> edge.setAttribute("ui.style", "fill-color: orange;"));
        }
    }

    /**
     * Plots sequentially the {@code layers} of the BFS.
     *
     * @param layers the layers of the BFS
     */
    public void showBFS(List<List<Node>> layers) {
        resetGraphAppearence();
        (new AnimationController(layers, 0)).startThread();
    }

    /**
     * Plots sequentially the {@code paths} of the DFS.
     *
     * @param paths the paths of the DFS
     */
    public void showDFS(List<List<Node>> paths) {
        resetGraphAppearence();
        (new AnimationController(paths, theGraph.nodes().count())).startThread();
    }

    /**
     * Resets the colors of the edges and nodes of the graph.
     */
    private void resetGraphAppearence() {
        theGraph.nodes().forEach(node -> node.setAttribute("ui.style", "fill-color: black;"));
        theGraph.edges().forEach(edge -> edge.setAttribute("ui.style", "fill-color: black;"));
    }

    /**
     * Displays the weight labels of each edge in the {@code theGraph}.
     */
    private void setEdgesLabels(Stream<Edge> edges) {
        edges.forEach(edge -> {
            edge.setAttribute("ui.label", edge.getAttribute("weight").toString());
            edge.setAttribute("ui.style","text-alignment: center; " +
                    "text-padding: 10; text-size: 20;text-offset: 10px, 0px;");
        });
    }

    /**
     * Displays the names of the nodes.
     *
     * @param nodes the list of nodes
     */
    private void setNodeLabels(Stream<Node> nodes) {
        nodes.forEach(
                node -> {
                    node.setAttribute("ui.label", node.getId());
                    node.setAttribute("ui.style","text-alignment: right;" +
                            " text-padding: 10; text-size: 20;text-offset: 20px, 0px;");
                }
        );
    }

    /**
     * This is called to refresh the view when new elements are added to the model.
     */
    public void myNotify() {
        resetGraphAppearence();
        setNodeLabels(theGraph.nodes());
        setEdgesLabels(theGraph.edges());
    }
}
