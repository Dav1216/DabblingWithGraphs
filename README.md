# DabblingWithGraphs
This Java program allows users to input commands in the command line interface (CLI) to generate and analyze graphs. The program offers two options: generating a random number of nodes graph or inputting a number of nodes. After selecting one of the options, the user can choose between computing the Minimum Spanning Tree (MST) of the graph or the shortest path between two input nodes using Dijkstra's algorithm.

The program uses the GraphStream library to generate and visualize graphs.

# Requirements
* Java 8 or higher
* GraphStream library

# Instalation
1. Clone the repository: git clone https://github.com/yourusername/graph-algorithms.git
2. Import the project into your preferred Java IDE.

#Usage
1. Run the program in your IDE or from the command line.
2. Follow the prompts to select a graph type and analysis method.
3. If generating a graph with user input, enter the number of nodes when prompted.
4. If analyzing shortest paths, enter two nodes to find the shortest path between them.

#Design Patterns Used
* Template Method: The GraphGenerator class defines the overall structure of the graph generation algorithm and delegates specific operations to subclasses.
* Union-Find: The UnionFind data structure is used for the MST calculation algorithm.
* Model-View-Controller (MVC): The program uses the MVC design pattern to separate concerns between the data (graph), user interface (CLI)/ controller (main program logic) and the view.
* Adapter: The NodeForDijkstra class adapts the org.graphstream.graph.Node to be used for calculating the shortest path using the Dijsktra, this is because each node should store a distance and a shortest path to it. 

