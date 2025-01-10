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
        System.out.println("POIDS DE L'ARBRE : " + getPoids(PPAR));
        for (int i = 0; i < PPAR.lenght(); i++) {
            System.out.println("(" + (PPAR.get(i)[0]+1) + " -> " + (PPAR.get(i)[1]+1) + " : " + MatriceAdjacence[PPAR.get(i)[0]][PPAR.get(i)[1]] + ")");
        }
    }

    // Calcule le poids de l'arbre recouvrant à partir de la liste des aretes dans PPAR
    private Object getPoids(Liste<int[]> ppar) throws Exception {
        int weight = 0;
        for (int i = 0; i < ppar.lenght(); i++) {
            weight += MatriceAdjacence[ppar.get(i)[0]][ppar.get(i)[1]];
        }
        return weight;
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
        int[] lourd = liste.get(0);
        for (int i = 1; i < liste.lenght(); i++) {
            int[] arete = liste.get(i);
            if (MatriceAdjacence[arete[0]][arete[1]] > MatriceAdjacence[lourd[0]][lourd[1]]) {
                lourd[0] = arete[0];
                lourd[1] = arete[1];
            }
        }
        return lourd;
    }

    // Retourne la liste sans l'arête lourde
    private Liste<int[]> removeAreteLourde(Liste<int[]> liste, int[] lourd) throws Exception {
        Liste<int[]> resListe = new Liste<>();
        for (int k = 0; k < liste.lenght(); k++) {
            int[] arete = liste.get(k);
            if (arete[0] != lourd[0] || arete[1] != lourd[1]) {
                resListe.add(arete);
            }
        }
        return resListe;
    }

    // Cherche un cycle dans la liste du plus petit arbre recouvrant en cours de construction (partie récursive)
    private Liste<int[]> chercheCycleRec(Liste<int[]> Liste, int sommet, boolean[] visited, int[] sommetsCibles, Liste<int[]> cycleListe) throws Exception {
        visited[sommet] = true;
        if (visited[sommetsCibles[1]]) {
            cycleListe.add(Liste.get(Liste.lenght() - 1));
            return cycleListe;
        }
        for (int i = 0; i < Liste.lenght() - 1; i++) {
            int[] arete = Liste.get(i);
            if (arete[0] == sommet && !visited[arete[1]]) {
                cycleListe.add(arete);
                cycleListe = chercheCycleRec(Liste, arete[1], visited, sommetsCibles, cycleListe);
                if (cycleListe.lenght() != 0) {
                    int[] lastArete = cycleListe.get(cycleListe.lenght() - 1);
                    if (lastArete[0] == sommetsCibles[0] && lastArete[1] == sommetsCibles[1]) {
                        return cycleListe;
                    }
                }
            } else if (arete[1] == sommet && !visited[arete[0]]) {
                cycleListe.add(arete);
                cycleListe = chercheCycleRec(Liste, arete[0], visited, sommetsCibles, cycleListe);
                if (cycleListe.lenght() != 0) {
                    int[] lastArete = cycleListe.get(cycleListe.lenght() - 1);
                    if (lastArete[0] == sommetsCibles[0] && lastArete[1] == sommetsCibles[1]) {
                        return cycleListe;
                    }
                }
            }
        }
        return new Liste<>();
    }

    // Cherche un cycle dans la liste du plus petit arbre recouvrant en cours de construction
    private Liste<int[]> chercheCycle(Liste<int[]> PPAR, int[] arete) throws Exception {
        boolean[] visited = new boolean[NmbSommets];
        for (int k = 0; k < NmbSommets; k++) {
            visited[k] = false;
        }
        Liste<int[]> cycleListe = new Liste<>();
        cycleListe = chercheCycleRec(PPAR, arete[0], visited, arete, cycleListe);

        if (cycleListe.lenght() != 0) {
            int[] lourd = getAreteLourde(cycleListe);
            return removeAreteLourde(PPAR, lourd);
        }
        return PPAR;
    }

    // Algorithme de Kruskal, voir le rapport pour plus d'informations.
    public Liste<int[]> kruskal() throws Exception {
        // Liste qui représente le plus petit arbre recouvrant. Chaque élément est un tableau de deux entiers représentant les sommets de l'arête.
        // Le poids de l'arête est stocké dans la matrice d'adjacence.
        Liste<int[]> PPAR = new Liste<>();
        // Le graphe est non orienté, on ne prend que les arêtes de la moitié supérieure de la matrice d'adjacence.
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = i + 1; j < NmbSommets; j++) {
                if (MatriceAdjacence[i][j] != 0) {
                    int[] arete = new int[]{i, j};
                    PPAR.add(arete);
                    PPAR = chercheCycle(PPAR, arete);
                }
            }
        }
        return PPAR;
    }

    // Ajoute une arête entre deux sommets avec un poids donné
    public void add(int index1, int index2, int value) throws Exception {
        if (index1 < 0 || index2 < 0 || index1 >= NmbSommets || index2 >= NmbSommets) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index1 + ", " + index2);
        }
        MatriceAdjacence[index1][index2] = value;
        MatriceAdjacence[index2][index1] = value;
    }

    public String printResult(Liste<int[]> ppar, long elapsedTime) throws Exception {
        StringBuilder out = new StringBuilder();
        if (IsConnexe(ppar)) {
            out.append("LE GRAPHE EST CONNEXE.\n");
        } else {
            out.append("LE GRAPHE N'EST PAS CONNEXE.\n");
        }
        out.append("POIDS DE L'ARBRE : ").append(getPoids(ppar)).append("\n");
        for (int i = 0; i < ppar.lenght(); i++) {
            out.append("(")
                    .append(ppar.get(i)[0] + 1)
                    .append(" -> ")
                    .append(ppar.get(i)[1] + 1)
                    .append(" : ")
                    .append(MatriceAdjacence[ppar.get(i)[0]][ppar.get(i)[1]])
                    .append(")\n");
        }
        out.append("TEMPS D'EXÉCUTION : ").append(elapsedTime).append(" MS\n");
        return out.toString();
    }
}
