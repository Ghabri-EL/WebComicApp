import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ComiXML {

    public static boolean createXML(ArrayList<Panel> list){

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

                Element top = document.createElement("top");
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

                Element bottom = document.createElement("bottom");
                bottom.appendChild(document.createTextNode(currentPanel.getNarrativeTextBottom()));
                panel.appendChild(bottom);

                panels.appendChild(panel);
            }
            File file = new File("./comiXML.xml");

            if(!file.exists()){
                file.delete();
                file.createNewFile();
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

            //just for testing
//            BufferedReader br = new BufferedReader(new FileReader("./comiXML.xml"));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }

            return true;
        }
        catch(ParserConfigurationException exception){
            exception.printStackTrace();
        }
        catch(TransformerConfigurationException exception){
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}
