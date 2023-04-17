package org.example.MyGraphElementsAlgorithms.Kruskal.UnionFind;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds the Kruskal minimum spanning tree algorithm.
 *
 *  @author David Nistor
 */
public class KruskalMinimumSpanningTree {
    private UnionFind unionFind;
    private Graph theGraph;
    private List<Edge> sortedEdges;

    /**
     * Creates a new KruskalMinimumSpanningTree object.
     *
     * @param numberNodes the number of nodes of the graph
     * @param theGraph the graph
     */
    public KruskalMinimumSpanningTree(int numberNodes, Graph theGraph) {
        this.unionFind = new UnionFind(numberNodes);
        this.theGraph = theGraph;

        initializeSortedEdges();
    }

    /**
     * Initializes the sortedEdges to a sorted by weight list of the edges of {@code theGraph}.
     *
     * @modifies sortedEdges
     */
    private void initializeSortedEdges() {
        List<Edge> edgesResult = new ArrayList<>();
        theGraph.edges().forEach(edge -> edgesResult.add(edge));
        sortedEdges = sortEdges(edgesResult);
    }

    /**
     * Sort a list of edges by ascending edge weight.
     *
     * @param edgesResult
     * @return {@code sortedEdges} the sorted list
     * @post {@code (\forall int edge; 0 <= edge && edge < sortedEdges.size() - 1
     * ;sortedEdges.get(edge).getAttribute("weight") <= sortedEdges.get(edge).getAttribute("weight")}
     */
    private List<Edge> sortEdges(List<Edge> edgesResult) {
        List<Edge> sortedEdges =  edgesResult.stream()
                .sorted(Comparator.comparing(edge -> (int) edge.getAttribute("weight")))
                .collect(Collectors.toList());

        return sortedEdges;
    }

    /**
     * Walks through the sorted edges and looks at the two nodes of each edge, if they are already unified, skip,
     * otherwise include and unite.
     *
     * @return the edges in the minimum spanning tree
     */
    public List<Edge> calculateMST() {

        List<Edge> minimumSpanningTree = new ArrayList<>();

        sortedEdges.stream().forEach(edge -> {
            if(!unionFind.connected(getNrFromNode(edge.getNode0()), getNrFromNode(edge.getNode1()))) {
                unionFind.unify(getNrFromNode(edge.getNode0()), getNrFromNode(edge.getNode1()));
                minimumSpanningTree.add(edge);
            }
        });

        return minimumSpanningTree;
    }

    /**
     * Gets a number from the name of the input {@node} in order to be used in union find.
     *
     * @param node the node
     * @return the unique number to be used in union find
     */
    private int getNrFromNode(Node node) {
        String numberString = node.getId().replaceAll("\\D+","");
        int number = Integer.parseInt(numberString);
        return number;
    }
}
