package FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Conteneurs.Liste;

public class FileManager {

    public static StringBuilder sb = new StringBuilder();

    private static Scanner sc;

    /////////////// SYSTEM GET DATA///////////////////

    public static void GetDataFile(Liste<Integer> graph, String infile) {
        try {
            File test = new File("res\\in\\" + infile+".txt");
            sc = new Scanner(test);
    
            while (sc.hasNextInt()) {
                Integer line = sc.nextInt();
                graph.add(line); 
            }
            sc.close();
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
            FileWriter myWriter = new FileWriter("res\\out\\"+outfile+".txt");
            myWriter.write(FileManager.sb.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }

    }
}
