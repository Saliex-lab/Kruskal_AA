import Conteneurs.Liste;

public class KruskalM {
    // Nombre de sommets du graphe
    private int NmbSommets;
    // Matrice d'adjacence du graphe. Utilisation d'une liste de listes pour utiliser au maximum la classe Liste.
    private Liste<Liste<Integer>> MatriceAdjacence;

    // Constructeur avec une matrice d'adjacence vide
    public KruskalM(int NombreSommets) throws Exception {
        this.NmbSommets = NombreSommets;
        MatriceAdjacence = new Liste<Liste<Integer>>();
        for (int i = 0; i < NombreSommets; i++) {
            MatriceAdjacence.add(new Liste<Integer>());
            for (int j = 0; j < NombreSommets; j++) {
                MatriceAdjacence.get(i).add(0);
            }
        }
    }

    // Affiche la matrice d'adjacence
    public void printMatrix() throws Exception {
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = 0; j < NmbSommets; j++) {
                System.out.print(MatriceAdjacence.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    // Affiche le plus petit arbre recouvrant par le biais de la liste des aretes dans PPAR.
    public void printPPAR(Liste<int[]> liste) throws Exception {
        for (int i = 0; i < liste.lenght(); i++) {
            System.out.println("[Arete " + i + ": " + liste.get(i)[0] + " - " + liste.get(i)[1] + " ; Poid : " + MatriceAdjacence.get(liste.get(i)[0]).get(liste.get(i)[1]) + "]");
        }
    }

    // Donne l'arete la plus lourde de la liste
    private int[] getAreteLourde(Liste<int[]> liste) throws Exception {
        int lourd = 0;
        for (int i = 0; i < liste.lenght(); i++) {
            if (MatriceAdjacence.get(i).get(i) > MatriceAdjacence.get(lourd).get(lourd)) {
                lourd = i;
            }
        }
        return liste.get(lourd);
    }

    // Retourne la liste sans l'arete lourde
    private Liste<int[]> removeAreteLourde(Liste<int[]> liste, int[] lourd) throws Exception {
        Liste<int[]> resListe = new Liste<int[]>();
        for (int k = 0; k < liste.lenght(); k++) {
            if (liste.get(k) == lourd) {
                resListe.add(lourd);
            }
        }
        return resListe;
    }

    // Cherche un cycle dans la liste du plus petit arbre recouvrant en cours de construction (partie récursive)
    private boolean chercheCycleRec(Liste<int[]> liste, int v, boolean[] visited, int parent) throws Exception {
        visited[v] = true;
        for (int i = 0; i < NmbSommets; i++) {
            if (MatriceAdjacence.get(v).get(i) != 0) {
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

    public Liste<int[]> kruskal() throws Exception {
        System.out.println("Application de l'algorithme de Kruskal sur la matrice d'adjacence suivante.");
        long startTime = System.nanoTime();
        //Liste qui représente le plus petit arbre recouvrant
        Liste<int[]> PPAR = new Liste<int[]>();
        for (int i = 0; i < NmbSommets; i++) {
            for (int j = i + 1; j < NmbSommets; j++) {
                if (MatriceAdjacence.get(i).get(j) != 0) {
                    PPAR.add(new int[]{i, j});
                    PPAR = chercheCycle(PPAR, i, j);
                }
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds
        System.out.println("Temps d'exécution de l'algorithme de Kruskal: " + duration + " ms");

        return PPAR;
    }

    public static void main(String[] args) {
        System.out.println("KruskalM");
    }
}
