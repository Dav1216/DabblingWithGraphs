package org.example.controller.inputlistener;

import org.example.controller.inputlistener.callbacks.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final AddElement callbackElement;
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
            this.callbackElement = (AddElement) callback;
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
                  "\n" + " \"b\" to animate a BFS for the graph, \"d\" to animate a DFS for the graph or \n" +
                "\"e\" to add new elements to the graph");
        System.out.println("You can call \"p\", \"m\", \"b\", \"d\" or  \"e\" multiple times");

        while (listening) {
            String input = sc.nextLine();

            if(input.length() > 0) {
                char selection = input.charAt(0);

                switch (selection) {
                    case 'p' -> handlePSelection();
                    case 'm' -> handleMSelection();
                    case 'b' -> handleBSelection();
                    case 'd' -> handleDSelection();
                    case 'e' -> handleESelection();
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
        stringInput = stringInput.replaceAll("\\s", "");
        stringInput = stringInput.replaceAll("\"", "");

        if(stringInput.matches("^Node\\d+-Node\\d+$")) {
            String[] parsedInput = parseInputByHyphen(stringInput);
            callbackDijsktra.compute(parsedInput[0],parsedInput[1]);
        } else {
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
        stringInput = stringInput.replaceAll("\\s", "");
        stringInput = stringInput.replaceAll("\"", "");

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
        stringInput = stringInput.replaceAll("\\s", "");
        stringInput = stringInput.replaceAll("\"", "");

        if(stringInput.matches("Node\\d") || stringInput.matches("Node\\d{1,2}")) {
            callbackDFS.calculateDFS(stringInput);
        } else {
            System.out.println("Your input is not valid, input \"b\" and try again with an existing node");
        }
    }

    /**
     * Method to handle the case when 'e' has been read by the {@code sc}.
     */
    private void handleESelection() {
        System.out.println("Input \"Node\" to create one node or input two nodes separated by a \"-nr-\" to create \n " +
                "a weighted edge like: \"Node0 -6- Node5\"");
        String stringInput = sc.nextLine();
        stringInput = stringInput.replaceAll("\\s", "");
        stringInput = stringInput.replaceAll("\"", "");

        if(stringInput.matches("^Node\\d+-\\d+-Node\\d+$")){
           String[] parsedInput =  parseInputByNumber(stringInput);
            callbackElement.addEdge(parsedInput[0], parsedInput[2], Integer.parseInt(parsedInput[1]));
        } else if(stringInput.matches("Node")) {
            callbackElement.addNode();
        } else {
            System.out.println("Your input is not valid, input \"e\" and try again with correct names of possible nodes");
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
     * Splits a String into two, based on the "-".
     *
     * @param input the input string to be parsed
     * @return a set of two String objects, each containing two node names
     */
    private String[] parseInputByHyphen(String input) {
         return input.split("-");

    }

    /**
     * Parses a String input of the form "^Node\d+-\d+-Node\d+$" into different pieces: "Node\d+", "-\\d+-"
     * and "Node\\d+".
     *
     * @param input the input String
     * @return a String array containing the three components that have been parsed as separate Strings
     */
    private String[] parseInputByNumber(String input) {
        String[] result = new String[3];

        Matcher matcher = Pattern.compile("^Node\\d+").matcher(input);
        Matcher matcher1 = Pattern.compile("-\\d+-").matcher(input);
        Matcher matcher2 = Pattern.compile("Node\\d+$").matcher(input);

        if(matcher.find() && matcher1.find() && matcher2.find()) {
            result[0] = matcher.group(0);
            result[1] = matcher1.group(0).replaceAll("-", "");
            result[2] = matcher2.group(0);
        } else {
            System.out.println("The input is not of the form \"Node0 -3- Node5\"");
        }

        return result;
    }
}
