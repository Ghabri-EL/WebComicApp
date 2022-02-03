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
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadComiXML implements DefaultColors {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String title;
    private String credits;
    private ArrayList<Panel> panels;
    private boolean failedToParse;
    private File xmlFile;
    private File charactersDir;

    public LoadComiXML(File xmlFile, File charactersDir){
        panels = new ArrayList<>();
        failedToParse = false;
        this.xmlFile = xmlFile;
        this.charactersDir = charactersDir;
    }
    public void createComicStripFromComiXML(){
        if(xmlFile == null){
            LOGGER.log(Level.SEVERE, "Given xml file is is null");
            return;
        }

        try{
            LOGGER.log(Level.FINEST, "LOADING COMIXML LOG");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            if(document == null){
                LOGGER.log(Level.WARNING, "XML file format could not be parsed properly. Incorrect XML file format.");
                failedToParse = true;
                return;
            }
            document.normalizeDocument();
            Node title = document.getElementsByTagName("title").item(0);
            Node credits = document.getElementsByTagName("credits").item(0);

            if(title != null){
                this.title = title.getTextContent();
            }
            else {
                LOGGER.log(Level.WARNING, "Failed to parse title element");
            }

            if(credits != null){
                this.credits = credits.getTextContent();
            }
            else{
                LOGGER.log(Level.WARNING, "Failed to parse credits element");
            }

            NodeList listOfPanels = document.getElementsByTagName("panel");
            int id;
            int idGenerated = 0;
            for(int i = 0; i < listOfPanels.getLength(); i++){
                Node node = listOfPanels.item(i);
                Panel panel = new Panel();
                LOGGER.log(Level.FINEST, "Parsing :: [Panel " + i +"]");

                Element panelElement = (Element) node;
                if(panelElement.hasAttribute("id")){
                    try{
                        id = Integer.parseInt(panelElement.getAttribute("id"));
                        panel.setId(id);
                    }catch(NumberFormatException ex){
                        LOGGER.info("Panel does not contain an id attribute. Id generated: " + idGenerated);
                        id = idGenerated;
                    }
                }else{
                    id = idGenerated;
                }

                panel.setId(id);
                //parse the current panel
                parsePanel(node, panel, charactersDir);

                if(panel.getCharacterLeft() != null && panel.getCharacterRight() != null){
                    panels.add(panel);
                    //log panel created successfully
                    LOGGER.info("Panel [id:" +  panel.getId() + "] loaded successfully");
                    idGenerated++;
                }else{
                    //to add error to log
                    LOGGER.log(Level.WARNING, "Panel number [" + panel.getId() +"]could not be parsed");
                }
            }
            if(panels.isEmpty()){
                LOGGER.info("No panels were found in the file provided.");
            }
        }
        catch(ParserConfigurationException exception){
            exception.printStackTrace();
            LOGGER.log(Level.SEVERE, "Parser configuration error");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "XML file could not be loaded properly.");
        } catch (SAXException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "XML file format could not be parsed properly. Incorrect XML file format.");
            failedToParse = true;
        }
    }

    private void parsePanel(Node panelNode, Panel panel, File charactersDir){
        NodeList panelElements = panelNode.getChildNodes();

        boolean leftFound = false;
        boolean rightFound = false;
        for(int i = 0; i < panelElements.getLength(); i++){
            Node node = panelElements.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                if(node.getNodeName().equalsIgnoreCase("above")){
                    NarrativeText topNarrativeText = parseNarrativeText(node);
                    panel.setNarrativeTextTop(topNarrativeText);
                }
                else if(node.getNodeName().equalsIgnoreCase("left")){
                    leftFound = true;
                    parseLeftAndRightElement(node, panel, Selected.LEFT, charactersDir);
                }
                else if(node.getNodeName().equalsIgnoreCase("right")){
                    rightFound = true;
                    parseLeftAndRightElement(node, panel, Selected.RIGHT, charactersDir);
                }else if(node.getNodeName().equalsIgnoreCase("below")){
                    NarrativeText bottomNarrativeText = parseNarrativeText(node);
                    panel.setNarrativeTextBottom(bottomNarrativeText);
                }
                else{
                    LOGGER.info( "Node [" + node.getNodeName() + "] cannot be parsed");
                }
            }
        }
        if(!leftFound){
            panel.setCharacterLeft(null);
            LOGGER.log(Level.WARNING, "Left element in [panel " + panel.getId() + "] not found");
        }

        if(!rightFound){
            panel.setCharacterRight(null);
            LOGGER.log(Level.WARNING, "Right element in [panel " + panel.getId() + "] not found");
        }
    }

    private NarrativeText parseNarrativeText(Node narrativeTextNode){
        String text;
        if(narrativeTextNode.getChildNodes().getLength() == 1){
            text = narrativeTextNode.getTextContent();
            return new NarrativeText(text);
        }
        else{
            NodeList nodeElements = narrativeTextNode.getChildNodes();
            NarrativeText narrativeText = new NarrativeText();
            for(int i = 0; i < nodeElements.getLength(); i++){
                Node node = nodeElements.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    if(node.getNodeName().equalsIgnoreCase("content")){
                        text = node.getTextContent();
                        narrativeText.setNarrativeText(text);
                    }
                    else if(node.getNodeName().equalsIgnoreCase("wrap")){
                        String wrap = node.getTextContent();
                        narrativeText.setNarrativeTextWrap(NarrativeTextWrap.getWrapType(wrap));
                        if(!(wrap.equalsIgnoreCase("nowrap") || wrap.equalsIgnoreCase("wrap"))){
                            LOGGER.log(Level.WARNING, "Wrap element does not contain a valid value. Wrap value set to default");
                        }
                    }
                    else{
                        LOGGER.info("This element [" + node.getNodeName() + "] cannot be parsed in this app.");
                    }
                }
            }
            return narrativeText;
        }
    }

    //partition refers to the left or right side of the panel
    private void parseLeftAndRightElement(Node partition, Panel panel, Selected selected, File charactersDir){
        NodeList list = partition.getChildNodes();

        boolean bubblePresent = false;
        boolean figurePresent = false;
        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if(node.getNodeName().equalsIgnoreCase("figure")){
                    figurePresent = true;
                    Character character = parseFigure(node, charactersDir);
                    if(selected == Selected.LEFT){
                        panel.setCharacterLeft(character);
                    }else{
                        panel.setCharacterRight(character);
                    }
                }else if(node.getNodeName().equalsIgnoreCase("balloon")){
                    bubblePresent = true;
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
                    LOGGER.log(Level.INFO, "Panel: " + panel.getId() + ". Cannot parse element: " + node.getNodeName());
                }
            }
        }
        if(!figurePresent){
            if(selected == Selected.LEFT){
                panel.setCharacterLeft(new Character(loadNeutralPose(), "neutral"));
            }else{
                panel.setCharacterRight(new Character(loadNeutralPose(), "neutral"));
            }
            LOGGER.log(Level.WARNING, "Figure: "+ selected.toString().toLowerCase()+ " character in [panel "+ panel.getId() + "] " +
                    "not found. Neutral loaded.");
        }
        if(!bubblePresent){
            LOGGER.log(Level.WARNING, "Failed to load "+ selected.toString().toLowerCase()+ " bubble in [panel "+ panel.getId() + "]");
        }
    }

    private Character parseFigure(Node figureNode, File charactersDir){
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
                    if(!(appearance.equalsIgnoreCase("male") || appearance.equalsIgnoreCase("female"))){
                        LOGGER.log(Level.WARNING, "Appearance value is invalid. Appearance set to default(female).");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("skin")){
                    //Color.web encased in a try as it returns illegal argument exception on invalid color argument
                    String skin = node.getTextContent();
                    try{
                        skinColor = Color.web(skin);
                    }catch(IllegalArgumentException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Skin color invalid. Skin tone set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Skin color invalid. Skin tone set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("hair")){
                    String hair = node.getTextContent();
                    try{
                        hairColor = Color.web(hair);
                    }catch(IllegalArgumentException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Hair color invalid. Hair color set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Hair color invalid. Hair color set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("lips")){
                    String lips = node.getTextContent();
                    try{
                        lipsColor = Color.web(lips);
                    }catch(IllegalArgumentException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Lips color invalid. Lips color set to default");
                    }catch(NullPointerException ex){
                        //ex.printStackTrace();
                        LOGGER.log(Level.WARNING, "Lips color invalid. Lips color set to default");
                    }
                }else if(node.getNodeName().equalsIgnoreCase("pose")){
                    pose = node.getTextContent();
                }else if(node.getNodeName().equalsIgnoreCase("facing")){
                    facing = node.getTextContent();
                    if(!(facing.equalsIgnoreCase("left") || facing.equalsIgnoreCase("right"))){
                        LOGGER.log(Level.WARNING, "Orientation/Facing value is invalid. Orientation set to default(right).");
                    }
                }
                else{
                    LOGGER.info("Figure element ["+ node.getNodeName() + "] cannot be parsed in this app.");
                }
            }
        }

        Image charImage = loadCharacterPose(charactersDir, pose);
        if(charImage == null){
            LOGGER.log(Level.WARNING, "Failed to load character pose [" + pose + "]");
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

    private Image loadCharacterPose(File charDir, String pose){
        if(pose == null){
            LOGGER.log(Level.WARNING, "Pose invalid. Neutral pose imported.");
            return loadNeutralPose();
        }

        if(charDir == null){
            LOGGER.log(Level.WARNING, "Invalid characters directory. Neutral pose imported.");
            return loadNeutralPose();
        }

        final String POSE = pose.toLowerCase();
        FileFilter filter = file -> file.getName().endsWith("png") && file.getName().startsWith(POSE);
        File [] files = charDir.listFiles(filter);

        if(files.length != 0){
            return new Image(files[0].toURI().toString());
        }else{
            LOGGER.log(Level.WARNING, "Pose [" + pose + "] not found in the given directory. Neutral pose imported.");
            return loadNeutralPose();
        }
    }

    private Image loadNeutralPose(){
        return new Image("/resources/neutral.png");
    }

    public ArrayList<Panel> getPanels(){
        return this.panels;
    }

    public String getTitle() {
        return title;
    }

    public String getCredits() {
        return credits;
    }

    public boolean isXMLNull(){
        return this.xmlFile == null;
    }

    public boolean failedToParse(){
        return failedToParse;
    }
}
