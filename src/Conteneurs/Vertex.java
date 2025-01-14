package Conteneurs;

public class Vertex implements Comparable<Vertex>{
    private int _number;
    private Liste<Edge> _edges = new Liste<>();

    public Vertex(int number) {
        _number = number;
    }

    public void addEdge(Vertex successor, int weight) {
        _edges.add(new Edge(successor, weight));
    }

    // GETTERS
    public int GetNumber() {
        return _number;
    }
    public Liste<Edge> GetEdges() {
        return _edges;
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this._number, other._number);
    }

    @Override
    public String toString() {
        return "Vertex{" +
               "number=" + _number +
               ", edges=" + _edges +
               '}';
    }
}
