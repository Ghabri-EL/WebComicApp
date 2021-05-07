package main.html;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class htmlCreator
{
    public void snapToHTML(ArrayList<Image> images)
    {
        File outputFile = new File("C:\\testFolder\\output.html");
        BufferedImage bi;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outputFile, false));
            for(int i=0; i<images.size(); i++)
            {
                bw.write("<img src='panel" + i + ".png' alt='Panel" + i + "' style='width:100%'>");
                bw.flush();
                bi = SwingFXUtils.fromFXImage(images.get(i), null);

                File currentPanelSnapshot = new File("C:\\testFolder\\panel" + i + ".png");
                ImageIO.write(bi, "png", currentPanelSnapshot);
            }
            bw.close();
        }
        catch (IOException e)
        {
            System.out.println("Error saving Images in snapToHTML method");
        }
        System.out.println("Files saved to HTML successfully");
    }
}