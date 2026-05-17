import java.util.*;

public class Graph {

    private Map<Vertex, List<Vertex>> adjacencyList;

    private Map<Integer, Vertex> vertexMap;

    public Graph() {
        adjacencyList = new LinkedHashMap<>();
        vertexMap = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjacencyList.put(v, new ArrayList<>());
        vertexMap.put(v.getId(), v);
    }

    public void addEdge(int from, int to) {
        Vertex source = vertexMap.get(from);
        Vertex destination = vertexMap.get(to);

        if (source == null || destination == null) {
            System.out.println("Warning: vertex " + from + " or " + to + " not found.");
            return;
        }

        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public void printGraph() {
        System.out.println("\n--- Graph Adjacency List ---");
        for (Map.Entry<Vertex, List<Vertex>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            List<Vertex> neighbors = entry.getValue();
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i));
                if (i < neighbors.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        System.out.println("----------------------------\n");
    }

    public void bfs(int start) {
        Vertex startVertex = vertexMap.get(start);
        if (startVertex == null) {
            System.out.println("Start vertex " + start + " not found.");
            return;
        }

        Set<Vertex> visited = new LinkedHashSet<>();

        Queue<Vertex> queue = new LinkedList<>();

        queue.add(startVertex);
        visited.add(startVertex);

        System.out.print("BFS traversal order: ");

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            System.out.print(current.getId() + " ");

            for (Vertex neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void dfs(int start) {
        Vertex startVertex = vertexMap.get(start);
        if (startVertex == null) {
            System.out.println("Start vertex " + start + " not found.");
            return;
        }

        Set<Vertex> visited = new LinkedHashSet<>();

        System.out.print("DFS traversal order: ");
        dfsRecursive(startVertex, visited);
        System.out.println();
    }

    private void dfsRecursive(Vertex current, Set<Vertex> visited) {
        visited.add(current);
        System.out.print(current.getId() + " ");

        for (Vertex neighbor : adjacencyList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    public int getVertexCount() {
        return adjacencyList.size();
    }

    public int getEdgeCount() {
        int total = 0;
        for (List<Vertex> neighbors : adjacencyList.values()) {
            total += neighbors.size();
        }
        return total / 2;
    }
}
