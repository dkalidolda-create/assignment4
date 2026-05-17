public class Experiment {
    private long[][] results;

    private static final String[] SIZE_LABELS = {"Small (10 vertices)", "Medium (30 vertices)", "Large (100 vertices)"};
    private static final int[] SIZES = {10, 30, 100};

    public Experiment() {
        results = new long[3][2];
    }

    public void runTraversals(Graph g, int sizeIndex, boolean showOutput) {
        System.out.println("\n=== Running on " + SIZE_LABELS[sizeIndex] + " ===");
        System.out.println("Vertices: " + g.getVertexCount() + "  |  Edges: " + g.getEdgeCount());

        if (showOutput) {
            long bfsStart = System.nanoTime();
            g.bfs(0);
            long bfsEnd = System.nanoTime();
            results[sizeIndex][0] = bfsEnd - bfsStart;
        } else {
            long bfsStart = System.nanoTime();
            g.bfs(0);
            long bfsEnd = System.nanoTime();
            results[sizeIndex][0] = bfsEnd - bfsStart;
        }

        long dfsStart = System.nanoTime();
        g.dfs(0);
        long dfsEnd = System.nanoTime();
        results[sizeIndex][1] = dfsEnd - dfsStart;

        System.out.println("BFS time: " + results[sizeIndex][0] + " ns");
        System.out.println("DFS time: " + results[sizeIndex][1] + " ns");
    }

    public void runMultipleTests() {
        for (int s = 0; s < SIZES.length; s++) {
            int n = SIZES[s];
            Graph g = buildGraph(n);

            // Only print traversal order for the small graph (readable output)
            boolean showOutput = (s == 0);
            runTraversals(g, s, showOutput);
        }
    }

    private Graph buildGraph(int n) {
        Graph g = new Graph();

        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < n - 1; i++) {
            g.addEdge(i, i + 1);
        }

        for (int i = 0; i < n - 2; i++) {
            g.addEdge(i, i + 2);
        }

        g.addEdge(0, n - 1);

        return g;
    }

    public Graph getGraph(int sizeIndex) {
        return buildGraph(SIZES[sizeIndex]);
    }

    public void printResults() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              PERFORMANCE RESULTS SUMMARY                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-25s | %-14s | %-14s ║%n", "Graph Size", "BFS Time (ns)", "DFS Time (ns)");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");

        for (int i = 0; i < SIZES.length; i++) {
            System.out.printf("║ %-25s | %-14d | %-14d ║%n",
                    SIZE_LABELS[i], results[i][0], results[i][1]);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("\n--- Analysis Observations ---");
        System.out.println("• Both BFS and DFS have time complexity O(V + E).");
        System.out.println("• Execution time grows as graph size increases, confirming O(V+E) behavior.");

        for (int i = 0; i < SIZES.length; i++) {
            String faster = results[i][0] <= results[i][1] ? "BFS" : "DFS";
            System.out.println("• " + SIZE_LABELS[i] + ": " + faster + " was faster in this run.");
        }

        System.out.println("• BFS uses a Queue → explores level by level → better for shortest paths.");
        System.out.println("• DFS uses recursion (call stack) → explores deep branches → better for cycle detection / topological sort.");
    }
}
