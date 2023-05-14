package org.example.controller.inputlistener.callbacks;

/**
 * Interface with callback methods.
 *
 * @author David Nistor
 */
public interface AddElement {
    /**
     * Is called by {@code InputListener} object after receiving input.
     *
     * @param node1 the first node name id
     * @param node2 the second node name id
     * @param weight the desired weight
     */
    void addEdge(String node1,String node2, Integer weight);

    /**
     * Is called by {@code InputListener} object after receiving input.
     */
    void addNode();
}
