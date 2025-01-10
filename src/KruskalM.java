import Conteneurs.Liste;
import Graphes.GrapheMatrice.GrapheMatrice;
import Utils.FileManager;
import Utils.Timer;

public class KruskalM {

    public static void main(String[] args) throws Exception {
        Liste<Integer>_graph = new Liste<>();
        // Matrice d'adjacence du graphe
        GrapheMatrice MatriceAdjacence;
        // Représentation du plus petit arbre recouvrant par le biais de la liste des aretes dans PPAR
        Liste<int[]> PPAR;
        System.out.println("KruskalM");

        switch (args.length) {
            case 1:
                FileManager.GetDataFile(_graph, args[0]);
                MatriceAdjacence = new GrapheMatrice(_graph);
                System.out.println("Matrice d'adjacence du graphe :");
                MatriceAdjacence.printMatrix();
                Timer.start();
                PPAR = MatriceAdjacence.kruskal();
                Timer.stop();
                MatriceAdjacence.printPPAR(PPAR);
                System.out.println("Temps d'exécution : " + Timer.getElapsedTime() + " ms");
                break;

            case 2:
                FileManager.GetDataFile(_graph, args[0]);
                MatriceAdjacence = new GrapheMatrice(_graph);
                String out;
                Timer.start();
                PPAR = MatriceAdjacence.kruskal();
                Timer.stop();
                out = MatriceAdjacence.printResult(PPAR, Timer.getElapsedTime());
                FileManager.SetDataFile(args[1], out);

                break;

            default:
                throw new IllegalArgumentException("Le programme nécessite 1 ou 2 arguments.");
        }
    }
}
