package Graphes.GrapheListe;
import Conteneurs.Edge;
import Conteneurs.Liste;
import Conteneurs.ListeTriable;
import Conteneurs.Vertex;
import Utils.FileManager;
import Utils.Timer;

public class GrapheListe {
    private int _order = 0;
    private Liste<Vertex> _graph = new Liste<>();
    private ListeTriable<Edge> _mstEdges = new ListeTriable<>();

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

    public void KruskalAlgo() throws Exception {
        Timer.start();
    
        // Récupérer toutes les arêtes sans doublons
        ListeTriable<Edge> edges = getAllEdges();
    
        // Trier les arêtes par poids croissant
        edges.sort();
    
        // Initialiser le tableau parent pour les composantes connexes
        int[] parent = initializeParent(_order);
    
        // Construire le MST
        buildMST(edges, parent);
    
        Timer.stop();
    
        saveMSTResult(Timer.getElapsedTime());
    }
    
    private ListeTriable<Edge> getAllEdges() throws Exception {
        ListeTriable<Edge> edges = new ListeTriable<>();
    
        for (int i = 0; i < _graph.lenght(); i++) {
            Vertex vertex = _graph.get(i);
            Liste<Edge> vertexEdges = vertex.GetEdges();
    
            for (int j = 0; j < vertexEdges.lenght(); j++) {
                Edge edge = vertexEdges.get(j);
                edges.add(edge);
            }
        }
    
        return edges;
    }

    private int[] initializeParent(int order) {
        int[] parent = new int[order + 1]; 
    
        for (int i = 1; i <= order; i++) {
            parent[i] = i;
        }
    
        return parent; 
    }

    private void buildMST(Liste<Edge> edges, int[] parent) throws Exception {
        for (int i = 0; i < edges.lenght(); i++) {
            Edge edge = edges.get(i);
    
            int u = find(parent, findSource(edge));
            int v = find(parent, edge.getSuccessor().GetNumber());
    
            if (u != v) {
                _mstEdges.add(edge); 
                parent[u] = v;
            }
    
            if (_mstEdges.lenght() == _order - 1) {
                break;
            }
        }
    }

    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]); // Compression de chemin
        }
        return parent[vertex];
    }

    private int findSource(Edge edge) throws Exception {
        for (int i = 0; i < _graph.lenght(); i++) {
            Vertex vertex = _graph.get(i);
            Liste<Edge> edges = vertex.GetEdges();

            for (int j = 0; j < edges.lenght(); j++) {
                if (edges.get(j) == edge) { // Trouve l'arête
                    return vertex.GetNumber();
                }
            }
        }
        throw new Exception("Source introuvable pour l'arête : " + edge);
    }

    public void saveMSTResult(long computationTime) throws Exception {
        boolean isConnected = _mstEdges.lenght() == (_order - 1);

        FileManager.sb.setLength(0); 

        if (isConnected) {
            FileManager.sb.append("LE GRAPHE EST CONNEXE\n");
        } else {
            FileManager.sb.append("LE GRAPHE N'EST PAS CONNEXE\n");
        }

        int totalWeight = 0;
        for (int i = 0; i < _mstEdges.lenght(); i++) {
            totalWeight += _mstEdges.get(i).getWeight();
        }
        FileManager.sb.append(totalWeight).append("\n");

        ListeTriable<String> formattedEdges = new ListeTriable<>();
        for (int i = 0; i < _mstEdges.lenght(); i++) {
            Edge edge = _mstEdges.get(i);
            int source = findSource(edge);
            int destination = edge.getSuccessor().GetNumber();

            if (source > destination) {
                int temp = source;
                source = destination;
                destination = temp;
            }

            formattedEdges.add("(" + source + " -> " + destination + " : " + edge.getWeight() + ")");
        }

        formattedEdges.sort();

        for (int i = 0; i < formattedEdges.lenght(); i++) {
            FileManager.sb.append(formattedEdges.get(i)).append("\n");
        }

        FileManager.sb.append("TEMPS CPU : ").append(computationTime).append(" ns\n");
    }
}