package org.example.view.animation;

import org.graphstream.graph.Node;

import java.util.List;

/**
 * Class that colors the nodes of a graph in a BFS traversal manner.
 *
 * @author David Nistor
 */
public class LayerColorer implements Runnable {
    private final List<List<Node>> layers;

    /**
     * Creates a new {@code LayerColorer} object.
     *
     * @param layers the layers
     */
    public LayerColorer(List<List<Node>> layers) {
        this.layers = layers;
    }

    /**
     * Paints the nodes in the {@code layer}.
     *
     * @param layer the list of nodes
     * @param color the desired color
     */
    private void paintLayer(List<Node> layer, String color){
        layer.forEach(node -> node.setAttribute("ui.style", "fill-color: " + color + ";"));
    }

    /**
     * Defines the run behaviour for when the LayerColorer is called in a new thread, here for all layers of {@code Node}
     * objects in {@code layers}, it colors them sequentially.
     */
    @Override
    public void run() {
        for(int i = 0; i < layers.size(); ++i) {
            if(i > 0){
                paintLayer(layers.get(i - 1), "green");
            }
            paintLayer(layers.get(i), "orange");
            sleep();
        }
        paintLayer(layers.get(layers.size() - 1), "green");
    }

    /**
     * Method to be called to pause the current {@code Thread} execution.
     */
    private void sleep() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
