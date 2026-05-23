public class Experiment {

    private long[][] results; // [graphSize][0=BFS, 1=DFS, 2=Dijkstra]
    private static final String[] SIZE_LABELS = {"Small (10 vertices)", "Medium (30 vertices)", "Large (100 vertices)"};
    private static final int[] SIZES = {10, 30, 100};

    public Experiment() {
        results = new long[3][3];
    }


    public void runTraversals(Graph g, int sizeIndex, boolean showOutput) {
        System.out.println("\n=== Running on " + SIZE_LABELS[sizeIndex] + " ===");
        System.out.println("Vertices: " + g.getVertexCount() + "  |  Edges: " + g.getEdgeCount());

        // BFS
        long bfsStart = System.nanoTime();
        g.bfs(0);
        long bfsEnd = System.nanoTime();
        results[sizeIndex][0] = bfsEnd - bfsStart;

        // DFS
        long dfsStart = System.nanoTime();
        g.dfs(0);
        long dfsEnd = System.nanoTime();
        results[sizeIndex][1] = dfsEnd - dfsStart;

        // Dijkstra
        long dijkstraStart = System.nanoTime();
        g.dijkstra(0);
        long dijkstraEnd = System.nanoTime();
        results[sizeIndex][2] = dijkstraEnd - dijkstraStart;

        System.out.println("BFS time:      " + results[sizeIndex][0] + " ns");
        System.out.println("DFS time:      " + results[sizeIndex][1] + " ns");
        System.out.println("Dijkstra time: " + results[sizeIndex][2] + " ns");
    }

    public void runMultipleTests() {
        for (int s = 0; s < SIZES.length; s++) {
            Graph g = buildGraph(SIZES[s]);
            runTraversals(g, s, s == 0);
        }
    }

    private Graph buildGraph(int n) {
        Graph g = new Graph();

        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < n - 1; i++) {
            g.addEdge(i, i + 1, (i % 9) + 1); // weights 1-9
        }

        for (int i = 0; i < n - 2; i++) {
            g.addEdge(i, i + 2, (i % 5) + 2); // weights 2-6
        }

        g.addEdge(0, n - 1, 10);

        return g;
    }

    public void printResults() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     PERFORMANCE RESULTS SUMMARY                          ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-25s | %-14s | %-14s | %-14s ║%n", "Graph Size", "BFS (ns)", "DFS (ns)", "Dijkstra (ns)");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");

        for (int i = 0; i < SIZES.length; i++) {
            System.out.printf("║ %-25s | %-14d | %-14d | %-14d ║%n",
                    SIZE_LABELS[i], results[i][0], results[i][1], results[i][2]);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");

        System.out.println("\n--- Analysis ---");
        System.out.println("• BFS and DFS: O(V+E) — grow linearly with graph size.");
        System.out.println("• Dijkstra: O(V²) with array implementation — grows faster than BFS/DFS.");
        System.out.println("• As graph size increases, Dijkstra takes proportionally more time.");
    }
}
