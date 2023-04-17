package org.example.Controller.InputListener;

import java.util.Scanner;

/**
 * Class that listens for {@code System.in} input.
 *
 *  @author David Nistor
 */
public class InputListener implements Runnable {
        private Scanner scanner;
        private boolean listening;
        private InputListenerCallbackDijsktra callbackDijsktra;
        private InputListenerCallbackMST callbackMST;

    /**
     * Creates a new InputListener object.
     * @param callback the callback object to be passed to this InputListener
     */
    public InputListener(InputListenerCallbackDijsktra callback) {
            this.scanner = new Scanner(System.in);
            this.listening = true;
            this.callbackDijsktra = callback;
            this.callbackMST = (InputListenerCallbackMST) callback;
        }

    /**
     * Defines the run behaviour for when the InputListener is called in a new thread, here it is always looking for
     * inputs and adapts based behaviour based on these inputs while {@code listening == true}.
     */
    @Override
        public void run() {
            while (listening) {
                if (scanner.hasNextLine()) {
                    char theInput = scanner.nextLine().charAt(0);

                    if(theInput == 'p') {
                        System.out.println("Input something of the form \"Node0 - Node5\"");
                        String stringInput = scanner.nextLine();

                        try {
                            String[] s = parseInput(stringInput);
                            callbackDijsktra.inputWasDetected(s[0], s[1]);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Your input is not valid, input \"p\" and try again with existing ones");
                        }

                    } else if(theInput == 'm') {
                        callbackMST.inputWasDetected();
                    } else {
                        System.out.println("No recognizable command, please try again..");
                    }
                }
            }
            scanner.close();
        }

    /**
     * Sets the {@code listening} boolean to false.
     * @modifies listening
     */
    public void stopListening() {
            this.listening = false;
        }

    /**
     Parses the input string to extract two node names, which must be of the form: a string that
     starts with "Node", followed by one or more digits,then zero or more whitespace characters,
     a hyphen, and again zero or more whitespace characters, and ends with "Node" followed by one or more digits.
     Returns an array containing the two node names.
     @param inputDetected the input string to be parsed
     @return an array of two String objects, each containing two node names
     @throws IllegalArgumentException if the input is not of the expected form
     */
    private String[] parseInput(String inputDetected) {
        String pattern = "^Node\\d+\\s*-\\s*Node\\d+$";

            if(inputDetected.matches(pattern)) {
                String[] nodes = inputDetected.split("\\s*-\\s*");
                String node1 = nodes[0].trim();
                String node2 = nodes[1].trim();

                return new String[]{node1, node2};
            } else {
                throw new IllegalArgumentException("The input was not of the form \"Node0\" - \"Node11\"");
            }
    }
}
