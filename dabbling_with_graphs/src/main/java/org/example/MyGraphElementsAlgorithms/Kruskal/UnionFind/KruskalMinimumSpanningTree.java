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
    private final UnionFind unionFind;
    private final Graph theGraph;
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
        theGraph.edges().forEach(edgesResult::add);
        sortedEdges = sortEdges(edgesResult);
    }

    /**
     * Sort a list of edges by ascending edge weight.
     *
     * @param edgesResult the list to be sorted
     * @return {@code sortedEdges} the sorted list
     * @post {@code (\forall int edge; 0 <= edge && edge < sortedEdges.size() - 1
     * ;sortedEdges.get(edge).getAttribute("weight") <= sortedEdges.get(edge).getAttribute("weight")}
     */
    private List<Edge> sortEdges(List<Edge> edgesResult) {
        return sortedEdges =  edgesResult.stream()
                .sorted(Comparator.comparing(edge -> (int) edge.getAttribute("weight")))
                .collect(Collectors.toList());
    }

    /**
     * Walks through the sorted edges and looks at the two nodes of each edge, if they are already unified, skip,
     * otherwise include and unite.
     *
     * @return the edges in the minimum spanning tree
     */
    public List<Edge> calculateMST() {
        List<Edge> minimumSpanningTree = new ArrayList<>();

        sortedEdges.forEach(edge -> {
            if(!unionFind.connected(getNrFromNode(edge.getNode0()), getNrFromNode(edge.getNode1()))) {
                unionFind.unify(getNrFromNode(edge.getNode0()), getNrFromNode(edge.getNode1()));
                minimumSpanningTree.add(edge);
            }
        });

        return minimumSpanningTree;
    }

    /**
     * Gets a number from the name of the input {@code node} in order to be used in union find.
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
