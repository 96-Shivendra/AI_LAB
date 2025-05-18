import java.util.*;

    class Edge {
        String to;
        int cost;

        Edge(String to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    class Node {
        String name;
        int g;  // Cost from start to current node
        int h;  // Heuristic estimate to goal
        Node parent;

        Node(String name, int g, int h, Node parent) {
            this.name = name;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        int f() {
            return g + h;
        }
    }

public class AStarPathfinding {


    public static void main(String[] args) {
        // Graph using Arrays.asList
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

        // Run A* from A to G
        aStarSearch(graph, heuristic, "A", "G");
    }

    public static void aStarSearch(Map<String, List<Edge>> graph, Map<String, Integer> heuristic,
                                   String start, String goal) {

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        Set<String> closed = new HashSet<>();

        open.add(new Node(start, 0, heuristic.get(start), null));

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.name.equals(goal)) {
                printPath(current);
                return;
            }

            if (closed.contains(current.name)) continue;
            closed.add(current.name);

            for (Edge edge : graph.get(current.name)) {
                if (!closed.contains(edge.to)) {
                    int gNew = current.g + edge.cost;
                    int hNew = heuristic.get(edge.to);
                    open.add(new Node(edge.to, gNew, hNew, current));
                }
            }
        }

        System.out.println("No path found.");
    }

    private static void printPath(Node node) {
        List<String> path = new ArrayList<>();
        int totalCost = node.g;

        while (node != null) {
            path.add(node.name);
            node = node.parent;
        }

        Collections.reverse(path);
        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Total cost: " + totalCost);
    }
}
