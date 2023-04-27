package org.example.View.Animation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.List;

/**
 * Class that contains the thread where the animation will run.
 *
 * @author David Nistor
 */
public class AnimationController implements FinishedLayer {
    private final Thread thread;

    /**
     * Creates a new {@code AnimationController} object.
     *
     * @param theGraph the graph
     * @param layers the layers
     */
    public AnimationController(Graph theGraph, List<List<Node>> layers) {
        thread = new Thread(new LayerColorer(theGraph, layers, this));
        System.out.println("Animation will begin shortly, look at the graph!");
    }

    /**
     * Starts the animation thread.
     *
     * @throws InterruptedException
     */
    public void startThread() throws InterruptedException {
        thread.sleep(3000);
        thread.start();
    }

    /**
     * Implements callback method from {@code FinishedLayer} interface.
     *
     * @throws InterruptedException
     */
    @Override
    public void done() throws InterruptedException {
        thread.sleep(4000);
    }
}
