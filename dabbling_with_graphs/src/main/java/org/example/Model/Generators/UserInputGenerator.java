package org.example.Model.Generators;

import java.util.Scanner;

/**
 * Concrete template implementation that overrides hook method {@code getSizeOfGraph}.
 *
 *  @author David Nistor
 */
public class UserInputGenerator extends AbstractTemplateGenerator {
    @Override
    protected int getSizeOfGraph() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of nodes in the graph(between 10 and 20):");
        int sizeOfGraph = sc.nextInt();

        if (!(sizeOfGraph >= 10 && sizeOfGraph <= 20)) {
            System.out.println("Invalid input!!!");
            getSizeOfGraph();
        }

        return sizeOfGraph;
    }
}
