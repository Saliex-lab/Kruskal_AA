import Conteneurs.Listes.Liste;
import Graphes.GrapheListe.GrapheListe;
import Utils.FileManager;

public class KruskalL {
    public static void main(String[] args) throws Exception {
        Liste<Integer>_graph = new Liste<>();
        GrapheListe graph = null;
        switch (args.length) {
            case 1:
                FileManager.GetDataFile(_graph, args[0]);
                graph = new GrapheListe(_graph);
                graph.KruskalAlgo();
                System.out.println(FileManager.sb.toString());
                break;

            case 2:
                FileManager.GetDataFile(_graph, args[0]);
                graph = new GrapheListe(_graph);
                graph.KruskalAlgo();
                FileManager.SetDataFile(args[1]);
                break;

            default:
                throw new IllegalArgumentException("Le programme nécessite 1 ou 2 arguments.");
        }
    }
}
