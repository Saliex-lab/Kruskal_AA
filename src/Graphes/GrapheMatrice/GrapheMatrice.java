package Graphes.GrapheMatrice;

import Conteneurs.Liste;

public class GrapheMatrice {
    // Nombre de sommets du graphe
    private int NmbSommets;
    // Matrice d'adjacence du graphe. Utilisation d'un tableau à deux dimensions pour représenter la matrice.
    private int[][] MatriceAdjacence;

    // Constructeur avec une matrice d'adjacence vide
    public GrapheMatrice(int NombreSommets) throws Exception {
        this.NmbSommets = NombreSommets;
        MatriceAdjacence = new int[NombreSommets][NombreSommets];
        for (int i = 0; i < NombreSommets; i++) {
            for (int j = 0; j < NombreSommets; j++) {
                MatriceAdjacence[i][j] = 0;
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

    // Affiche le plus petit arbre recouvrant par le biais de la liste des aretes dans PPAR.
    public void printPPAR(Liste<int[]> PPAR) throws Exception {
        if (IsConnexe(PPAR)) {
            System.out.println("LE GRAPHE EST CONNEXE.");
        } else {
            System.out.println("LE GRAPHE N'EST PAS CONNEXE.");
        }
        for (int i = 0; i < PPAR.lenght(); i++) {
            System.out.println("(" + PPAR.get(i)[0]+1 + " -> " + PPAR.get(i)[1]+1 + " : " + MatriceAdjacence[PPAR.get(i)[0]][PPAR.get(i)[1]] + ")");
        }
    }

    // Vérifie si le graphe est connexe à partir de la liste des aretes dans PPAR
    private boolean IsConnexe(Liste<int[]> ppar) throws Exception {
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
    private void dfs(Liste<int[]> ppar, int vertex, boolean[] visited) throws Exception {
        visited[vertex] = true;
        for (int i = 0; i < ppar.lenght(); i++) {
            int[] edge = ppar.get(i);
            if (edge[0] == vertex && !visited[edge[1]]) {
                dfs(ppar, edge[1], visited);
            } else if (edge[1] == vertex && !visited[edge[0]]) {
                dfs(ppar, edge[0], visited);
            }
        }
    }

    // Donne l'arête la plus lourde de la liste
    private int[] getAreteLourde(Liste<int[]> liste) throws Exception {
        int lourd = 0;
        for (int i = 1; i < liste.lenght(); i++) {
            if (MatriceAdjacence[liste.get(i)[0]][liste.get(i)[1]] > MatriceAdjacence[liste.get(lourd)[0]][liste.get(lourd)[1]]) {
                lourd = i;
            }
        }
        return liste.get(lourd);
    }

    // Retourne la liste sans l'arête lourde
    private Liste<int[]> removeAreteLourde(Liste<int[]> liste, int[] lourd) throws Exception {
        Liste<int[]> resListe = new Liste<int[]>();
        for (int k = 0; k < liste.lenght(); k++) {
            if (liste.get(k) != lourd) {
                resListe.add(liste.get(k));
            }
        }
        return resListe;
    }

    // Cherche un cycle dans la liste du plus petit arbre recouvrant en cours de construction (partie récursive)
    private boolean chercheCycleRec(Liste<int[]> liste, int v, boolean[] visited, int parent) throws Exception {
        visited[v] = true;
        for (int i = 0; i < NmbSommets; i++) {
            if (MatriceAdjacence[v][i] != 0) {
                if (!visited[i]) {
                    if (chercheCycleRec(liste, i, visited, v)) {
                        return true;
                    }
                } else if (i != parent) {
                    return true;
                }
            }
        }
        return false;
    }

    // Cherche un cycle dans la liste du plus petit arbre recouvrant en cours de construction
    private Liste<int[]> chercheCycle(Liste<int[]> liste, int i, int j) throws Exception {
        boolean[] visited = new boolean[NmbSommets];
        if (chercheCycleRec(liste, i, visited, -1)) {
            int[] lourd = getAreteLourde(liste);
            return removeAreteLourde(liste, lourd);
        }
        return liste;
    }

    // Algorithme de Kruskal, voir le rapport pour plus d'informations.
    public Liste<int[]> kruskal() throws Exception {
        // Liste qui représente le plus petit arbre recouvrant. Chaque élément est un tableau de deux entiers représentant les sommets de l'arête.
        // Le poids de l'arête est stocké dans la matrice d'adjacence.
        Liste<int[]> PPAR = new Liste<int[]>();
        // Le graphe est non orienté, on ne prend que les arêtes de la moitié supérieure de la matrice d'adjacence.
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = i + 1; j < NmbSommets; j++) {
                if (MatriceAdjacence[i][j] != 0) {
                    PPAR.add(new int[]{i, j});
                    PPAR = chercheCycle(PPAR, i, j);
                }
            }
        }
        return PPAR;
    }

    // Ajoute une arête entre deux sommets avec un poids donné
    public void add(int index1, int index2, int value) throws Exception {
        System.out.println("Ajout de l'arête (" + (index1+1) + " -> " + (index2+1) + ") de poids " + value);
        if (index1 < 0 || index2 < 0 || index1 >= NmbSommets || index2 >= NmbSommets) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index1 + ", " + index2);
        }
        MatriceAdjacence[index1][index2] = value;
        MatriceAdjacence[index2][index1] = value;
    }
}
