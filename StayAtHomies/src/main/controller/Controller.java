package main.controller;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;

import main.comi_xml_handler.ComiXML;
import main.model.*;
import main.model.Character;
import main.project_enums.*;
import main.view.AppGUI;
import main.view.PanelView;
import java.util.ArrayList;
import main.html.HtmlCreator;
//main.controller.Controller.java represents the main.controller.Controller following the MVC pattern
public class Controller {
   private final ComixApp comixApp;
   private final AppGUI view;

   public Controller(ComixApp comixApp, AppGUI view){
       this.comixApp = comixApp;
       this.view = view;
   }
   public void execution(){
       selectHandler();
       view.getImportLeftCharButton().setOnAction(event -> importLeftModelEvent());
       view.getImportRightCharButton().setOnAction(event -> importRightModelEvent());
       view.getFlipButton().setOnAction(event -> flipCharacterEvent());
       view.getGenderSwapButton().setOnAction(event -> genderSwapEvent());
       view.getChangeSkinToneButton().setOnAction(event -> changeSkinToneEvent());
       view.getChangeHairColorButton().setOnAction(event -> changeHairColorEvent());
       view.getChangeLipsColorButton().setOnAction(event -> changeLipsColorEvent());
       view.getAddSpeechBubbleButton().setOnAction(event -> addSpeechBubbleEvent());
       view.getAddThoughtBubbleButton().setOnAction(event -> addThoughtBubbleEvent());
       view.getRemoveBubbleButton().setOnAction(event -> removeBubbleEvent());
       view.getAddTextTopButton().setOnAction(event -> addNarrativeTextTopEvent());
       view.getAddTextBottomButton().setOnAction(event -> addNarrativeTextBottomEvent());
       view.getFileMenuCharactersDir().setOnAction(event -> openCharacterDirectory());
       view.getPanelMenuNew().setOnAction(event -> newPanelEvent());
       view.getPanelMenuSave().setOnAction(event -> savePanelEvent());
       view.getSavePanel().setOnAction(event -> savePanelEvent());
       view.getPanelMenuDelete().setOnAction(event -> deletePanelEvent());
       view.getDeletePanel().setOnAction(event -> deletePanelEvent());
       view.getHelpPage().setOnAction(event -> helpPage());
       view.getHelpStartedPage().setOnAction(event -> gettingStarted());
       view.getAboutPage().setOnAction(event -> aboutPage());
       view.getFileMenuSaveXML().setOnAction(event -> saveComiXML());
       view.getFileMenuLoadXML().setOnAction(event -> loadComiXML());
       view.getSaveAsHtml().setOnAction(event -> saveAsHTML());
   }

   private void selectHandler(){
       EventHandler<MouseEvent> leftViewEvent = event -> {
           view.selectFrame(Selected.LEFT);
           comixApp.selectCharacter(Selected.LEFT);
           event.consume();
       };

       EventHandler<MouseEvent> rightViewEvent = event -> {
           view.selectFrame(Selected.RIGHT);
           comixApp.selectCharacter(Selected.RIGHT);
           event.consume();
       };
       //created handlers to handles selecting the left or right character
       //and set them for each view
       view.setSelectingHandler(leftViewEvent, rightViewEvent);
   }
   private void importLeftModelEvent(){
       File imageFile = view.importModel();
       if(imageFile != null){
           Image charImage = new Image(imageFile.toURI().toString());
           String pose = imageFile.getName().replaceFirst("[.][^.]+$", "");
           Character character = new Character(charImage, pose);
           comixApp.setCharacterLeft(character);
           view.getLeftCharView().setImage(character.getCharacterImage());
           comixApp.selectCharacter(Selected.LEFT);
           view.selectFrame(Selected.LEFT);
       }
   }

   private void importRightModelEvent(){
       File imageFile = view.importModel();
       if(imageFile != null){
           Image charImage = new Image(imageFile.toURI().toString());
           String pose = imageFile.getName().replaceFirst("[.][^.]+$", "");
           Character character = new Character(charImage, pose);
           comixApp.setCharacterRight(character);
           view.getRightCharView().setImage(character.getCharacterImage());
           comixApp.selectCharacter(Selected.RIGHT);
           view.selectFrame(Selected.RIGHT);
       }
   }

