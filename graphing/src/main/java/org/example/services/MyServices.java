package org.example.services;

import org.graphstream.graph.Node;

/**
 * Class that contains services needed in multiple classes.
 *
 * @author David Nistor
 */
public class MyServices {

    /**
     * Gets a number from the name of the input {@code node}.
     *
     * @param node the node
     * @return the number
     */
    public static int getNrFromNode(Node node) {
        String numberString = node.getId().replaceAll("\\D+","");
        return Integer.parseInt(numberString);
    }
}
