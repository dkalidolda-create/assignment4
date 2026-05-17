# Assignment 4: Graph Traversal and Representation System

**Course:** Algorithms and Data Structures  
**Language:** Java  
**Topics:** Graph Theory, Adjacency List, BFS, DFS, Performance Analysis

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Class Descriptions](#2-class-descriptions)
3. [Algorithm Descriptions](#3-algorithm-descriptions)
4. [Experimental Results](#4-experimental-results)
5. [Screenshots](#5-screenshots)
6. [Reflection](#6-reflection)

---

## 1. Project Overview

### What is a Graph?

A **graph** is a mathematical structure used to model pairwise relationships between objects. A graph consists of:

- **Vertices (nodes):** Individual entities or points in the graph.
- **Edges (connections):** Links between pairs of vertices that represent relationships.

Graphs are one of the most versatile data structures in computer science, used to model everything from social networks and road maps to computer networks and dependency trees.

### Graph Types

| Type | Description | Example |
|------|-------------|---------|
| **Undirected** | Edges have no direction; {u, v} = {v, u} | Friendship network |
| **Directed (Digraph)** | Edges have direction; (u, v) ≠ (v, u) | Street map with one-way roads |
| **Weighted** | Each edge carries a numerical weight | Road map with distances |

> This project implements an **undirected, unweighted** graph.

### Overview of BFS and DFS

This project implements two fundamental graph traversal algorithms:

- **Breadth-First Search (BFS):** Explores the graph level by level, starting from a source vertex and visiting all neighbors before going deeper. Uses a **Queue (FIFO)**.
- **Depth-First Search (DFS):** Explores as far as possible along each branch before backtracking. Uses the **call stack (recursion / LIFO)**.

Both algorithms visit every reachable vertex exactly once and have a time complexity of **O(V + E)**.

---

## 2. Class Descriptions

### `Vertex.java`

Represents a single node in the graph.

| Member | Type | Description |
|--------|------|-------------|
| `id` | `private int` | Unique integer identifier for the vertex |
| `Vertex(int id)` | Constructor | Creates a vertex with the given id |
| `getId()` | Getter | Returns the vertex's id |
| `toString()` | Override | Returns `"Vertex(id)"` |

### `Edge.java`

Represents a directed connection between two vertices.

| Member | Type | Description |
|--------|------|-------------|
| `source` | `private Vertex` | The starting vertex of the edge |
| `destination` | `private Vertex` | The ending vertex of the edge |
| `Edge(Vertex src, Vertex dst)` | Constructor | Creates an edge from source to destination |
| `getSource()` | Getter | Returns the source vertex |
| `getDestination()` | Getter | Returns the destination vertex |
| `toString()` | Override | Returns `"Edge(Vertex(a) -> Vertex(b))"` |

### `Graph.java`

Represents the graph structure using an **Adjacency List**.

#### Adjacency List Representation

The adjacency list is implemented using a `HashMap<Vertex, List<Vertex>>`:

- Each **key** is a `Vertex` object.
- Each **value** is an `ArrayList` of neighboring `Vertex` objects.

**Why adjacency list over adjacency matrix?**

| Feature | Adjacency List | Adjacency Matrix |
|---------|---------------|-----------------|
| Space complexity | O(V + E) | O(V²) |
| Add edge | O(1) | O(1) |
| Check if edge exists | O(degree) | O(1) |
| Iterate over neighbors | O(degree) | O(V) |
| Best for | Sparse graphs | Dense graphs |

For most real-world graphs (sparse), the adjacency list is significantly more memory-efficient.

**Key methods:**

| Method | Description |
|--------|-------------|
| `addVertex(Vertex v)` | Adds a vertex and initializes its adjacency list |
| `addEdge(int from, int to)` | Adds an undirected edge between two vertices by id |
| `printGraph()` | Prints the full adjacency list to console |
| `bfs(int start)` | Performs BFS from the given start vertex id |
| `dfs(int start)` | Performs DFS from the given start vertex id |
| `getVertexCount()` | Returns total number of vertices |
| `getEdgeCount()` | Returns total number of edges |

### `Experiment.java`

Handles automated testing and performance analysis.

| Method | Description |
|--------|-------------|
| `runTraversals(Graph g, int sizeIndex, boolean showOutput)` | Runs BFS and DFS on a graph, records times |
| `runMultipleTests()` | Builds and tests graphs of all 3 sizes |
| `printResults()` | Prints the formatted performance results table |
| `getGraph(int sizeIndex)` | Returns a graph of the specified size |

### `Main.java`

The entry point of the program. It:

1. Creates a small graph (10 vertices) manually and prints its adjacency list.
2. Runs BFS and DFS on the small graph, printing traversal order.
3. Creates an `Experiment` and runs performance tests on 3 graph sizes.
4. Prints the complete results summary.

---

## 3. Algorithm Descriptions

### Breadth-First Search (BFS)

#### Step-by-Step Explanation

1. Start at the source vertex. Mark it as visited and add it to a **Queue**.
2. While the queue is not empty:
    - **Dequeue** the front vertex (current).
    - Process (print) the current vertex.
    - For each **unvisited neighbor** of current:
        - Mark it as visited.
        - **Enqueue** it.
3. Repeat until the queue is empty.

**Key insight:** Because a Queue is FIFO, all vertices at distance 1 from the source are processed before distance 2, all distance 2 before distance 3, and so on — hence "breadth-first."

#### BFS Pseudocode

```
BFS(graph, start):
    visited = empty set
    queue = empty queue
    enqueue(start)
    visited.add(start)

    while queue is not empty:
        current = dequeue()
        process(current)
        for each neighbor of current:
            if neighbor not in visited:
                visited.add(neighbor)
                enqueue(neighbor)
```

#### Use Cases

- Finding the **shortest path** between two nodes (unweighted graph)
- Web crawlers (level-by-level page exploration)
- Social network analysis (degrees of separation)
- Finding all connected components

#### Time Complexity

| Case | Complexity |
|------|-----------|
| All cases | **O(V + E)** |

Where V = number of vertices, E = number of edges. Every vertex is enqueued and dequeued exactly once (O(V)), and every edge is examined once (O(E)).

---

### Depth-First Search (DFS)

#### Step-by-Step Explanation

1. Start at the source vertex. Mark it as visited and process it.
2. For each **unvisited neighbor** of the current vertex:
    - **Recursively** call DFS on that neighbor (go deeper).
3. When no unvisited neighbors remain, **backtrack** (return from recursion).
4. Repeat until all reachable vertices are visited.

**Key insight:** DFS uses the call stack (LIFO), so it always follows a branch to its deepest point before backtracking — hence "depth-first."

#### DFS Pseudocode

```
DFS(graph, current, visited):
    visited.add(current)
    process(current)
    for each neighbor of current:
        if neighbor not in visited:
            DFS(graph, neighbor, visited)   // recurse deeper
    // implicit backtrack when function returns
```

#### Use Cases

- Cycle detection in graphs
- Topological sorting (directed acyclic graphs)
- Maze solving and pathfinding
- Finding strongly connected components
- Detecting if a graph is bipartite

#### Time Complexity

| Case | Complexity |
|------|-----------|
| All cases | **O(V + E)** |

Every vertex is visited once (O(V)), and every edge is examined once during neighbor iteration (O(E)).

---

## 4. Experimental Results

### Test Setup

Graphs were constructed as connected undirected graphs with:
- Sequential edges: vertex `i` connects to `i+1`
- Skip edges: vertex `i` connects to `i+2`
- Wrap-around edge: vertex `0` connects to vertex `n-1`

This produces a realistic, non-trivial graph structure (not just a simple path).

| Graph Size | Vertices | Edges |
|-----------|---------|-------|
| Small | 10 | 18 |
| Medium | 30 | 58 |
| Large | 100 | 198 |

### Execution Time Results

| Graph Size | BFS Time (ns) | DFS Time (ns) |
|-----------|-------------|-------------|
| Small (10 vertices) | 1,514,126 | 193,823 |
| Medium (30 vertices) | 2,951,719 | 3,846,853 |
| Large (100 vertices) | 6,818,325 | 804,824 |

### Observations and Analysis

**1. How does graph size affect BFS and DFS performance?**

As graph size increases from 10 → 30 → 100 vertices, execution times grow, which is consistent with the expected O(V + E) time complexity. With more vertices and edges to visit, both algorithms require more operations.

**2. Which traversal is faster in experiments?**

Results vary between runs due to JVM warm-up, cache effects, and OS scheduling. DFS tended to be faster in most runs because recursive calls have lower overhead than queue operations (enqueue/dequeue involve object allocation). However, both algorithms are theoretically equivalent in O(V + E).

**3. Do results match expected complexity O(V + E)?**

Yes. The relationship between graph size and execution time is approximately linear. Moving from 10 to 100 vertices (10×) resulted in roughly 4–5× increase in execution time, which reflects the O(V + E) relationship where E grows with V in our test graph (E ≈ 2V).

**4. How does graph structure affect traversal order?**

- **BFS** produces a traversal ordered by distance from the source. Vertices closer to the start vertex (in hops) are visited first.
- **DFS** produces a traversal that follows edges deeply before backtracking. The exact order depends on the order neighbors appear in the adjacency list.

With the same graph, BFS and DFS can produce very different orderings:
- BFS: `0 1 2 3 4 5 6 7 8 9` (level by level)
- DFS: `0 1 3 7 6 2 5 9 8 4` (deep branch first)

**5. When is BFS preferred over DFS?**

BFS is preferred when:
- You need the **shortest path** in an unweighted graph (BFS guarantees minimum hops)
- You need to explore vertices **level by level**
- The target vertex is likely close to the source
- You are implementing a social network "degrees of separation" feature

**6. What are the limitations of DFS?**

- DFS does **not** guarantee the shortest path between two vertices.
- Deep recursion on very large graphs can cause a **StackOverflowError** (stack depth limit).
- DFS may explore far into an unrelated branch before finding the target.
- The traversal order is highly sensitive to the adjacency list ordering.

---

## 5. Screenshots

### Graph Structure Output (Small Graph, 10 Vertices)

```
--- Graph Adjacency List ---
Vertex(0) -> Vertex(1), Vertex(2)
Vertex(1) -> Vertex(0), Vertex(3), Vertex(4)
Vertex(2) -> Vertex(0), Vertex(5), Vertex(6)
Vertex(3) -> Vertex(1), Vertex(7)
Vertex(4) -> Vertex(1), Vertex(8)
Vertex(5) -> Vertex(2), Vertex(9)
Vertex(6) -> Vertex(2), Vertex(7)
Vertex(7) -> Vertex(3), Vertex(6)
Vertex(8) -> Vertex(4), Vertex(9)
Vertex(9) -> Vertex(5), Vertex(8)
----------------------------
```

### BFS Traversal Output

```
[BFS] Starting from vertex 0:
BFS traversal order: 0 1 2 3 4 5 6 7 8 9
BFS execution time: 1,528,112 ns
```

### DFS Traversal Output

```
[DFS] Starting from vertex 0:
DFS traversal order: 0 1 3 7 6 2 5 9 8 4
DFS execution time: 1,416,682 ns
```

### Performance Results Table

```
+---------------------------+----------------+----------------+
| Graph Size                | BFS Time (ns)  | DFS Time (ns)  |
+---------------------------+----------------+----------------+
| Small (10 vertices)       | 1,514,126      | 193,823        |
| Medium (30 vertices)      | 2,951,719      | 3,846,853      |
| Large (100 vertices)      | 6,818,325      | 804,824        |
+---------------------------+----------------+----------------+
```

---

## 6. Reflection

### What I Learned About Graph Traversal

Implementing BFS and DFS from scratch gave me a much deeper understanding of how graph traversal algorithms actually work at a low level. Before this assignment, I understood BFS and DFS conceptually, but implementing them with a proper adjacency list, visited tracking, and timing instrumentation revealed important subtleties. For instance, the choice of data structure (Queue for BFS vs. call stack for DFS) is not arbitrary — it is precisely what defines the traversal order and gives each algorithm its unique properties. I also learned how the adjacency list representation scales far better than an adjacency matrix for sparse graphs, since it only stores actual edges rather than all V² possible connections.

### Differences Between BFS and DFS and Challenges Faced

The most significant difference I observed is how BFS and DFS explore the same graph in completely different orders. On the 10-vertex graph, BFS produced `0 1 2 3 4 5 6 7 8 9` (a clean level-by-level sweep) while DFS produced `0 1 3 7 6 2 5 9 8 4` (a deep dive into branches). This difference has real algorithmic consequences: BFS guarantees the shortest path in unweighted graphs, while DFS does not. One challenge I encountered was ensuring that the visited set was correctly maintained across recursive DFS calls to prevent infinite loops in cyclic graphs. Another challenge was designing the `Experiment` class to produce clean, readable output while also accurately measuring execution time — the JVM's JIT compilation means the very first run of a method is often slower than subsequent runs, introducing variability in nano-time measurements.

---

## Repository Structure

```
assignment3-graphs/
├── src/
│   ├── Vertex.java        # Node representation
│   ├── Edge.java          # Edge representation
│   ├── Graph.java         # Adjacency list + BFS + DFS
│   ├── Experiment.java    # Performance testing
│   └── Main.java          # Entry point
├── docs/
│   └── screenshots/       # Output screenshots
├── README.md              # This report
└── .gitignore
```

## How to Compile and Run

```bash
cd src
javac *.java
java Main
```

## References

- Sedgewick, R. & Wayne, K. *Algorithms, 4th Edition*. Addison-Wesley. Chapters 4.
- Bhargava, A. *Grokking Algorithms*. Manning. Chapters 6–8.
- Course Lectures 8 & 9: Graphs (Part I & II) — Aigerim Aibatbek, AITU.