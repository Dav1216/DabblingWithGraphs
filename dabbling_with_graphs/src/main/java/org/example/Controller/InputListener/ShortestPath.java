package org.example.Controller.InputListener;

/**
 * Interface with callback method.
 *
 * @author David Nistor
 */
public interface ShortestPath {
    /**
     * Is called by {@code InputListener} object after receiving input.
     *
     * @param inputDetected1 the first input
     * @param inputDetected2 the second input
     */
    void compute(String inputDetected1, String inputDetected2);
}
