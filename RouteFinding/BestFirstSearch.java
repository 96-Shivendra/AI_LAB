import java.util.*;

public class BestFirstSearch {

    static class Edge {
        String to;
        int cost;

        Edge(String to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class Node {
        String name;
        int h; // heuristic value
        Node parent;

        Node(String name, int h, Node parent) {
            this.name = name;
            this.h = h;
            this.parent = parent;
        }
    }

    public static void main(String[] args) {
        // Graph using adjacency list
        Map<String, List<Edge>> graph = new HashMap<>();
        graph.put("A", Arrays.asList(new Edge("B", 1), new Edge("C", 4)));
        graph.put("B", Arrays.asList(new Edge("D", 2), new Edge("E", 5)));
        graph.put("C", Arrays.asList(new Edge("F", 3)));
        graph.put("D", Arrays.asList(new Edge("G", 1)));
        graph.put("E", Arrays.asList(new Edge("G", 2)));
        graph.put("F", Arrays.asList(new Edge("G", 5)));
        graph.put("G", new ArrayList<>()); // Goal node

        // Heuristic values
        Map<String, Integer> heuristic = new HashMap<>();
        heuristic.put("A", 7);
        heuristic.put("B", 6);
        heuristic.put("C", 5);
        heuristic.put("D", 4);
        heuristic.put("E", 3);
        heuristic.put("F", 6);
        heuristic.put("G", 0); // Goal

        // Run Best-First Search from A to G
        bestFirstSearch(graph, heuristic, "A", "G");
    }

    public static void bestFirstSearch(Map<String, List<Edge>> graph,
                                       Map<String, Integer> heuristic,
                                       String start, String goal) {

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.h));
        Set<String> visited = new HashSet<>();

        open.add(new Node(start, heuristic.get(start), null));

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (visited.contains(current.name)) continue;
            visited.add(current.name);

            if (current.name.equals(goal)) {
                printPath(current);
                return;
            }

            for (Edge edge : graph.get(current.name)) {
                if (!visited.contains(edge.to)) {
                    open.add(new Node(edge.to, heuristic.get(edge.to), current));
                }
            }
        }

        System.out.println("No path found.");
    }

    private static void printPath(Node node) {
        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(node.name);
            node = node.parent;
        }
        Collections.reverse(path);
        System.out.println("Best-First Search Path: " + String.join(" -> ", path));
    }
}
