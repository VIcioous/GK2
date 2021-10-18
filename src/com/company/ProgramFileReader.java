package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ProgramFileReader {
    private int columns;
    private int rows;
    private int maxColorValue;

    public BufferedImage readFile(String path, int width, int height) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/com/company/" + path + ".ppm"), 4194304)) {

            String filetype = reader.readLine();
            if (Objects.equals(filetype, "P3")) {
                return getBufferedImageP3(reader, width, height);
            }
            if (Objects.equals(filetype, "P6")) {

                return getBufferedImageP6(path, width, height);
            }

        } catch (FileNotFoundException fe) {
            System.out.println("Nie znaleziono pliku");
        } catch (Exception e) {
            System.out.println(e.toString() + "coś się popsuło");
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage getBufferedImageP6(String path, int width, int height)  {
        try {
            BufferedInputStream binaryReader = new BufferedInputStream(new FileInputStream("src/com/company/" + path + ".ppm"));
            int ch;
            int enterSigns = 0;
            String helper = "";
            while ((ch = binaryReader.read()) != -1) {
                if (enterSigns < 3) {
                    if (ch == 10) enterSigns++;
                    if (enterSigns > 0) helper += (char) ch;
                } else break;

            }
            Scanner scanner = new Scanner(helper);
            columns = scanner.nextInt();
            rows = scanner.nextInt() - 1;
            maxColorValue = scanner.nextInt();
            byte[] bytes = binaryReader.readAllBytes();
            binaryReader.close();
            BufferedImage img = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
            for (int row = 0; row < rows; row++)
                for (int column = 0; column < columns; column++) {
                    {
                        int index = row * columns + column;
                        int red = bytes[3 * index] & 0xFF;
                        int green = bytes[3 * index + 1] & 0xFF;
                        int blue = bytes[3 * index + 2] & 0xFF;
                        int rgb = (red << 16) | (green << 8) | blue;
                        img.setRGB(column, row, rgb);
                    }
                }
            return returnResizedImage(width, height, img);
        }catch (IOException ignored)
        {

        }
        return null;
    }


    private BufferedImage getBufferedImageP3(BufferedReader reader, int width, int height) {
        String result = reader.lines().collect(Collectors.joining(System.lineSeparator())).replaceAll("#[^\r]*", "");
        Scanner scanner = new Scanner(result);
        columns = scanner.nextInt();
        rows = scanner.nextInt();
        maxColorValue = scanner.nextInt();
        BufferedImage img = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
        if (maxColorValue == 65535)
            for (int row = 0; row < rows; row++)
                for (int column = 0; column < columns; column++) {

                    {
                        int red = convert(scanner.nextInt()) & 0xFF;
                        int green = convert(scanner.nextInt()) & 0xFF;
                        int blue = convert(scanner.nextInt()) & 0xFF;
                        int rgb = (red << 16) | (green << 8) | blue;
                        img.setRGB(column, row, rgb);
                    }
                }
        else {
            for (int row = 0; row < rows; row++)
                for (int column = 0; column < columns; column++) {

                    {
                        int red = scanner.nextInt() & 0xFF;
                        int green = scanner.nextInt() & 0xFF;
                        int blue = scanner.nextInt() & 0xFF;
                        int rgb = (red << 16) | (green << 8) | blue;
                        img.setRGB(column, row, rgb);
                    }
                }
        }

        return returnResizedImage(width, height, img);
    }

    private BufferedImage returnResizedImage(int width, int height, BufferedImage img) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        return dimg;
    }

    private int convert(int value) {
        float ratio = value / 256;
        return (int) ratio;
    }
}
