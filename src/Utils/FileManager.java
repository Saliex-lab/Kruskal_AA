package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Conteneurs.Listes.Liste;

public class FileManager {

    // Pour le cas dossier bin
    //../res/f_in/
    //../res/f_out/

    public static StringBuilder sb = new StringBuilder();

    private static Scanner sc;

    /////////////// SYSTEM GET DATA///////////////////

    public static void GetDataFile(Liste<Integer> graph, String infile) {
        try {
            File test = new File("../../res/f_in/" + infile+".txt");
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

    /////////////// SYSTEM SET DATA///////////////////

    public static void SetDataFile(String outfile) {
        try {
            FileWriter myWriter = new FileWriter("../res/f_out/"+outfile+".txt");
            myWriter.write(FileManager.sb.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }

    }
}
