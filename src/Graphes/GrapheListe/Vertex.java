package Graphes.GrapheListe;

import Conteneurs.Liste;

public class Vertex {
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
}
