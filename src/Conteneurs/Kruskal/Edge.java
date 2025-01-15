package Conteneurs.Kruskal;

public class Edge implements Comparable<Edge> {
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

    //Sort with weight
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this._weight, other._weight);
    }
    
    @Override
    public String toString() {
        return "Edge{" +
               "successor=" + (_successor != null ? _successor.GetNumber() : "null") +
               ", weight=" + _weight +
               '}';
    }
}
