package org.example.view.animation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.List;

/**
 * Class that contains the thread where the animation will run.
 *
 * @author David Nistor
 */
public class AnimationController {
    private final Thread thread;

    /**
     * Creates a new {@code AnimationController} object.
     *
     * @param theGraph the graph
     * @param layers the layers
     */
    public AnimationController(Graph theGraph, List<List<Node>> layers) {
        thread = new Thread(new LayerColorer(theGraph, layers));
        System.out.println("Animation will begin shortly, look at the graph!");
    }

    /**
     * Starts the animation thread.
     *
     */
    public void startThread() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }


}
