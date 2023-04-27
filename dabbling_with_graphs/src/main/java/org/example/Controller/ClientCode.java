package org.example.Controller;

import org.example.Controller.InputListener.BFS;
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
public class ClientCode implements ShortestPath, MST, BFS {
    private TheGraphModel theModel;
    private MyView theView;

    /**
     * Creates the model, passes the model to the view in its creation, then starts a new thread to run the 
     * input listener.
     */
    private void startGraphing() {
        theModel = new TheGraphModel();
        theView = new MyView(theModel.getTheGraph());

        (new Thread(new InputListener(this))).start();
    }

    /**
     * Implements behaviour for when {@code this} is called as a {@code ShortestPath}
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
     * Implements behaviour for when {@code this} is called as a {@code MST}
     * callback by the {@code InputListener} class: calculates and shows MST.
     */
    @Override
    public void show() {
        theView.showMST(theModel.calculateMST());
    }

    /**
     * Implements behaviour for when {@code this} is called as a {@code BFS}
     * callback by the {@code InputListener} class: calculates and shows BFS.
     *
     * @param sourceNode the input node name
     */
    @Override
    public void calculate(String sourceNode) {
        if(theModel.getTheGraph().getNode(sourceNode) != null) {
            Graph theGraph = theModel.getTheGraph();
            List<List<Node>> layers = theModel.calculateBFS(theGraph.getNode(sourceNode));
            theView.showBFS(layers);
        } else {
            System.out.println("Please press \"b\" and enter an existing node");
        }
    }

    public static void main(String[] args) {
        (new ClientCode()).startGraphing();
    }


}
