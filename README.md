# DabblingWithGraphs
I made this Java program as an educational program that allows users to input commands in the command line interface (CLI) to generate and analyze graphs. The program offers two options: generating a random number of nodes graph or inputting a number of nodes. After selecting one of the options, the user can choose between computing the Minimum Spanning Tree (MST) of the graph, the shortest path between two input nodes using Dijkstra's algorithm, or to compute the BFS or DFS. Also, the user can choose to add new nodes or to connect existing ones with weighted edges.

The program uses the GraphStream library to visualize the graphs.

# Requirements
* Java 17 or higher

# Instalation
1. Clone the repository.
2. Import the project into your preferred Java IDE.

# Usage
1. Run the program in your IDE or from the command line.
2. Follow the prompts to select a graph type and analysis method.
3. If generating a graph with user input, enter the number of nodes when prompted.
4. If analyzing shortest paths, enter two nodes to find the shortest path between them.
5. If generating a BFS animation, enter the source node when prompted.
6. If generating a DFS animation, enter the source node when prompted.
7. If adding nodes or edges between nodes, follow the instructions as prompted.

# Design Patterns Used
* Template Method: The AbstractTemplateGenerator class defines the overall structure of the graph generation algorithm and delegates getSizeOfGraph hook method to be implemented by the subclasses.
* Union-Find: The UnionFind data structure is used for the MST calculation algorithm.
* Model-View-Controller (MVC): The program uses the MVC design pattern to separate concerns between the model (graph), user interface (CLI)/ controller (main program logic) and the view.
* Strategy: The program lets the user choose the type of generator at runtime through CLI.

# Demonstrations:

## The shortest weighted path from Node2 to Node3:
![Dijsktra path](graphing/docs/PATH.png)

## The minimum spanning tree of the graph:
![MST](graphing/docs/MST.png)

## Demonstration of BFS starting at Node6:
![BFS search](graphing/docs/BFS2.gif)

## Demonstration of DFS starting at Node6:
![DFS search](graphing/docs/DFS.gif)

## Future plans
#### MUST-HAVE: allowing the user to delete nodes and edges
#### SHOULD-HAVE: create UNDO-REDO facility to keep track of the add and delete commands with the use of Command Design Patern, make two stacks: REDO - UNDO and allow the user to have the option of redoing - undoing of commands 
#### COULD-HAVE: Swing GUI instead of the CLI which is quite tedious to use or, a Java Android App with accounts for each user that would hold past graphs


