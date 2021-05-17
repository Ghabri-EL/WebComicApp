package main.comi_xml_handler;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.model.Character;
import main.model.DefaultColors;
import main.model.NarrativeText;
import main.model.Panel;
import main.project_enums.BubbleType;
import main.project_enums.NarrativeTextWrap;
import main.project_enums.Selected;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComiXML implements DefaultColors {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean createXML(ArrayList<Panel> list, File comiXMLFile, String title, String credits){
        if(list.isEmpty()){
            return false;
        }

        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element comic = document.createElement("comic");
            document.appendChild(comic);

            Element comicTitle = document.createElement("title");
            comic.appendChild(comicTitle);
            comicTitle.appendChild(document.createTextNode(title));

            Element panels = document.createElement("panels");
            comic.appendChild(panels);

            Element comicCredits = document.createElement("credits");
            comic.appendChild(comicCredits);
            comicCredits.appendChild(document.createTextNode(credits));

            for(int i = 0; i < list.size(); i++){
                Panel currentPanel = list.get(i);

                Element panel = document.createElement("panel");
                panel.setAttribute("id", "" + currentPanel.getId());

                NarrativeText narrativeTextTop = currentPanel.getNarrativeTextTop();
                String aboveText = narrativeTextTop.getNarrativeText();
                String aboveWrapType = narrativeTextTop.getNarrativeTextWrap().toString().toLowerCase();
                Element top = document.createElement("above");
                Element aboveContent = document.createElement("content");
                Element aboveWrap = document.createElement("wrap");
                aboveContent.appendChild(document.createTextNode(aboveText));
                aboveWrap.appendChild(document.createTextNode(aboveWrapType));
                top.appendChild(aboveContent);
                top.appendChild(aboveWrap);
                panel.appendChild(top);

                //left
                Element left = document.createElement("left");
                panel.appendChild(left);
                addFigureElements(document, left, Selected.LEFT, currentPanel);

                //right
                Element right = document.createElement("right");
                panel.appendChild(right);
                addFigureElements(document, right, Selected.RIGHT, currentPanel);

                NarrativeText narrativeTextBottom = currentPanel.getNarrativeTextBottom();
                String belowText = currentPanel.getNarrativeTextBottom().getNarrativeText();
                String belowWrapType = narrativeTextBottom.getNarrativeTextWrap().toString().toLowerCase();
                Element bottom = document.createElement("below");
                Element belowContent = document.createElement("content");
                Element belowWrap = document.createElement("wrap");
                belowContent.appendChild(document.createTextNode(belowText));
                belowWrap.appendChild(document.createTextNode(belowWrapType));
                bottom.appendChild(belowContent);
                bottom.appendChild(belowWrap);
                panel.appendChild(bottom);

                panels.appendChild(panel);
            }

            if(comiXMLFile.exists()){
                comiXMLFile.createNewFile();
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(comiXMLFile);

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

            return true;
        }
        catch(ParserConfigurationException exception){
            exception.printStackTrace();
        }
        catch(TransformerConfigurationException exception){
            exception.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void addFigureElements(Document document, Element side, Selected frame, Panel panel){
        Character character;
        String bubbleType;
        String bubbleText;
        if(frame == Selected.LEFT ){
            character = panel.getCharacterLeft();
            bubbleType = panel.getLeftBubbleType().toString().toLowerCase();
            bubbleText = panel.getLeftBubbleText();
        }
        else{
            character = panel.getCharacterRight();
            bubbleType = panel.getRightBubbleType().toString().toLowerCase();
            bubbleText = panel.getRightBubbleText();
        }

        Element leftChar = document.createElement("figure");
        side.appendChild(leftChar);

        //create figure elements
        Element appearance = document.createElement("appearance");
        String gender = character.getGender().toString().toLowerCase();
        appearance.appendChild(document.createTextNode(gender));
        leftChar.appendChild(appearance);

        Element skin = document.createElement("skin");
        String sColor = character.getSkin().toString();
        skin.appendChild(document.createTextNode(sColor));
        leftChar.appendChild(skin);

        Element hair = document.createElement("hair");
        String hColor = character.getHair().toString();
        hair.appendChild(document.createTextNode(hColor));
        leftChar.appendChild(hair);

        Element lips = document.createElement("lips");
        String lColor = character.getLips().toString();
        lips.appendChild(document.createTextNode(lColor));
        leftChar.appendChild(lips);

        Element pose = document.createElement("pose");
        pose.appendChild(document.createTextNode(character.getPose()));
        leftChar.appendChild(pose);

        Element orientation = document.createElement("facing");
        String charOrientation = character.getOrientation().toString().toLowerCase();
        orientation.appendChild(document.createTextNode(charOrientation));
        leftChar.appendChild(orientation);

        Element balloon = document.createElement("balloon");
        balloon.setAttribute("status", bubbleType);
        side.appendChild(balloon);

        Element bubbleContent = document.createElement("content");
        bubbleContent.appendChild(document.createTextNode(bubbleText));
        balloon.appendChild(bubbleContent);
    }
}
