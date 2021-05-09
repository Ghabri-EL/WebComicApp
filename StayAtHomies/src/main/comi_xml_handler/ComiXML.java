package main.comi_xml_handler;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.model.Character;
import main.model.DefaultColors;
import main.model.Panel;
import main.project_enums.BubbleType;
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
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean createXML(ArrayList<Panel> list, File comiXMLFile){
        if(list.isEmpty()){
            return false;
        }

        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element comic = document.createElement("comic");
            document.appendChild(comic);
            Element panels = document.createElement("panels");
            comic.appendChild(panels);

            for(int i = 0; i < list.size(); i++){
                Panel currentPanel = list.get(i);

                Element panel = document.createElement("panel");
                panel.setAttribute("id", "" + currentPanel.getId());

                Element top = document.createElement("above");
                top.appendChild(document.createTextNode(currentPanel.getNarrativeTextTop()));
                panel.appendChild(top);

                //left
                Element left = document.createElement("left");
                panel.appendChild(left);
                addFigureElements(document, left, Selected.LEFT, currentPanel);

                //right
                Element right = document.createElement("right");
                panel.appendChild(right);
                addFigureElements(document, right, Selected.RIGHT, currentPanel);

                Element bottom = document.createElement("below");
                bottom.appendChild(document.createTextNode(currentPanel.getNarrativeTextBottom()));
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

    public static ArrayList<Panel> createComicStripFromComiXML(File xmlFile, File charactersDir){
        if(xmlFile == null){
            return null;
        }

        ArrayList<Panel> panels = new ArrayList<>();
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            if(document == null){
                logger.log(Level.WARNING, "XML file format could not be parsed properly. Incorrect XML file format.");
                return null;
            }
            document.normalizeDocument();
            NodeList listOfPanels = document.getElementsByTagName("panel");

            int id;
            for(int i = 0; i < listOfPanels.getLength(); i++){
                Node node = listOfPanels.item(i);
                Panel panel = new Panel();

                Element panelElement = (Element) node;
                if(panelElement.hasAttribute("id")){
                    try{
                        id = Integer.parseInt(panelElement.getAttribute("id"));
                        panel.setId(id);
                    }catch(NumberFormatException ex){
                        //ex.printStackTrace();
                        logger.info("Panel does not contain an id attribute. Id generated: " + i);
                        id = i;
                    }
                }else{
                    id = i;
                }
                panel.setId(id);
                //parse the current panel
                parsePanel(node, panel, charactersDir);

                if(panel.getCharacterLeft() != null && panel.getCharacterRight() != null){
                    panels.add(panel);
                    //log panel created successfully
                    logger.info("Panel [" +  panel.getId() + "] loaded successfully");
                }else{
                    //to add error to log
                    logger.log(Level.WARNING, "Panel [" + panel.getId() +"]could not be parsed");
                }
            }
            if(panels.isEmpty()){
                logger.info("No panels were found in the file provided.");
            }
            return panels;
        }
        catch(ParserConfigurationException exception){
            exception.printStackTrace();
            logger.log(Level.WARNING, "Parser configuration error");
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "XML file could not be loaded properly.");
        } catch (SAXException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "XML file format could not be parsed properly. Incorrect XML file format.");
        }
        return null;
    }

    private static void parsePanel(Node panelNode, Panel panel, File charactersDir){
        NodeList panelElements = panelNode.getChildNodes();

        for(int i = 0; i < panelElements.getLength(); i++){
            Node node = panelElements.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                if(node.getNodeName().equalsIgnoreCase("above")){
                    String aboveText = node.getTextContent();
                    panel.setNarrativeTextTop(aboveText);
                }
                else if(node.getNodeName().equalsIgnoreCase("left")){
                    parseLeftAndRightElement(node, panel, Selected.LEFT, charactersDir);
                }
                else if(node.getNodeName().equalsIgnoreCase("right")){
                    parseLeftAndRightElement(node, panel, Selected.RIGHT, charactersDir);
                }else if(node.getNodeName().equalsIgnoreCase("below")){
                    String belowText = node.getTextContent();
                    panel.setNarrativeTextBottom(belowText);
                }
                else{
                    logger.info( "Node [" + node.getNodeName() + "] cannot be parsed");
                }
            }
        }
    }

    //partition refers to the left or right side of the panel
    private static void parseLeftAndRightElement(Node partition, Panel panel, Selected selected, File charactersDir){
        NodeList list = partition.getChildNodes();

        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if(node.getNodeName().equalsIgnoreCase("figure")){
                    Character character = parseFigure(node, charactersDir);
                    if(character == null){
                        logger.log(Level.WARNING, "Failed to load "+ selected.toString().toLowerCase()+ " character/pose in panel ["+ panel.getId() + "]");
                    }
                    if(selected == Selected.LEFT){
                        panel.setCharacterLeft(character);
                    }else{
                        panel.setCharacterRight(character);
                    }
                }else if(node.getNodeName().equalsIgnoreCase("balloon")){
                    Element element = (Element) node;
                    String bubbleType = element.getAttribute("status");
                    String bubbleText = element.getElementsByTagName("content").item(0).getTextContent();

                    if(selected == Selected.LEFT){
                        BubbleType type = BubbleType.getBubble(bubbleType);
                        panel.setLeftBubbleType(type);
                        if(type != BubbleType.NONE){
                            panel.setLeftBubbleText(bubbleText);
                        }
                    }else if(selected == Selected.RIGHT){
                        BubbleType type = BubbleType.getBubble(bubbleType);
                        panel.setRightBubbleType(type);
                        if(type != BubbleType.NONE){
                            panel.setRightBubbleText(bubbleText);
                        }
                    }
                }
                else{
                    logger.log(Level.INFO, "Panel: " + panel.getId() + ". Cannot parse element: " + node.getNodeName());
                }
            }
        }
    }

    private static Character parseFigure(Node figureNode, File charactersDir){
        NodeList figureElements = figureNode.getChildNodes();

        String appearance = "female";
        Color skinColor = DEFAULT_SKIN_COLOR;
        Color hairColor = DEFAULT_FEMALE_HAIR_COLOR;
        Color lipsColor = DEFAULT_LIPS_COLOR;
        String pose = "neutral";
        String facing = "right";

        for(int i = 0; i < figureElements.getLength(); i++){
            Node node = figureElements.item(i);

            //only examine element nodes
            if(node.getNodeType() == Node.ELEMENT_NODE){
                if(node.getNodeName().equalsIgnoreCase("appearance")){
                    appearance = node.getTextContent();
                }else if(node.getNodeName().equalsIgnoreCase("skin")){
                    //Color.web encased in a try as it returns illegal argument exception on invalid color argument
                    String skin = node.getTextContent();
                    try{
                        skinColor = Color.web(skin);
                    }catch(IllegalArgumentException ex){
                        //ex.printStackTrace();
                        //to add error message to logger
                        logger.log(Level.WARNING, "Skin color invalid. Skin tone set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        //add error to logger
                        logger.log(Level.WARNING, "Skin color invalid. Skin tone set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("hair")){
                    String hair = node.getTextContent();
                    try{
                        hairColor = Color.web(hair);
                    }catch(IllegalArgumentException ex){
                        //to add error message to logger
                        logger.log(Level.WARNING, "Hair color invalid. Hair color set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        //add error to logger
                        logger.log(Level.WARNING, "Hair color invalid. Hair color set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("lips")){
                    String lips = node.getTextContent();
                    try{
                        lipsColor = Color.web(lips);
                    }catch(IllegalArgumentException ex){
                        //to add error message to logger
                        logger.log(Level.WARNING, "Lips color invalid. Lips color set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        //add error to logger
                        logger.log(Level.WARNING, "Lips color invalid. Lips color set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("pose")){
                    pose = node.getTextContent();
                }else if(node.getNodeName().equalsIgnoreCase("facing")){
                    facing = node.getTextContent();
                }
                else{
                    logger.info("Figure element ["+ node.getNodeName() + "] cannot be parsed in this app.");
                }
            }
        }

        Image charImage = loadCharacterPose(charactersDir, pose);
        if(charImage == null){
            return null;
        }
        Character character = new Character(charImage, pose);

        //default is female, hence if male change appearance
        if(appearance.equalsIgnoreCase("male")){
            character.switchGenders();
        }
        //default is right, hence if left change orientation
        if(facing.equalsIgnoreCase("left")){
            character.flipImage();
        }
        character.skinChange(skinColor);
        character.hairChange(hairColor);
        character.changeLipsColor(lipsColor);
        return character;
    }

    private static Image loadCharacterPose(File charDir, String pose){
        if(pose == null){
            return null;
        }
        final String POSE = pose.toLowerCase();

        FileFilter filter = file -> file.getName().endsWith("png") && file.getName().startsWith(POSE);
        File [] files = charDir.listFiles(filter);

        if(files.length != 0){
            return new Image(files[0].toURI().toString());
        }else{
            logger.log(Level.WARNING, "Pose not found in the given directory. Neutral pose uploaded.");
            return new Image("/resources/neutral.png");
        }
    }
}
