import FileManager.FileManager;
import Conteneurs.Liste;
import Graphes.GrapheListe.GrapheListe;

public class KruskalL {
    public static void main(String[] args) throws Exception {
        Liste<Integer>_graph = new Liste<>();
        GrapheListe graph = null;
        switch (args.length) {
            case 2:
                FileManager.GetDataFile(_graph, args[0]);
                graph = new GrapheListe(_graph);
                graph.KruskalAlgo(Integer.parseInt(args[1]));
                break;

            case 3:
                FileManager.GetDataFile(_graph, args[0]);
                graph = new GrapheListe(_graph);
                graph.KruskalAlgo(Integer.parseInt(args[1]));
                FileManager.SetDataFile(args[2]);
                break;

            default:
                throw new IllegalArgumentException("Le programme nécessite 2 ou 3 arguments.");
        }
    }
}
