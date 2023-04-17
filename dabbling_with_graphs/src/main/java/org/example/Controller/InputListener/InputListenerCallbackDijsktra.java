package org.example.Controller.InputListener;

/**
 * Interface with callback method.
 *
 * @author David Nistor
 */
public interface InputListenerCallbackDijsktra {
    /**
     * Is called by {@code InputListener} object after receiving input.
     *
     * @param inputDetected1 the first input
     * @param inputDetected2 the second input
     */
    public abstract void inputWasDetected(String inputDetected1, String inputDetected2);
}
