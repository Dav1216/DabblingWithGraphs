package org.example.View.Animation;

/**
 * Interface with callback method.
 *
 * @author David Nistor
 */
public interface FinishedLayer {

     /**
      * Is called by {@code LayerColorer} object after painting each layer.
      */
     void done() throws InterruptedException;
}
