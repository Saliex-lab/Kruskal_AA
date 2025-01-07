package Graphes.GrapheListe;

public class Edge {
    private Vertex _successor; 
    private int _weight;      

    // Constructeur
    public Edge(Vertex successor, int weight) {
        this._successor = successor;
        this._weight = weight;
    }

    // Getters
    public Vertex getSuccessor() {
        return _successor;
    }

    public int getWeight() {
        return _weight;
    }

    // Setters 
    public void setSuccessor(Vertex successor) {
        this._successor = successor;
    }

    public void setWeight(int weight) {
        this._weight = weight;
    }
}
