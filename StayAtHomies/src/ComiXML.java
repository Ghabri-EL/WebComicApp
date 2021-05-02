import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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

public class ComiXML implements DefaultColors {

    public static boolean createXML(ArrayList<Panel> list, File comiXMLFile){

        if(list.isEmpty()){
            return false;
        }

        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element panels = document.createElement("panels");
            document.appendChild(panels);

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
                System.out.println("XML file format could not be parsed properly. Incorrect XML file format.");
                return null;
            }

            NodeList listOfPanels = document.getElementsByTagName("panel");

            for(int i = 0; i < listOfPanels.getLength(); i++){
                Node node = listOfPanels.item(i);
                Panel panel = new Panel();
                int id = 0;
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element panelElement = (Element) node;

                    id = Integer.parseInt(panelElement.getAttribute("id"));
                    panel.setId(id);
                    System.out.println("ID: " + id);
                    Node topTextNode = panelElement.getElementsByTagName("above").item(0);
                    if(topTextNode != null){
                        String aboveText = topTextNode.getTextContent();
                        panel.setNarrativeTextTop(aboveText);
                    }

                    NodeList left = panelElement.getElementsByTagName("left");
                    parseLeftAndRightElement(left, panel, Selected.LEFT, charactersDir);

                    NodeList right = panelElement.getElementsByTagName("right");
                    parseLeftAndRightElement(right, panel, Selected.RIGHT, charactersDir);

                    Node belowTextNode = panelElement.getElementsByTagName("above").item(0);
                    if(belowTextNode != null){
                        String belowText = topTextNode.getTextContent();
                        panel.setNarrativeTextBottom(belowText);
                    }

                }
                if(panel.getCharacterLeft() != null && panel.getCharacterRight() != null){
                    panels.add(panel);
                }else{
                    //to add error to log
                    System.out.println("Panel [" + id +"]could not be parsed");
                }
            }
            return panels;
        }
        catch(ParserConfigurationException exception){
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void parseLeftAndRightElement(NodeList list, Panel panel, Selected selected, File charactersDir){
        Character character = null;
        list = list.item(0).getChildNodes();
        System.out.println("LIST:" + list.item(0).getNodeName());
        for(int i = 0; i < list.getLength(); i++){
            Node node = list.item(0);
            System.out.println("NODE: " + node.getNodeName());

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getNodeName().toLowerCase() == "figure"){
                   character = parseFigure(element, charactersDir);
                }
                else if(element.getNodeName().toLowerCase() == "balloon"){
                    String bubbleType = element.getAttribute("status");
                    String bubbleText = element.getElementsByTagName("content").item(0).getTextContent();

                    if(selected == Selected.LEFT){
                        BubbleType type = parseBubbleType(bubbleType);
                        panel.setLeftBubbleType(type);
                        if(type != BubbleType.NONE){
                            panel.setLeftBubbleText(bubbleText);
                        }
                    }else if(selected == Selected.RIGHT){
                        BubbleType type = parseBubbleType(bubbleType);
                        panel.setRightBubbleType(type);
                        if(type != BubbleType.NONE){
                            panel.setRightBubbleText(bubbleText);
                        }
                    }
                }
                else{
                    System.out.println("Cannot parse element: " + element.getNodeName());
                }
            }
        }
        if(selected == Selected.LEFT){
            panel.setCharacterLeft(character);
        }else{
            panel.setCharacterRight(character);
        }
    }

    private static Character parseFigure(Element figure, File charactersDir){
        Node appearanceNode = figure.getElementsByTagName("appearance").item(0);
        String appearance = null;
        if(appearanceNode != null){
            appearance = appearanceNode.getTextContent();
        }

        //Color.web encased in a try as it returns illegal argument exception on invalid color argument
        Node skinNode = figure.getElementsByTagName("skin").item(0);
        Color skinColor = DEFAULT_SKIN_COLOR;
        if(skinNode != null){
            String skin = skinNode.getTextContent();
            try{
                skinColor = Color.web(skin);
            }catch(IllegalArgumentException ex){
                //to add error message to logger
            }
        }

        Node hairNode = figure.getElementsByTagName("hair").item(0);
        Color hairColor = DEFAULT_FEMALE_HAIR_COLOR;
        if(hairNode != null){
            String hair = hairNode.getTextContent();
            try{
                hairColor = Color.web(hair);
            }catch(IllegalArgumentException ex){
                //to add error message to logger
            }
        }

        Node lipsNode = figure.getElementsByTagName("lips").item(0);
        Color lipsColor = DEFAULT_LIPS_COLOR;
        if(lipsNode != null){
            String lips = lipsNode.getTextContent();
            try{
                lipsColor = Color.web(lips);
            }catch(IllegalArgumentException ex){
                //to add error message to logger
            }
        }

        Node poseNode = figure.getElementsByTagName("pose").item(0);
        String pose = null;
        if(poseNode != null){
            pose = poseNode.getTextContent();
        }

        Node facingNode = figure.getElementsByTagName("facing").item(0);
        String facing = null;
        if(facingNode != null){
            facing = facingNode.getTextContent();
        }
        File imageFile = loadCharacterOnPose(charactersDir, pose);
        //if character not imported, return null, cannot create char
        if(imageFile == null){
            return null;
        }
        Image charImage = new Image(imageFile.toURI().toString());
        Character character = new Character(charImage, pose);

        if(appearance == "female"){
            character.switchGenders();
        }

        if(facing == "left"){
            character.flipImage();
        }

        return character;
    }

    private static File loadCharacterOnPose(File charDir, String pose){
        if(pose == null){
            return null;
        }
        final String POSE = pose.toLowerCase();

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith("png") && file.getName().startsWith(POSE);
            }
        };
        File [] files = charDir.listFiles(filter);

        if(files.length != 0){
            return files[0];
        }else{
            return new File("/resources/neutral.png");
        }
    }

    private static BubbleType parseBubbleType(String type){
        if(type.toLowerCase() == "speech"){
            return BubbleType.SPEECH;
        }
        else if(type.toLowerCase() == "thought"){
            return BubbleType.THOUGHT;
        }
        else{
            return BubbleType.NONE;
        }
    }
}
