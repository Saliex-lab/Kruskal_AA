package Graphes.GrapheMatrice;

import Conteneurs.Liste;
import Conteneurs.ListeTriable;
import Conteneurs.Edge;
import Conteneurs.Vertex;
import Utils.FileManager;
import Utils.Timer;

public class GrapheMatrice {
    // Nombre de sommets du graphe
    private final int NmbSommets;
    // Matrice d'adjacence du graphe. Utilisation d'un tableau à deux dimensions pour représenter la matrice.
    private final int[][] MatriceAdjacence;
    // Liste des aretes de l'arbre recouvrant minimal
    private Liste<Edge> PPAR;

    // Constructeur à partir d'une liste d'entiers
    public GrapheMatrice(Liste<Integer> _graph) throws Exception {
        this.NmbSommets = _graph.get(0);
        MatriceAdjacence = new int[this.NmbSommets][this.NmbSommets];
        for (int i = 0; i < this.NmbSommets; i++) {
            for (int j = 0; j < this.NmbSommets; j++) {
                MatriceAdjacence[i][j] = 0;
            }
        }
        if (this.NmbSommets > 0) {
            int index = 1;
            while (index < _graph.lenght()) {
                int sommet = _graph.get(index++);
                while (index < _graph.lenght()) {
                    int connexe = _graph.get(index++);
                    if (connexe == 0) break;
                    int poids = _graph.get(index++);
                    MatriceAdjacence[sommet - 1][connexe - 1] = poids;
                    MatriceAdjacence[connexe - 1][sommet - 1] = poids;
                }
            }
        }
    }

    // Affiche la matrice d'adjacence
    public void printMatrix() throws Exception {
        for (int k = 0; k < NmbSommets * 5 + 1; k++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0; i < NmbSommets; i++) {
            System.out.print("|");
            for (int j = 0; j < NmbSommets; j++) {
                System.out.printf("%4d|", MatriceAdjacence[i][j]);
            }
            System.out.println();
            for (int k = 0; k < NmbSommets * 5 + 1; k++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    // Calcule le poids de l'arbre recouvrant à partir de la liste des aretes dans PPAR
    private int getPoids(Liste<Edge> ppar) throws Exception {
        int weight = 0;
        for (int i = 0; i < ppar.lenght(); i++) {
            weight += ppar.get(i).getWeight();
        }
        return weight;
    }

    // Vérifie si le graphe est connexe à partir de la liste des aretes dans PPAR
    private boolean IsConnexe(Liste<Edge> ppar) throws Exception {
        boolean[] visited = new boolean[NmbSommets];
        dfs(ppar, 0, visited);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    // Parcours en profondeur du graphe pour vérifier la connexité
    private void dfs(Liste<Edge> ppar, int vertex, boolean[] visited) throws Exception {
        visited[vertex] = true;
        for (int i = 0; i < ppar.lenght(); i++) {
            Edge edge = ppar.get(i);
            int u = findSource(edge) - 1;
            int v = edge.getSuccessor().GetNumber() - 1;
            if (u == vertex && !visited[v]) {
                dfs(ppar, v, visited);
            } else if (v == vertex && !visited[u]) {
                dfs(ppar, u, visited);
            }
        }
    }


    // Algorithme de Kruskal
    public void kruskal() throws Exception {
        int[] parent = initializeParent(NmbSommets);
        Timer.start();
        ListeTriable<Edge> edges = getAllEdges();
        edges.sort();
        PPAR = new Liste<>();
        for (int i = 0; i < edges.lenght(); i++) {
            Edge edge = edges.get(i);
            int u = find(parent, findSource(edge) - 1);
            int v = find(parent, edge.getSuccessor().GetNumber() - 1);
            if (u != v) {
                PPAR.add(edge);
                parent[u] = v;
            }
            if (PPAR.lenght() == NmbSommets - 1) {
                break;
            }
        }
        Timer.stop();
        PPAR = sortPPAR();
        printResult();
    }

    // Trie la liste des arêtes de l'arbre recouvrant minimal dans l'ordre des sommets
    private Liste<Edge> sortPPAR() throws Exception {
        Liste<Edge> sortedPPAR = new Liste<>();
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = 0; j < NmbSommets; j++) {
                for (int k = 0; k < PPAR.lenght(); k++) {
                    Edge edge = PPAR.get(k);
                    if (findSource(edge) == i + 1 && edge.getSuccessor().GetNumber() == j + 1) {
                        sortedPPAR.add(edge);
                    }
                }
            }
        }
        return sortedPPAR;
    }


    // Récupère toutes les arêtes du graphe
    private ListeTriable<Edge> getAllEdges() throws Exception {
        ListeTriable<Edge> edges = new ListeTriable<>();
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = i + 1; j < NmbSommets; j++) {
                if (MatriceAdjacence[i][j] != 0) {
                    Vertex destination = new Vertex(j + 1);
                    edges.add(new Edge(destination, MatriceAdjacence[i][j]));
                }
            }
        }
        return edges;
    }

    // Initialisation du tableau parent
    private int[] initializeParent(int order) {
        int[] parent = new int[order];
        for (int i = 0; i < order; i++) {
            parent[i] = i;
        }
        return parent;
    }

    // Compression de chemin
    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    // Trouve la source d'une arête
    private int findSource(Edge edge) throws Exception {
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = (i + 1); j < NmbSommets; j++) {
                if (MatriceAdjacence[i][j] != 0 && MatriceAdjacence[i][j] == edge.getWeight()) {
                    return i + 1; // Return the source vertex (1-based index)
                }
            }
        }
        throw new Exception("Source introuvable pour l'arête : " + edge);
    }

    // Génère l'affichage du résultat de l'algorithme de Kruskal
    public void printResult() throws Exception {
        FileManager.sb.setLength(0);
        System.out.println("Matrice d'adjacence du graphe :");
        printMatrix();
        if (IsConnexe(PPAR)) {
            FileManager.sb.append("LE GRAPHE EST CONNEXE.\n");
        } else {
            FileManager.sb.append("LE GRAPHE N'EST PAS CONNEXE.\n");
        }
        FileManager.sb.append(getPoids(PPAR)).append("\n");
        for (int i = 0; i < PPAR.lenght(); i++) {
            Edge edge = PPAR.get(i);
            FileManager.sb.append("(")
                    .append(findSource(edge))
                    .append(" -> ")
                    .append(edge.getSuccessor().GetNumber())
                    .append(" : ")
                    .append(edge.getWeight())
                    .append(")\n");
        }
        FileManager.sb.append("TEMPS CPU : ").append(Timer.getElapsedTime()).append(" ns\n");
    }
}
