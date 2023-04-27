package org.example.controller.inputlistener;

/**
 * Interface with callback method.
 *
 * @author David Nistor
 */
public interface ShortestPath {
    /**
     * Is called by {@code InputListener} object after receiving input.
     *
     * @param inputDetected1 the first input node name
     * @param inputDetected2 the second input node name
     */
     void compute(String inputDetected1, String inputDetected2);
}
