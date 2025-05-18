import java.util.*;

public class GraphColoringCSPList {

    static int V = 4; // Number of vertices
    static List<List<Integer>> adjList = new ArrayList<>();
    static int[] colors;
    static int M = 3; // Number of colors

    public static void main(String[] args) {
        initializeGraph();

        colors = new int[V];

        if (solve(0)) {
            printSolution();
        } else {
            System.out.println("No solution exists.");
        }
    }

    // Initialize graph as an adjacency list
    static void initializeGraph() {
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }

        // Example graph:
        // 0--1
        // |\ |
        // 2--3

        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 3);
        addEdge(1, 2);
        addEdge(2, 3);
    }

    static void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u); // Undirected graph
    }

    // Recursive function to assign colors
    static boolean solve(int vertex) {
        if (vertex == V)
            return true;

        for (int c = 1; c <= M; c++) {
            if (isSafe(vertex, c)) {
                colors[vertex] = c;
                if (solve(vertex + 1))
                    return true;
                colors[vertex] = 0; // Backtrack
            }
        }

        return false;
    }

    // Check if assigning color c to vertex is safe
    static boolean isSafe(int vertex, int c) {
        for (int neighbor : adjList.get(vertex)) {
            if (colors[neighbor] == c)
                return false;
        }
        return true;
    }

    // Display solution
    static void printSolution() {
        System.out.println("Coloring is possible with " + M + " colors:");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " ---> Color " + colors[i]);
        }
    }
}
