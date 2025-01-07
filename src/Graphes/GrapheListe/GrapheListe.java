package Graphes.GrapheListe;
import Conteneurs.Liste;

public class GrapheListe {
    private int _order = 0;
    private Liste<Vertex> _graph = new Liste<>();

    public GrapheListe(Liste<Integer> graphL) throws Exception {
        if (graphL != null && graphL.lenght() > 0) {
            try {
                _order = graphL.get(0);
            } catch (Exception e) {
                throw new Exception("Erreur lors de la récupération de l'ordre du graphe.", e);
            }
    
            for (int i = 1; i <= _order; i++) {
                _graph.add(new Vertex(i));
            }
    
            int index = 1; 
            while (index < graphL.lenght()) {
                int num_vertex = graphL.get(index);
                index++;
    
                while (index < graphL.lenght() && graphL.get(index) != 0) {
                    int succ = graphL.get(index);     
                    int weight = graphL.get(index + 1); 
                    index += 2;
    
                    for (int i = 0; i < _graph.lenght(); i++) {
                        Vertex v = _graph.get(i);
                        if (v.GetNumber() == num_vertex) {
                            Vertex successor = null;
                            for (int j = 0; j < _graph.lenght(); j++) {
                                if (_graph.get(j).GetNumber() == succ) {
                                    successor = _graph.get(j);
                                    break;
                                }
                            }
    
                            if (successor != null) {
                                v.addEdge(successor, weight);
                            }
                        }
                    }
                }
    
                if (index < graphL.lenght() && graphL.get(index) == 0) {
                    index++;
                }
            }
        } else {
            throw new IllegalArgumentException("Liste de données de graphe invalide ou vide.");
        }
    }
    public void KruskalAlgo(int v_start) throws Exception {
        //TODO
    }
}
