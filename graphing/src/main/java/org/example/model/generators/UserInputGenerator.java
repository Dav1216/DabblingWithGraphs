package org.example.model.generators;

import java.util.Scanner;

/**
 * Concrete template implementation that overrides hook method {@code getSizeOfGraph}.
 *
 *  @author David Nistor
 */
public class UserInputGenerator extends AbstractTemplateGenerator {
    Scanner sc;
    @Override
    protected int getSizeOfGraph() {
        sc = new Scanner(System.in);
        System.out.println("Input the number of nodes in the graph(between 10 and 20):");
        int sizeOfGraph = 0;

        while(!(sizeOfGraph >= 10 && sizeOfGraph <= 20)) {
            sizeOfGraph = sc.nextInt();

            if(!(sizeOfGraph >= 10 && sizeOfGraph <= 20)) {
                System.out.println("Invalid input!!!(10 - 20)");
            }
        }

        return sizeOfGraph;
    }
}
