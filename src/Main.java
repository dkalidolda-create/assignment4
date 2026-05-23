
public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Graph Traversal and Representation System  ║");
        System.out.println("╚══════════════════════════════════════════════╝");


        System.out.println("\n[PART 1] Small Graph – Structure Demonstration (10 vertices)\n");

        Graph smallGraph = new Graph();


        for (int i = 0; i < 10; i++) {
            smallGraph.addVertex(new Vertex(i));
        }


        smallGraph.addEdge(0, 1, 4);
        smallGraph.addEdge(0, 2, 1);
        smallGraph.addEdge(1, 3, 1);
        smallGraph.addEdge(1, 4, 5);
        smallGraph.addEdge(2, 5, 2);
        smallGraph.addEdge(2, 6, 8);
        smallGraph.addEdge(3, 7, 3);
        smallGraph.addEdge(4, 8, 2);
        smallGraph.addEdge(5, 9, 6);
        smallGraph.addEdge(6, 7, 1);
        smallGraph.addEdge(8, 9, 3);


        smallGraph.printGraph();


        System.out.println("[BFS] Starting from vertex 0:");
        long bfsStart = System.nanoTime();
        smallGraph.bfs(0);
        long bfsEnd = System.nanoTime();
        System.out.println("BFS execution time: " + (bfsEnd - bfsStart) + " ns\n");


        System.out.println("[DFS] Starting from vertex 0:");
        long dfsStart = System.nanoTime();
        smallGraph.dfs(0);
        long dfsEnd = System.nanoTime();
        System.out.println("DFS execution time: " + (dfsEnd - dfsStart) + " ns\n");


        System.out.println("[DIJKSTRA] Shortest paths from vertex 0:");
        long dijkstraStart = System.nanoTime();
        smallGraph.dijkstra(0);
        long dijkstraEnd = System.nanoTime();
        System.out.println("Dijkstra execution time: " + (dijkstraEnd - dijkstraStart) + " ns\n");

        // ---------------------------------------------------------------
        // PART 2: Performance Experiments
        // ---------------------------------------------------------------
        System.out.println("\n[PART 2] Performance Experiments – Multiple Graph Sizes\n");

        Experiment experiment = new Experiment();
        experiment.runMultipleTests();
        experiment.printResults();

        System.out.println("\n✔ All experiments completed successfully.");
    }
}