package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ProgramFileReader {
    private int columns;
    private int rows;
    private int maxColorValue;


    private StringBuilder content = new StringBuilder();

    private  String result ;

    public BufferedImage readFile(String path) {

        try(BufferedReader reader = new BufferedReader(new FileReader("src/com/company/"+path+".ppm"))) {

            String filetype=reader.readLine();
            if(Objects.equals(filetype, "P3")) {
                return getBufferedImageP3(reader);
            }
            if(Objects.equals(filetype, "P6"))
            {

                BufferedInputStream binaryReader = new BufferedInputStream(new FileInputStream(new File("src/com/company/"+path+".ppm")));
                int ch;
                int enterSigns =0;
                String helper="";
                while ((ch = binaryReader.read()) != -1) {
                    if(enterSigns<3)
                    {
                        if (ch==10) enterSigns++;
                       if(enterSigns>0) helper+=(char)ch;
                    }
                    else break;

                }
                Color color;
                Scanner scanner = new Scanner(helper);
                columns=scanner.nextInt();
                rows=scanner.nextInt();
                maxColorValue=scanner.nextInt();
                byte[] bytes = binaryReader.readAllBytes();
                binaryReader.close();
                BufferedImage img = new BufferedImage(columns,rows,BufferedImage.TYPE_INT_RGB);
                for(int row =0;row<rows;row++)
                    for (int column=0;column<columns;column++)
                    {

                        {
                            int index=row*columns+column ;
                            int red =bytes[index] & 0xFF;
                            int green = bytes[index+1]  & 0xFF;
                            int blue =bytes[index+2]   & 0xFF;
                            int rgb = (red << 16) | (green << 8) | blue;

                            img.setRGB(column, row,rgb );
                        }
                    }







                return img ;
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

    private BufferedImage getBufferedImageP6( byte[] bytes) {

        BufferedImage img = new BufferedImage(columns,rows,BufferedImage.TYPE_INT_RGB);
        for(int row =0;row<rows;row++)
            for (int column=0;column<columns;column++)
            {

                {
                    int index= row*columns+column;
                    int red =bytes[index] & 0xFF;
                    int green = bytes[index+1]  & 0xFF;
                    int blue =bytes[index+2]   & 0xFF;
                    int rgb = (red << 16) | (green << 8) | blue;
                    img.setRGB(column, row, rgb);
                }
            }
        return img;
    }

    private BufferedImage getBufferedImageP3(BufferedReader reader) {
        result = reader.lines().collect(Collectors.joining(System.lineSeparator())).replaceAll("#[^\r]*", "");
        Scanner scanner = new Scanner(result);
        columns=scanner.nextInt();
        rows=scanner.nextInt();
        maxColorValue=scanner.nextInt();
        BufferedImage img = new BufferedImage(columns,rows,BufferedImage.TYPE_INT_RGB);
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
    private int getIntFromColor(int Red, int Green, int Blue){
        int R = Math.round(255 * Red);
        int G = Math.round(255 * Green);
        int B = Math.round(255 * Blue);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }
}
