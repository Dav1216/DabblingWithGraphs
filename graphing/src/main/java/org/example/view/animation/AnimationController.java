package org.example.view.animation;

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
     * @param twoDimensionList the layers or paths
     * @param numberOfNodes the number of nodes of the graph
     */
    public AnimationController(List<List<Node>> twoDimensionList, long numberOfNodes) {
        // if numberOfNodes == 0 then we want the BFS search
        if (numberOfNodes > 0) {
            thread = new Thread(new PathColorer(twoDimensionList, numberOfNodes));
        } else {
            thread = new Thread(new LayerColorer(twoDimensionList));
        }
        System.out.println("Animation will begin shortly, look at the graph!");
    }

    /**
     * Starts the animation thread.
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