   private void flipCharacterEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           Character selectedCharacter = comixApp.getSelectedCharacter();
           selectedCharacter.flipImage();
           Image newCharImage = selectedCharacter.getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void genderSwapEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           comixApp.getSelectedCharacter().switchGenders();
           Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void changeSkinToneEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           comixApp.getSelectedCharacter().skinChange(view.getSelectedColor());
           Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void changeHairColorEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           Character selectedCharacter = comixApp.getSelectedCharacter();
           selectedCharacter.hairChange(view.getSelectedColor());
           Image newCharImage = selectedCharacter.getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void changeLipsColorEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           Character selectedCharacter = comixApp.getSelectedCharacter();
           selectedCharacter.changeLipsColor(view.getSelectedColor());
           Image newCharImage = selectedCharacter.getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void addSpeechBubbleEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           String bubbleText;
           bubbleText = view.importSpeechBubble();
           if(bubbleText != null){
               comixApp.setBubbleText(bubbleText);
               comixApp.setBubbleType(BubbleType.SPEECH);
           }
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void addThoughtBubbleEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           String bubbleText;
           bubbleText = view.importThoughtBubble();
           if(bubbleText != null){
               comixApp.setBubbleText(bubbleText);
               comixApp.setBubbleType(BubbleType.THOUGHT);
           }
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void removeBubbleEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           if(view.getSelectedCharacterView() == view.getLeftCharView()){
               view.getLeftBubble().setImage(null);
               view.getLeftBubbleText().setText(null);
           }
           else{
               view.getRightBubble().setImage(null);
               view.getRightBubbleText().setText(null);
           }
           comixApp.setBubbleText(null);
           comixApp.setBubbleType(BubbleType.NONE);
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void addNarrativeTextTopEvent(){
       String narrativeText = view.addNarrativeTextTop();
       if(narrativeText != null){
           comixApp.setNarrativeTextTop(narrativeText);
           view.getTopNarrativeText().setText(narrativeText);
       }
   }

   private void addNarrativeTextBottomEvent(){
       String narrativeText = view.addNarrativeTextBottom();
       if(narrativeText != null){
           comixApp.setNarrativeTextBottom(narrativeText);
           view.getBottomNarrativeText().setText(narrativeText);
       }
   }

   private void openCharacterDirectory(){
       view.setCharactersDirectory();
   }

   private void savePanelEvent(){
       if(comixApp.readyToCreate()){
           if(view.isPanelSelected()){
               editExistingPanel();
           }
           else{
               saveNewPanel();
           }
       }
       else{
           view.userInformationAlert("Import characters", "You need two characters on a panel in order to save a panel");
       }
   }

   private void saveNewPanel(){
       int id = comixApp.generateId();
       PanelView panel = view.createPanel();
       comixApp.setPanelShot(panel.getImage());
       comixApp.createPanelAndAddToStrip();
       panel.setPanelId(id);
       addPanelEventHandler(panel);
       view.addPanelToStrip(panel);
       resetWorkingPane();
   }

    private void saveNewPanel(int id){
        PanelView panel = view.createPanel();
        comixApp.setPanelShot(panel.getImage());
        comixApp.createPanelAndAddToStrip();
        panel.setPanelId(id);
        addPanelEventHandler(panel);
        view.addPanelToStrip(panel);
        resetWorkingPane();
    }

   private void editExistingPanel(){
       int id = view.getSelectedPanel().getPanelId();

       PanelView viewPanel = view.editSelectedPanel();
       comixApp.setPanelShot(viewPanel.getImage());

       Panel basePanel = comixApp.createPanel();
       comixApp.editPanel(id, basePanel);
   }

   private void newPanelEvent(){
       if(view.confirmWorkingPaneReset()){
            resetWorkingPane();
       }
   }
   private void resetWorkingPane(){
       comixApp.resetWorkingSpace();
       view.resetWorkingPane();
   }

