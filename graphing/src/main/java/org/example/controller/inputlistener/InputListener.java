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
    private final DFS callbackDFS;
    private final Scanner sc;

    /**
     * Creates a new InputListener object.
     * @param callback the callback object to be passed to this InputListener
     */
    public InputListener(ShortestPath callback) {
            this.callbackDijsktra = callback;
            this.callbackMST = (MST) callback;
            this.callbackBFS = (BFS) callback;
            this.callbackDFS = (DFS) callback;
            this.sc = new Scanner(System.in);
        }

    /**
     * Defines the run behaviour for when the InputListener is called in a new thread, here it is always looking for
     * inputs and adapts based behaviour based on these inputs while {@code listening == true}.
     */
    @Override
    public void run() {
        System.out.println("Enter \"p\" if you would want to calculate the shortest " +
                "path between two nodes, \"m\" to calculate a minimum spanning tree on the generated weighted graph, " +
                  "\n" + " \"b\" to animate a BFS for the graph, or \"d\" to animate a DFS for the graph");
        System.out.println("You can call \"p\", \"m\", \"b\" or \"d\" multiple times");

        while (listening) {
            String input = sc.nextLine();

            if(input.length() > 0) {
                char selection = input.charAt(0);

                switch (selection) {
                    case 'p' -> handlePSelection();
                    case 'm' -> handleMSelection();
                    case 'b' -> handleBSelection();
                    case 'd' -> handleDSelection();
                    default -> System.out.println("No recognizable command, please try again..");
                }
            }
        }
    }

    /**
     * Method to handle the case when 'p' has been read by the {@code sc}.
     */
    private void handlePSelection() {
        System.out.println("Input the source and target nodes; something of the form: \"Node0 - Node5\"");
        String stringInput = sc.nextLine();

        try {
            List<String> parsedInput = parseInput(stringInput);
            callbackDijsktra.compute(parsedInput.get(0), parsedInput.get(1));
        } catch (IllegalArgumentException e) {
            System.out.println("Your input is not valid, input \"p\" and try again with existing ones");
        }
    }

    /**
     * Method to handle the case when 'm' has been read by the {@code sc}.
     */
    private void handleMSelection() {
        callbackMST.show();
    }

    /**
     * Method to handle the case when 'b' has been read by the {@code sc}.
     */
    private void handleBSelection() {
        System.out.println("Input the starting node; something of the form \"Node0\"");
        String stringInput = sc.nextLine();

        if(stringInput.matches("Node\\d") || stringInput.matches("Node\\d{1,2}")) {
            callbackBFS.calculateBFS(stringInput);
        } else {
            System.out.println("Your input is not valid, input \"b\" and try again with an existing node");
        }
    }

    /**
     * Method to handle the case when 'd' has been read by the {@code sc}.
     */
    private void handleDSelection() {
        System.out.println("Input the starting node; something of the form \"Node0\"");
        String stringInput = sc.nextLine();

        if(stringInput.matches("Node\\d") || stringInput.matches("Node\\d{1,2}")) {
            callbackDFS.calculateDFS(stringInput);
        } else {
            System.out.println("Your input is not valid, input \"b\" and try again with an existing node");
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
