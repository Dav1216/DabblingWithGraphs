package org.example.controller.inputlistener.callbacks;

/**
 * Interface with callback method.
 *
 * @author David Nistor
 */
public interface BFS {

    /**
     * Is called by {@code InputListener} object after receiving input.
     *
     * @param sourceNode the input node name
     */
    void calculateBFS(String sourceNode);
}
