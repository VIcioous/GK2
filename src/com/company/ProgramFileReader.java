package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ProgramFileReader {
    private int columns;
    private int rows;
    private int maxColorValue;
    private int[][] table;
;

    public void readFile(String path) {

        try {
            Scanner inputFile = new Scanner(new FileReader("src/com/company/"+path+".ppm"));
            String filetype=inputFile.nextLine();
            columns = inputFile.nextInt();
            rows = inputFile.nextInt();
            maxColorValue = inputFile.nextInt();


            for (int row=0; row<rows; row++) {
                for (int column=0; column<columns; column++) {

                }
            }

            inputFile.close();
        }
        catch(FileNotFoundException fe) {
            System.out.println("Nie znaleziono pliku");
        }
        catch (Exception e) {
            System.out.println(e.toString() + "coś się popsuło");
            e.printStackTrace();
        }
    }
}
