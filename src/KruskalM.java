import Conteneurs.Liste;
import Graphes.GrapheMatrice.GrapheMatrice;
import Utils.FileManager;
import Utils.Timer;

public class KruskalM {

    public static void main(String[] args) throws Exception {
        // Matrice d'adjacence du graphe
        GrapheMatrice MatriceAdjacence;
        // Représentation du plus petit arbre recouvrant par le biais de la liste des aretes dans PPAR
        Liste<int[]> PPAR = null;
        System.out.println("KruskalM");

        switch (args.length) {
            case 1:
                MatriceAdjacence = FileManager.GetDataFile(args[0]);
                System.out.println("Matrice d'adjacence du graphe :");
                MatriceAdjacence.printMatrix();
                Timer.start();
                assert MatriceAdjacence != null;
                PPAR = MatriceAdjacence.kruskal();
                Timer.stop();

                break;

            case 2:

                break;

            default:
                throw new IllegalArgumentException("Le programme nécessite 1 ou 2 arguments.");
        }
    }
    // TODO : Utilisation de FileManager pour la lecture et l'écriture de fichiers.
}
