import Conteneurs.Liste;
import Graphes.GrapheMatrice.GrapheMatrice;
import Utils.FileManager;

public class KruskalM {

    public static void main(String[] args) throws Exception {
        Liste<Integer>_graph = new Liste<>();
        // Matrice d'adjacence du graphe
        GrapheMatrice MatriceAdjacence;

        switch (args.length) {
            case 1:
                FileManager.GetDataFile(_graph, args[0]);
                MatriceAdjacence = new GrapheMatrice(_graph);
                MatriceAdjacence.kruskal();
                System.out.println(FileManager.sb.toString());
                break;

            case 2:
                FileManager.GetDataFile(_graph, args[0]);
                MatriceAdjacence = new GrapheMatrice(_graph);
                MatriceAdjacence.kruskal();
                FileManager.SetDataFile(args[1]);
                break;

            default:
                throw new IllegalArgumentException("Le programme nécessite 1 ou 2 arguments.");
        }
    }
}
