package main.html;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HtmlCreator
{
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void snapToHTML(ArrayList<Image> images, File outputFile, File dir, String title, String credits)
    {
        BufferedImage bi;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outputFile, false));
            bw.write(formatHTML());
            bw.write("<h2>" + title + "</h2>\n");
            bw.write("<div id=\"comic\"> \n \t");

            for(int i=0; i<images.size(); i++)
            {
                File currentPanelSnapshot = new File(dir + "/panel" + i + ".png"); //Add directory in front of file name here.
                System.out.println(currentPanelSnapshot);
                bw.write("<div class=\"images\"><img src=\"" + currentPanelSnapshot +"\" alt='Panel" + i + "' style='width:100%'></div> \n");
                bw.flush();
                bi = SwingFXUtils.fromFXImage(images.get(i), null);
                ImageIO.write(bi, "png", currentPanelSnapshot);
            }
            bw.write("</div>\n" + "<h2>" + credits + "</h2>\n" +
                    "    </body>\n" +
                    "</html>");
            bw.close();
        }
        catch (IOException e)
        {
            System.out.println("Error saving Images in snapToHTML method");
            logger.log(Level.WARNING, "An error was encountered while saving the panels.");
        }
        System.out.println("Files saved to HTML successfully");
        logger.info("HTML file and panels saved successfully");
    }

    private String formatHTML()
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
            "                justify-content: center;\n"+
            "                border: 2px rgba(94, 94, 94, 0.514) double;\n" +
            "                background-color: rgb(0, 23, 32);\n" +
            "            }\n" +
            "\n" +
            "            .images{\n" +
            "                flex: 0 0 auto;\n" +
            "                background-color: rgb(255, 255, 255);\n" +
            "                margin: 10px 20px 10px 20px;\n" +
            "                width: 500px;\n" +
            "                height: 500px;\n" +
            "                transition: linear 0.1s;\n" +
            "            }\n" +
            "\n" +
            "            .images:hover{\n" +
            "                transform: scale(1.04);\n" +
            "            }\n" +
            "\n" +
            "            h2{\n" +
            "                color: blanchedalmond;\n" +
            "                padding: 20px;\n" +
            "                text-align: center;\n"+
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n";

        return formatter;
    }
}