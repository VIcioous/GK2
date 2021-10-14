package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canva extends JPanel {
    private final JButton readButton = new JButton();
    private final JTextField pathFile = new JTextField();

    private final JButton writeButtonJPG = new JButton();
    private final JButton readButtonJPG = new JButton();
    private final JTextField pathFileJPG = new JTextField();
    private final ProgramFileReader programFileReader = new ProgramFileReader();
    private final JPGService jpgService = new JPGService();
    private BufferedImage imageJPG;

    Canva()
    {
        setButtons();
    }

    private void setButtons() {

        readButton.addActionListener(e-> readFile(pathFile.getText()));
;
        readButton.setText("Read");
        readButton.setBounds(480,25,100,25);
        pathFile.setBounds(480,75,100,25);
        
        writeButtonJPG.addActionListener(e-> writeFileJPG(pathFileJPG.getText()));
        readButtonJPG.addActionListener(e-> readFileJPG(pathFileJPG.getText()));
        writeButtonJPG.setText("Write JPG");
        readButtonJPG.setText("Read JPG");
        readButtonJPG.setBounds(480,225,100,25);
        writeButtonJPG.setBounds(480,275,100,25);
        pathFileJPG.setBounds(480,325,100,25);

        this.add(pathFile);
        this.add(readButton);
        this.add(writeButtonJPG);
        this.add(pathFileJPG);
        this.add(readButtonJPG);
        this.setLayout(null);
    }

    private void writeFileJPG(String path) {
        jpgService.writeJPG(path,imageJPG);
    }

    private void readFileJPG(String path) {
        imageJPG = jpgService.readJPG(path);

    }

    private void readFile(String path) {
        programFileReader.readFile(path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageJPG, 0, 0, this);
        repaint();
    }



}
