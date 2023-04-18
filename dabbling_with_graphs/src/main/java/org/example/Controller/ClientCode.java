package org.example.Controller;

import org.example.Controller.InputListener.InputListener;
import org.example.Controller.InputListener.ShortestPath;
import org.example.Controller.InputListener.MST;
import org.example.Model.TheGraphModel;
import org.example.View.MyView;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.List;

/**
 * Class that executes the client code.
 *
 * @author David Nistor
 */
public class ClientCode implements ShortestPath, MST {
    private TheGraphModel theModel;
    private MyView theView;

    /**
     * Initiates {@code theModel}, {@code theGraph} and the {@code il}: starts the model and an {@code InputListener}.
     */
    private void startGraphing() {
        theModel = new TheGraphModel();
        theView = new MyView(theModel.getTheGraph());

        System.out.println("Enter \"p\" if you would want to calculate the shortest " +
                "path between two nodes or \"m\" calculate a minimum spanning tree on the generated weighted graph");
        System.out.println("You can call \"p\" or \"m\" multiple times");

        (new Thread(new InputListener(this))).start();
    }

    /**
     * Implements behaviour for when {@code this} is called as a {@code InputListenerCallbackDijsktra}
     * callback by the {@code InputListener} class: calculates and shows the shortest path between {@code source} node
     * and {@code target}.
     *
     * @param source the source node name received by input
     * @param target the target node name received by input
     */
    @Override
    public void compute(String source, String target) {
        Graph theGraph = theModel.getTheGraph();
        if(theGraph.getNode(source) == null || theGraph.getNode(target) == null) {
            System.out.println("These nodes do not exist, input \"p\" and try again with existing ones");
        } else {
            List<Node> shortestPath = theModel.calculateShortestPath(theGraph.getNode(source), theGraph.getNode(target));
            if(target.equals(source)) {
                System.out.println("A path does not exist since the nodes are one in the same");
            } else if(shortestPath.isEmpty()) {
                System.out.println("The two nodes are not connected," +
                        " thus a shortest path does not exist unfortunately..");
            } else {
                theView.showShortestPath(shortestPath, theGraph.getNode(target));
            }
        }
    }

    /**
     * Implements behaviour for when {@code this} is called as a {@code InputListenerCallbackMST}
     * callback by the {@code InputListener} class: calculates and shows MST.
     */
    @Override
    public void show() {
        theView.showMST(theModel.calculateMST());
    }

    public static void main(String[] args) {
        (new ClientCode()).startGraphing();
    }
}
