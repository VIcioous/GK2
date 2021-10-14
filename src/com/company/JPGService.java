package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPGService {

    public BufferedImage readJPG(String path)
    {
        try {
            BufferedImage bImage = ImageIO.read(new File("src/com/company/"+path+".jpg"));
            return bImage;
        }catch (IOException e)
        {
            System.out.println("nie załadowało pliku");
        }
        return null;
    }

    public void writeJPG(String path, BufferedImage imageJPG) {
        try
        {
            ImageIO.write(imageJPG,"jpeg",new File("src/com/company/"+path+".jpeg"));
        }
        catch (IOException e)
        {
            System.out.println("Nie udało się zapisać plikuj");
        }
    }
}
