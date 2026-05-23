
public class Edge {

    private Vertex source;

    private Vertex destination;

    private int weight;


    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }


    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Edge(" + source + " -> " + destination + ", weight=" + weight + ")";
    }
}