package org.example.View;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.SwingViewer;
import java.util.List;

/**
 * Class that holds the view of the program.
 *
 * @author David Nistor
 */
public class MyView {
    private Graph theGraph;

    /**
     * Creates {@code MyView} object.
     * @param theGraph the graph
     * @param nodes the list of nodes of the graph
     */
    public MyView(Graph theGraph, List<Node> nodes) {
        this.theGraph = theGraph;

        setNodeLabels(nodes);
        setEdgesLabels();
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
        System.out.println("The MST has been shown, it has been shown");
        resetGraphAppearence();
        minSTree.stream().forEach(edge -> edge.setAttribute("ui.style", "fill-color: red;"));
    }

    /**
     * Plots the shortest path, consisting of the nodes in the {@code shortestPath} plus {@code target} node.
     *
     * @param shortestPath the nodes in the shortest path
     * @param target the target node
     */
    public void showShortestPath(List<Node> shortestPath, Node target) {
        resetGraphAppearence();
        System.out.println("A path exists, it has been shown");

        for(int i = 0; i < shortestPath.size(); ++i) {

            if(i != shortestPath.size() - 1) {

                Node node1 = shortestPath.get(i);
                Node node2 = shortestPath.get(i + 1);

                theGraph.edges().filter(edge -> (edge.getNode1() == node1 && edge.getNode0() == node2)
                                || (edge.getNode0() == node1 && edge.getNode1() == node2))
                        .findAny().ifPresent(edge -> edge.setAttribute("ui.style", "fill-color: red;"));
            } else {

                Node node1 = shortestPath.get(i);

                theGraph.edges().filter(edge -> (edge.getNode1() == target && edge.getNode0() == node1)
                                || (edge.getNode0() == target && edge.getNode1() == node1))
                        .findAny().ifPresent(edge -> edge.setAttribute("ui.style", "fill-color: red;"));
            }
        }

    }

    /**
     * Resets the colors of the edges of the graph.
     */
    private void resetGraphAppearence() {
        theGraph.edges().forEach(edge -> edge.setAttribute("ui.style", "fill-color: black;"));
    }

    /**
     * Displays the weight labels of each edge in the {@code theGraph}.
     */
    private void setEdgesLabels() {
        theGraph.edges().forEach(edge -> {
            edge.setAttribute("ui.label",
                    "" +  edge.getAttribute("weight"));
            edge.setAttribute("ui.style",
                    "text-alignment: center; text-padding: 10; text-size: 20;text-offset: 10px, 0px;");
        });
    }

    /**
     * Displays the names of the nodes.
     *
     * @param nodes the list of nodes
     */
    private void setNodeLabels(List<Node> nodes) {
        nodes.stream().forEach(
                node -> {
                    node.setAttribute("ui.label", node.getId());
                    node.setAttribute("ui.style",
                            "text-alignment: right; text-padding: 10; text-size: 20;text-offset: 20px, 0px;");
                }
        );
    }

}
