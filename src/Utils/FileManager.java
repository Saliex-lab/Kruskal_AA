package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Conteneurs.Liste;
import Graphes.GrapheMatrice.GrapheMatrice;

public class FileManager {

    public static StringBuilder sb = new StringBuilder();

    private static Scanner sc;

    /////////////// SYSTEM GET DATA///////////////////

    public static void GetDataFile(Liste<Integer> graph, String infile) {
        try {
            File test = new File("res\\f_in\\" + infile+".txt");
            sc = new Scanner(test);
    
            while (sc.hasNextInt()) {
                Integer line = sc.nextInt();
                graph.add(line); 
            }
            sc.close();
            System.out.println("Successfully read to the file.");
        } catch (FileNotFoundException e) {
            System.out.println("File " + infile + " not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static GrapheMatrice GetDataFile(String infile) {
        try {
            File test = new File("../res/f_in/" + infile + ".txt");
            sc = new Scanner(test);
            GrapheMatrice graphe = null;
            if (sc.hasNextInt()) {
                int line = sc.nextInt();
                graphe = new GrapheMatrice(line);
            }
            int index = sc.nextInt();
            while (sc.hasNextInt()) {
                int sommet = sc.nextInt();
                int poids = 0;
                if (sommet != 0 && sc.hasNextInt()) {
                    poids = sc.nextInt();
                }
                assert graphe != null;
                if (sommet != 0) {
                    assert poids != 0;
                    graphe.add(index - 1, sommet - 1, poids);
                } else if (sc.hasNextInt()){
                    index = sc.nextInt();
                }
            }
            sc.close();
            System.out.println("Successfully read to the file.");
            return graphe;
        } catch (FileNotFoundException e) {
            System.out.println("File " + infile + " not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    /////////////// SYSTEM SET DATA///////////////////

    public static void SetDataFile(String outfile) {
        try {
            FileWriter myWriter = new FileWriter("res\\f_out\\"+outfile+".txt");
            myWriter.write(FileManager.sb.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }

    }
}
