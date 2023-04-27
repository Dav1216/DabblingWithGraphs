package org.example.View.Animation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.List;

/**
 * Class that colors the nodes of a graph in a BFS traversal manner.
 *
 * @author David Nistor
 */
public class LayerColorer implements Runnable {
    private final List<List<Node>> layers;
    private final FinishedLayer listener;

    /**
     * Creates a new {@code LayerColorer} object.
     *
     * @param theGraph the graph
     * @param layers the layers
     * @param listener the listener
     */
    public LayerColorer(Graph theGraph, List<List<Node>> layers, FinishedLayer listener) {
        this.layers = layers;
        this.listener = listener;
    }

    /**
     * Paints the nodes in the {@code layer}.
     *
     * @param layer the list of nodes
     * @throws InterruptedException
     */
    private void paintLayer(List<Node> layer, List<Node> previousLayer) throws InterruptedException {
        layer.forEach(node -> node.setAttribute("ui.style", "fill-color: orange;"));
//        paintEdges(layer,previousLayer);
        paintPreviousLayer(previousLayer);
        listener.done();
    }

    /**
     * Paints the nodes in the {@code previousLayer}.
     *
     * @param previousLayer the list of nodes
     */
    private void paintPreviousLayer(List<Node> previousLayer) {
        if(previousLayer != null) {
            previousLayer.forEach(node -> node.setAttribute("ui.style", "fill-color: green;"));
        }
    }

//    /**
//     * Paints the edges linking layers of nodes.
//     */
//    private void paintEdges(List<Node> layer, List<Node> previousLayer) {
//        if(previousLayer != null) {
//            previousLayer.forEach(node -> {
//                node.edges().filter(edge -> (layer.contains(edge.getNode0()) ||
//                        layer.contains(edge.getNode1()))).forEach(edge ->
//                        edge.setAttribute("ui.style", "fill-color: orange;"));
//            });
//        }
//    }

    /**
     * Defines the run behaviour for when the LayerColorer is called in a new thread, here for all layers of {@code Node}
     * objects in {@code layers}, it colors them sequentially.
     */
    @Override
    public void run() {
        List<Node> previousLayer = null;

        for(int i = 0; i < layers.size(); ++i) {

            try {
                paintLayer(layers.get(i), previousLayer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            previousLayer = layers.get(i);
        }

        paintPreviousLayer(layers.get(layers.size() - 1));
    }
}
