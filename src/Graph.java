import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class Graph {

    // Adjacency list: maps each vertex to its list of weighted edges
    private Map<Vertex, List<Edge>> adjacencyList;

    // Stores all vertex objects indexed by their id for quick lookup
    private Map<Integer, Vertex> vertexMap;


    public Graph() {
        adjacencyList = new LinkedHashMap<>();
        vertexMap = new HashMap<>();
    }


    public void addVertex(Vertex v) {
        adjacencyList.put(v, new ArrayList<>());
        vertexMap.put(v.getId(), v);
    }


    public void addEdge(int from, int to, int weight) {
        Vertex source = vertexMap.get(from);
        Vertex destination = vertexMap.get(to);

        if (source == null || destination == null) {
            System.out.println("Warning: vertex " + from + " or " + to + " not found.");
            return;
        }

        // Undirected: add both directions with the same weight
        adjacencyList.get(source).add(new Edge(source, destination, weight));
        adjacencyList.get(destination).add(new Edge(destination, source, weight));
    }


    public void printGraph() {
        System.out.println("\n--- Graph Adjacency List ---");
        for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            List<Edge> edges = entry.getValue();
            for (int i = 0; i < edges.size(); i++) {
                Edge e = edges.get(i);
                System.out.print(e.getDestination() + "(w=" + e.getWeight() + ")");
                if (i < edges.size() - 1) System.out.print(", ");
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

            // Enqueue all unvisited neighbors
            for (Edge edge : adjacencyList.get(current)) {
                Vertex neighbor = edge.getDestination();
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

        for (Edge edge : adjacencyList.get(current)) {
            Vertex neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
            }
        }
    }


    public void dijkstra(int start) {
        int n = vertexMap.size();

        // Map vertex ids to array indices
        int[] ids = new int[n];
        int index = 0;
        for (int id : vertexMap.keySet()) {
            ids[index++] = id;
        }

        // dist[i] = shortest known distance from start to vertex ids[i]
        int[] dist = new int[n];

        // visited[i] = true if vertex ids[i] has been finalized
        boolean[] visited = new boolean[n];

        // Initialize all distances to infinity
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        // Distance from start to itself is 0
        int startIndex = getIndex(ids, start);
        dist[startIndex] = 0;

        // Process all vertices
        for (int count = 0; count < n; count++) {

            // Step 3: Pick unvisited vertex with smallest distance
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            // All remaining vertices are unreachable
            if (dist[u] == Integer.MAX_VALUE) break;

            // Step 5: Mark current vertex as visited
            visited[u] = true;

            // Step 4: Update distances of neighbors
            Vertex current = vertexMap.get(ids[u]);
            for (Edge edge : adjacencyList.get(current)) {
                int neighborId = edge.getDestination().getId();
                int v = getIndex(ids, neighborId);
                int newDist = dist[u] + edge.getWeight();

                // If shorter path found — update
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                }
            }
        }

        // Print results
        System.out.println("\n--- Dijkstra's Shortest Path from Vertex " + start + " ---");
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("Vertex " + ids[i] + " : unreachable");
            } else {
                System.out.println("Vertex " + ids[i] + " : distance = " + dist[i]);
            }
        }
        System.out.println("-----------------------------------------------------\n");
    }


    private int getIndex(int[] ids, int id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) return i;
        }
        return -1;
    }

    public int getVertexCount() {
        return adjacencyList.size();
    }

    public int getEdgeCount() {
        int total = 0;
        for (List<Edge> edges : adjacencyList.values()) {
            total += edges.size();
        }
        return total / 2;
    }
}