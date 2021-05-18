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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;

public class HtmlCreator
{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private boolean saved;

    public HtmlCreator(){
        saved = false;
    }
    public void snapToHTML(ArrayList<Image> images, File outputFile, File dir, String title, String credits, BufferedImage endPanel)
    {
        LOGGER.log(Level.FINEST, "GENERATING HTML FILE LOG");
        BufferedImage bi;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outputFile, false));
            bw.write(formatHTML());
            bw.write("<h1>" + title + "</h1>\n");
            bw.write("<div id=\"comic\"> \n \t");

            int width = 610;
            int height = 600;
            for(int i=0; i<images.size(); i++)
            {
                File currentPanelSnapshot = new File(dir + "/panel" + i + ".png"); //Add directory in front of file name here.
                bw.write("<div class=\"images\"><img src=\"" + currentPanelSnapshot +"\" alt='Panel'" + i + " style='width:100%'></div> \n");
                bw.flush();
                Image image = images.get(i);
                bi = SwingFXUtils.fromFXImage(image, null);
                width = (int)image.getWidth();
                height = (int)image.getHeight();
                //scale up image to get a higher quality panel
                java.awt.Image scaledImg = bi.getScaledInstance(width * 3, height * 3, java.awt.Image.SCALE_SMOOTH);
                ImageIO.write(convertToBufferedImage(scaledImg), "png", currentPanelSnapshot);
            }

            if(endPanel != null){
                File closingPanelFile = new File(dir + "/closingPanel.png");
                bw.write("<div class=\"images\"><img src=\"" + closingPanelFile +"\" alt='ClosingPanel' style='width:100%; display:block;'></div> \n");
                bw.flush();
                java.awt.Image scaledClosingPanel = endPanel.getScaledInstance(width * 3, height * 3, java.awt.Image.SCALE_SMOOTH);
                ImageIO.write(convertToBufferedImage(endPanel), "png", closingPanelFile);
            }

            bw.write("</div>\n" + "<h1>" + credits + "</h1>\n" +
                    "    </body>\n" +
                    "</html>");
            bw.close();
            saved = true;
        }
        catch (IOException e)
        {
            LOGGER.log(Level.WARNING, "An error was encountered while saving the panels.");
        }
        LOGGER.info("HTML file and panels saved successfully");

    }

    public static BufferedImage convertToBufferedImage(java.awt.Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage;
    }

    public boolean isSaved(){
        return saved;
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
                "                min-height: 100%;\n" +
                "            }\n" +
                "\n" +
                "            body{\n" +
                "                width: 100%;\n" +
                "                height: 100%;\n" +
                "                display: flex;                   \n" +
                "                flex-flow: column;\n" +
                "                align-items: center;\n" +
                "                justify-content: center;\n" +
                "                background-image: linear-gradient(rgb(33, 142, 156),rgb(187, 192, 163),rgb(192, 186, 98),rgb(187, 192, 163),rgb(4, 66, 82));\n" +
                "            }\n" +
                "\n" +
                "            #comic{\n" +
                "                width: 90%;\n" +
                "                max-width: 80%;  \n" +
                "                display: flex;\n" +
                "                justify-content: center;  \n" +
                "                flex-wrap: wrap;   \n" +
                "                border: 2px rgba(94, 94, 94, 0.514) double;\n" +
                "                background-color: rgba(0, 23, 32, 0.1);\n" +
                "                border-radius: 3px;\n" +
                "                margin-bottom: 10px;\n" +
                "                transition: linear 0.2s;\n" +
                "            }\n" +
                "\n" +
                "            #comic:hover{\n" +
                "                background-color: rgba(0, 23, 32, 0.2);\n" +
                "            }\n" +
                "\n" +
                "            .images{\n" +
                "                flex: 0 0 auto;\n" +
                "                background-color: rgb(255, 255, 255);\n" +
                "                margin: 10px 20px 10px 20px;\n" +
                "                min-width: 400px;\n" +
                "                min-height: 400px;\n" +
                "                width: 45%;\n" +
                "                overflow: hidden;\n" +
                "                border-radius: 5px;\n" +
                "                transition: linear 0.1s;\n" +
                "            }\n" +
                "\n" +
                "            @media (max-width: 600px) {\n" +
                "                #comic{\n" +
                "                    max-width: 95%;\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            @media (min-width: 600px) and (max-width: 900px) {\n" +
                "                .images{\n" +
                "                    width: 95%;\n" +
                "                }\n" +
                "\n" +
                "                #comic{\n" +
                "                    max-width: 90%;\n" +
                "                }\n" +
                "            }\n" +
                "        \n" +
                "            @media (min-width: 900px) and (max-width: 1100px) {\n" +
                "                .images{\n" +
                "                    width: 80%;\n" +
                "                }\n" +
                "\n" +
                "                #comic{\n" +
                "                    max-width: 90%;\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            .images:hover{\n" +
                "                transform: scale(1.02);\n" +
                "            }\n" +
                "\n" +
                "            h1{\n" +
                "                color: blanchedalmond;\n" +
                "                padding: 20px;\n" +
                "            }\n" +
                "\n" +
                "            ::-webkit-scrollbar {\n" +
                "                width: 10px;\n" +
                "            }\n" +
                "\n" +
                "            ::-webkit-scrollbar-track {\n" +
                "                background: #cacaca;\n" +
                "            }\n" +
                "\n" +
                "            ::-webkit-scrollbar-thumb {\n" +
                "                background:#023246;\n" +
                "            }\n" +
                "            ::-webkit-scrollbar-thumb:hover {\n" +
                "                background:#111;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>";
        return formatter;
    }
}