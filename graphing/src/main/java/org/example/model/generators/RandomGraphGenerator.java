package org.example.model.generators;

import java.util.Random;

/**
 * Concrete template implementation that overrides hook method {@code getSizeOfGraph}.
 *
 *  @author David Nistor
 */
public class RandomGraphGenerator extends AbstractTemplateGenerator {
    @Override
    protected int getSizeOfGraph() {
        Random random = new Random();
        return random.nextInt(10, 20);
    }
}
