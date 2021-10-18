package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canva extends JPanel {
    private final JLabel labelName = new JLabel("file");
    private final JLabel labelHeight = new JLabel("height");
    private final JLabel labelWidth = new JLabel("width");

    private final JButton readButton = new JButton();
    private final JButton writeButtonJPG = new JButton();
    private final JButton readButtonJPG = new JButton();

    private final JTextField pathFile = new JTextField();
    private final JTextField pathFileJPG = new JTextField();
    private final JTextField widthToLoad= new JTextField();
    private final JTextField heightToLoad = new JTextField();

    private final ProgramFileReader programFileReader = new ProgramFileReader();
    private final JPGService jpgService = new JPGService();
    private BufferedImage imageJPG;


    Canva()
    {
        setButtons();
    }



    private void writeFileJPG(String path) {
        jpgService.writeJPG(path,imageJPG);
    }

    private void readFileJPG(String path) {
        imageJPG = jpgService.readJPG(path);
        NewWindow window = new NewWindow(imageJPG);
    }

    private void readFile(String path,int width,int height) {
       imageJPG= programFileReader.readFile(path,width,height);
        NewWindow window = new NewWindow(imageJPG);
    }




    private void setButtons() {

        readButton.addActionListener(e-> readFile(pathFile.getText(),
                Integer.parseInt(widthToLoad.getText()),Integer.parseInt(heightToLoad.getText())));
        readButton.setText("Read");
        readButton.setBounds(480,25,100,25);

        readButtonJPG.setBounds(480,225,100,25);
        readButtonJPG.addActionListener(e-> readFileJPG(pathFileJPG.getText()));
        readButtonJPG.setText("Read JPG");

        writeButtonJPG.setBounds(480,275,100,25);
        writeButtonJPG.addActionListener(e-> writeFileJPG(pathFileJPG.getText()));
        writeButtonJPG.setText("Write JPG");

        labelHeight.setBounds(440,175,100,25);
        labelWidth.setBounds(440,125,100,25);
        labelName.setBounds(440,75,100,25);

        pathFile.setBounds(480,75,100,25);
        widthToLoad.setBounds(480,125,100,25);
        heightToLoad.setBounds(480,175,100,25);
        pathFileJPG.setBounds(480,325,100,25);


        this.add(labelName);
        this.add(labelWidth);
        this.add(labelHeight);
        this.add(pathFile);
        this.add(readButton);
        this.add(writeButtonJPG);
        this.add(pathFileJPG);
        this.add(readButtonJPG);
        this.add(widthToLoad);
        this.add(heightToLoad);
        this.setLayout(null);
    }
}