   //add handler to panel to select panel and load it in the working pane
   private void addPanelEventHandler(PanelView panel){
       panel.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
           view.selectPanel(panel);
           loadSelectedPanel(panel.getPanelId());

           //right click menu for each panel
           if(mouseEvent.getButton() == MouseButton.SECONDARY){
               ContextMenu panelMenu = view.getSelectedPanelMenu();
               panelMenu.show(panel, mouseEvent.getScreenX(), mouseEvent.getScreenY());
           }
           mouseEvent.consume();
       });
   }

   private void loadSelectedPanel(int id){
       boolean loaded = comixApp.loadSelectedPanel(id);

       if(loaded){
           Character leftChar = comixApp.getCharacterLeft();
           Character rightChar = comixApp.getCharacterRight();

           //parameters: Image leftCharacter, Image rightCharacter, main.project_enums.BubbleType leftBubbleType, main.project_enums.BubbleType rightBubbleType,
           //String leftBubbleText, String rightBubbleText, String topNarrativeText, String bottomNarrativeText
           view.loadSelectedPanel(leftChar.getCharacterImage(), rightChar.getCharacterImage(), comixApp.getLeftBubbleType(),
                   comixApp.getRightBubbleType(), comixApp.getLeftBubbleText(), comixApp.getRightBubbleText(),
                   comixApp.getNarrativeTextTop(), comixApp.getNarrativeTextBottom());
       }
   }

   private void deletePanelEvent(){
       if(view.confirmDeletePanel()){
           deletePanel();
       }
   }

   private void deletePanel(){
       if(view.isPanelSelected()){
           int id = view.deletePanel();
           comixApp.deletePanel(id);
       }
   }

   private void helpPage() {
        view.createRightPaneHelp();
   }

   private void gettingStarted() {
        view.createRightPaneGS();
   }

   private void aboutPage() {
        view.createRightPaneAbout();
   }

   private void saveAsHTML()
   {
       if(comixApp.noPanels()){
           view.userErrorAlert("Failed to save ", "Comic strip is empty");
           return;
       }
       view.userInformationAlert("Save as HTML", "Please select a directory for the panels, then enter the name of the html file and select file destination");
       File dir = view.setHTMLDirectory();
       File outputter = view.saveHTMLFileWindow();

       if(dir != null && outputter != null){
           ArrayList <Image> arraySnaps = comixApp.getComixStrip().sendSnapshot();
           new HtmlCreator().snapToHTML(arraySnaps, outputter, dir);
       }
    }

   private void saveComiXML(){
       if(comixApp.noPanels()){
           view.userErrorAlert("Failed to save ", "Comic strip is empty");
           return;
       }

       File xmlFile = view.saveXMLFileWindow();
       if(xmlFile != null){
           boolean created = ComiXML.createXML(comixApp.getComixStrip().getPanels(), xmlFile);
           if(!created){
               //log file feature to be added
               view.userErrorAlert("Failed to save ", "Failed to save the XML file.");
           }
           else{
               view.userInformationAlert("Saved file", "Project saved in XML format successfully");
           }
       }
   }

   private void loadComiXML(){
       boolean load = true;
       if(!comixApp.noPanels()){
           load = view.confirmLoadXML();
       }

       if(load){
           File xmlFile = view.loadXMLFileWindow();
           if(xmlFile != null){
               ArrayList<Panel> panels = ComiXML.createComicStripFromComiXML(xmlFile, view.getDefaultCharactersDirectory());
               if(!panels.isEmpty()){
                   comixApp.clearComixStrip();
                   view.clearComicStrip();
                   for(Panel p : panels){
                       loadPanel(p);
                       saveNewPanel(p.getId());
                   }
                   view.userInformationAlert("Loaded successfully", "Xml file loaded successfully." +
                           " Please check the log file for more details");
               }
               else{
                   view.userInformationAlert("Loading status", "No panels were loaded");
               }
           }
       }
   }

    private void loadPanel(Panel panel){
       comixApp.loadPanel(panel);
       Character leftChar = panel.getCharacterLeft();
       Character rightChar = panel.getCharacterRight();

       //parameters: Image leftCharacter, Image rightCharacter, main.project_enums.BubbleType leftBubbleType, main.project_enums.BubbleType rightBubbleType,
       // String leftBubbleText, String rightBubbleText, String topNarrativeText, String bottomNarrativeText
       view.loadSelectedPanel(leftChar.getCharacterImage(), rightChar.getCharacterImage(), panel.getLeftBubbleType(),
                panel.getRightBubbleType(), panel.getLeftBubbleText(), panel.getRightBubbleText(),
                panel.getNarrativeTextTop(), panel.getNarrativeTextBottom());
    }
}