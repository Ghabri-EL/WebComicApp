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
            bw.write(formatHTML());
            for(int i=0; i<images.size(); i++)
            {
                bw.write("<div class=\"images\"><img src='panel" + i + ".png' alt='Panel" + i + "' style='width:100%'></div> \n");
                bw.flush();
                bi = SwingFXUtils.fromFXImage(images.get(i), null);

                File currentPanelSnapshot = new File("C:\\testFolder\\panel" + i + ".png");
                ImageIO.write(bi, "png", currentPanelSnapshot);
            }
            bw.write("</div>\n" +
                    "    </body>\n" +
                    "</html>");
            bw.close();
        }
        catch (IOException e)
        {
            System.out.println("Error saving Images in snapToHTML method");
        }
        System.out.println("Files saved to HTML successfully");
    }

    public String formatHTML()
    {
        String formatter = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <title>HomiesComix</title>\n" +
            "\n" +
            "        <style>\n" +
            "            *{\n" +
            "                margin: 0;\n" +
            "                padding: 0;\n" +
            "            }\n" +
            "\n" +
            "            html, body{\n" +
            "                width: 100%;\n" +
            "                height: 100%;\n" +
            "            }\n" +
            "\n" +
            "            body{\n" +
            "                width: 100%;\n" +
            "                display: flex;                   \n" +
            "                flex-flow: column;\n" +
            "                align-items: center;\n" +
            "                justify-content: center;\n" +
            "                background-color: rgb(4, 22, 31);\n" +
            "            }\n" +
            "\n" +
            "            #comic{\n" +
            "                width: 100%;\n" +
            "                overflow-x: auto;   \n" +
            "                display: flex;\n" +
            "                flex-wrap: nowrap;           \n" +
            "                border: 2px rgba(94, 94, 94, 0.514) double;\n" +
            "                background-color: rgb(0, 23, 32);\n" +
            "            }\n" +
            "\n" +
            "            .images{\n" +
            "                flex: 0 0 auto;\n" +
            "                background-color: rgb(255, 255, 255);\n" +
            "                margin: 10px 20px 10px 20px;\n" +
            "                width: 400px;\n" +
            "                height: 400px;\n" +
            "                transition: linear 0.1s;\n" +
            "            }\n" +
            "\n" +
            "            .images:hover{\n" +
            "                transform: scale(1.04);\n" +
            "            }\n" +
            "\n" +
            "            h1{\n" +
            "                color: blanchedalmond;\n" +
            "                padding: 20px;\n" +
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <h1>HomiesComix</h1>\n" +
            "        <div id=\"comic\"> \n \t";

        return formatter;
    }
}