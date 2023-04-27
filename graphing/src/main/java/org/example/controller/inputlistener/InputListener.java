package org.example.controller.inputlistener;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class that listens for {@code System.in} input.
 *
 *  @author David Nistor
 */
public class InputListener implements Runnable {
    private boolean listening = true;
    private final ShortestPath callbackDijsktra;
    private final MST callbackMST;
    private final BFS callbackBFS;

    /**
     * Creates a new InputListener object.
     * @param callback the callback object to be passed to this InputListener
     */
    public InputListener(ShortestPath callback) {
            this.callbackDijsktra = callback;
            this.callbackMST = (MST) callback;
            this.callbackBFS = (BFS) callback;
        }

    /**
     * Defines the run behaviour for when the InputListener is called in a new thread, here it is always looking for
     * inputs and adapts based behaviour based on these inputs while {@code listening == true}.
     */
    @Override
    public void run() {
        System.out.println("Enter \"p\" if you would want to calculate the shortest " +
                "path between two nodes or \"m\" calculate a minimum spanning tree on the generated weighted graph, " +
                "or \"b\" to animate a BFS for the graph");
        System.out.println("You can call \"p\", \"m\" or \"b\" multiple times");
        Scanner sc = new Scanner(System.in);

        while (listening) {
            char selection = sc.nextLine().charAt(0);

            if (selection == 'p') {
                System.out.println("Input something of the form \"Node0 - Node5\"");
                String stringInput = sc.nextLine();

                try {
                    List<String> parsedInput = parseInput(stringInput);
                    callbackDijsktra.compute(parsedInput.get(0), parsedInput.get(1));
                } catch (IllegalArgumentException e) {
                    System.out.println("Your input is not valid, input \"p\" and try again with existing ones");
                }
            } else if (selection == 'm') {
                callbackMST.show();
            } else if (selection == 'b') {
                System.out.println("Input something of the form \"Node0\"");
                String stringInput = sc.nextLine();

                if(stringInput.matches("Node\\d") || stringInput.matches("Node\\d{1,2}")) {
                    callbackBFS.calculate(stringInput);
                } else {
                    System.out.println("Your input is not valid, input \"b\" and try again with an existing node");
                }
            } else {
                System.out.println("No recognizable command, please try again..");
            }
        }
    }

    /**
     * Sets the {@code listening} boolean to false.
     *
     * @modifies listening
     */
    public void stopListening() {
            this.listening = false;
        }

    /**
     * Parses the input string to extract two node names, which must be of the form: a string that
     * starts with "Node", followed by one or more digits,then zero or more whitespace characters,
     * a hyphen, and again zero or more whitespace characters, and ends with "Node" followed by one or more digits.
     * Returns an array containing the two node names.
     *
     * @param input the input string to be parsed
     * @return a set of two String objects, each containing two node names
     * @throws IllegalArgumentException if the input is not of the expected form
     */
    private List<String> parseInput(String input) {
        String pattern = "^Node\\d+\\s*-\\s*Node\\d+$";

        if(input.matches(pattern)) {
            String[] nodes = input.split("\\s*-\\s*");
            return Arrays.stream(nodes).map(String::trim).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("The input was not of the form \"Node0\" - \"Node11\"");
        }
    }
}
