package com.company;

import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ProgramFileReader {
    private int columns;
    private int rows;
    private int maxColorValue;


    private StringBuilder content = new StringBuilder();

    private  String result ;
    private Scanner scanner;

    public BufferedImage readFile(String path) {

        try(BufferedReader reader = new BufferedReader(new FileReader("src/com/company/"+path+".ppm"))) {

            String filetype=reader.readLine();
            if(Objects.equals(filetype, "P3")) {
                return getBufferedImageP3(reader);
            }
            if(Objects.equals(filetype, "P6"))
            {

                BufferedInputStream binaryReader = new BufferedInputStream(new FileInputStream(new File("src/com/company/"+path+".ppm")),65536);
                int ch;
                while ((ch = binaryReader.read()) != -1) {
                    System.out.println((char)ch);
                }

                binaryReader.close();


                return getBufferedImageP6(reader);
            }

        }
        catch(FileNotFoundException fe) {
            System.out.println("Nie znaleziono pliku");
        }
        catch (Exception e) {
            System.out.println(e.toString() + "coś się popsuło");
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage getBufferedImageP6(BufferedReader reader) {

        try {


        }

        catch (Exception e)
        {
            System.out.println("muda");
        }
        return null;
    }

    private BufferedImage getBufferedImageP3(BufferedReader reader) {
        result = reader.lines().collect(Collectors.joining(System.lineSeparator())).replaceAll("#[^\r]*", "");
        Scanner scanner = new Scanner(result);
        columns=scanner.nextInt();
        rows=scanner.nextInt();
        maxColorValue=scanner.nextInt();
        BufferedImage img = new BufferedImage(columns,rows,BufferedImage.TYPE_INT_BGR);
        if(maxColorValue==65535)
        for(int row =0;row<rows;row++)
            for (int column=0;column<columns;column++)
            {

                {
                    int red = convert(scanner.nextInt()) & 0xFF;
                    int green = convert(scanner.nextInt()) & 0xFF;
                    int blue = convert(scanner.nextInt()) & 0xFF;
                    int rgb = (red << 16) | (green << 8) | blue;
                    img.setRGB(column, row, rgb);
                }
            }
        else
        {
            for(int row =0;row<rows;row++)
                for (int column=0;column<columns;column++)
                {

                    {
                        int red = scanner.nextInt()& 0xFF;
                        int green = scanner.nextInt() & 0xFF;
                        int blue = scanner.nextInt() & 0xFF;
                        int rgb = (red << 16) | (green << 8) | blue;
                        img.setRGB(column, row, rgb);
                    }
                }
        }
        return img;
    }

    private int convert(int value)
    {
        float ratio = value/256;

        return (int) ratio;
    }
}
