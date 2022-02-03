package main.controller;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import main.comi_xml_handler.GenerateComiXML;
import main.comi_xml_handler.LoadComiXML;
import main.model.*;
import main.model.Character;
import main.project_enums.*;
import main.view.AppGUI;
import main.view.PanelView;
import java.io.IOException;
import java.util.ArrayList;
import main.html.HtmlCreator;
import javax.imageio.ImageIO;

//Controller represents the Controller following the MVC pattern
public class Controller {
   private final ComixApp comixApp;
   private final AppGUI view;
   private boolean isSaved;

   public Controller(ComixApp comixApp, AppGUI view){
       this.comixApp = comixApp;
       this.view = view;
       isSaved = true;
   }

   public void execution(){
       selectCharacterAddEventHandler();
       topBarButtonsHandler();
       leftPanelButtonsHandler();
       comicStripButtonsHandler();
   }

   private void leftPanelButtonsHandler(){
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
       view.getSingleLineOptionTop().setOnAction(event -> narrativeTopSingleLine());
       view.getMultiLinesOptionTop().setOnAction(event -> narrativeTopMultiLines());
       view.getSingleLineOptionBottom().setOnAction(event -> narrativeBottomSingleLine());
       view.getMultiLinesOptionBottom().setOnAction(event -> narrativeBottomMultiLines());
       view.getAddTextBottomButton().setOnAction(event -> addNarrativeTextBottomEvent());
       view.getSetComicTitle().setOnAction(event -> setComicTitle());
       view.getSetComicCredits().setOnAction(event -> setComicCredits());
   }

   private void topBarButtonsHandler(){
       view.getFileMenuCharactersDir().setOnAction(event -> openCharacterDirectory());
       view.getPanelMenuNew().setOnAction(event -> newPanelEvent());
       view.getPanelMenuSave().setOnAction(event -> savePanelEvent());
       view.getPanelMenuDelete().setOnAction(event -> deletePanelEvent());
       view.getHelpPage().setOnAction(event -> helpPage());
       view.getHelpStartedPage().setOnAction(event -> gettingStarted());
       view.getAboutPage().setOnAction(event -> aboutPage());
       view.getFileMenuSaveXML().setOnAction(event -> saveComiXML());
       view.getFileMenuLoadXML().setOnAction(event -> loadComiXML());
       view.getSaveAsHtml().setOnAction(event -> saveAsHTML());
   }

   private void comicStripButtonsHandler(){
       view.getSavePanel().setOnAction(event -> savePanelEvent());
       view.getDeletePanel().setOnAction(event -> deletePanelEvent());
       view.getChangePanelPosition().setOnAction(event -> changePanelPosition());
       view.getPositionToLeft().setOnAction(event -> changePanelPositionLeft());
       view.getPositionToRight().setOnAction(event -> changePanelPositionRight());
   }

   private void selectCharacterAddEventHandler(){
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
       unsavedChanges();
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
       unsavedChanges();
   }

   private void flipCharacterEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           Character selectedCharacter = comixApp.getSelectedCharacter();
           selectedCharacter.flipImage();
           Image newCharImage = selectedCharacter.getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
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
           unsavedChanges();
       }
       else{
           view.userInformationAlert("Select character", "Select the characters on which you want to perform the operation");
       }
   }

   private void addNarrativeTextTopEvent(){
       String text = view.addNarrativeTextTop();
       if(text != null){
           NarrativeText narrativeText = new NarrativeText(text);
           comixApp.setNarrativeTextTop(narrativeText);
           unsavedChanges();
       }
   }

   private void addNarrativeTextBottomEvent(){
       String text = view.addNarrativeTextBottom();
       if(text != null){
           NarrativeText narrativeText = new NarrativeText(text);
           comixApp.setNarrativeTextBottom(narrativeText);
           unsavedChanges();
       }
   }

   private void narrativeTopSingleLine(){
        view.narrativeTextFormat(view.getTopNarrativeText());
        comixApp.getNarrativeTextTop().setNarrativeTextWrap(NarrativeTextWrap.NOWRAP);
        unsavedChanges();
   }

   private void narrativeTopMultiLines(){
       view.narrativeTextFormatTextWrapping(view.getTopNarrativeText());
       comixApp.getNarrativeTextTop().setNarrativeTextWrap(NarrativeTextWrap.WRAP);
       unsavedChanges();
   }

   private void narrativeBottomSingleLine(){
       view.narrativeTextFormat(view.getBottomNarrativeText());
       comixApp.getNarrativeTextBottom().setNarrativeTextWrap(NarrativeTextWrap.NOWRAP);
       unsavedChanges();
   }

    private void narrativeBottomMultiLines(){
        view.narrativeTextFormatTextWrapping(view.getBottomNarrativeText());
        comixApp.getNarrativeTextBottom().setNarrativeTextWrap(NarrativeTextWrap.WRAP);
        unsavedChanges();
    }

   private void setComicTitle(){
       String title = view.setComicTitleDialog();
       if(title != null){
           comixApp.setComicTitle(title);
       }
   }

   private void setComicCredits() {
       String credits = view.setComicCreditsDialog();
       if(credits != null) {
           comixApp.setComicCredits(credits);
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
               refreshViewComicStrip();
           }
           savedChanges();
       }
       else{
           view.userInformationAlert("Import characters", "You need two characters on a panel in order to save a panel");
       }
   }

   //saves panel into the comic strip and reflects the save operation in the view
   private void saveNewPanel(){
       PanelView panel = view.createPanel();
       comixApp.setPanelShot(panel.getImage());
       comixApp.createPanelAndAddToStrip();
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
       savedChanges();
   }

   private void changePanelPosition(){
       savePanelPrompt();
       String newPosition = view.changePanelIdWindow();
       if(newPosition == null){
           return;
       }

       boolean fail = true;
       try {
           int newId = Integer.parseInt(newPosition);

           if(newId >= 1 && newId <= comixApp.getNumberOfPanels()){
               newId -= 1;
               int panelId = comixApp.getId();
               comixApp.changePanelPosition(panelId, newId);
               refreshViewComicStrip();
               resetWorkingPane();
               loadAndSelectPanel(newId);
               fail = false;
           }

       }catch (NumberFormatException ex){
           ex.printStackTrace();
       }
       if(fail){
           view.userErrorAlert("Invalid position ", "Invalid value entered. " +
                   "Please enter an integer value in the range [1 - " + comixApp.getNumberOfPanels() + "]");
       }
   }

   private void changePanelPositionLeft(){
       savePanelPrompt();
       int panelId = comixApp.getId();
       if(comixApp.noPanels() || panelId < 1){
           return;
       }
       int newId = panelId - 1;
       comixApp.changePanelPosition(panelId, newId);
       refreshViewComicStrip();
       resetWorkingPane();
       loadAndSelectPanel(newId);
   }

   private void changePanelPositionRight(){
       savePanelPrompt();
       int panelId = comixApp.getId();
       if(comixApp.noPanels() || panelId >= (comixApp.getNumberOfPanels() - 1)){
           return;
       }
       int newId = panelId + 1;
       comixApp.changePanelPosition(panelId, newId);
       refreshViewComicStrip();
       resetWorkingPane();
       loadAndSelectPanel(newId);
   }

   private void loadAndSelectPanel(int id){
       loadSelectedPanel(id);
       view.selectPanel(id);
   }

   //add handler to panel to select panel and load it in the working pane
   private void addPanelEventHandler(PanelView panel){
       panel.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
           if(panel != view.getSelectedPanel()){
               savePanelPrompt();
               view.selectPanel(panel);
               loadSelectedPanel(panel.getPanelId());
           }
           //right click menu for each panel(save, delete, change position in the strip)
           if(mouseEvent.getButton() == MouseButton.SECONDARY){
               ContextMenu panelMenu = view.getSelectedPanelMenu();
               panelMenu.show(panel, mouseEvent.getScreenX(), mouseEvent.getScreenY());
           }
           mouseEvent.consume();
       });
   }

   private void savePanelPrompt(){
       //this method prompts the user to save panel if changes have been made to the current panel
       //before switching to the next panel
       if(!isSaved) {
           if (view.confirmChangingPanel()) {
               savePanelEvent();
           }
           else{
               savedChanges();
           }
       }
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
            if(view.isPanelSelected()){
                int id = view.getSelectedPanel().getPanelId();
                comixApp.deletePanel(id);
                view.resetWorkingPane();
                refreshViewComicStrip();
            }
        }
        else{
            view.userErrorAlert("Panel removal error", "No panel has been selected");
        }
    }

    private void refreshViewComicStrip(){
       ArrayList<PanelView> panelViewArray = new ArrayList<>();
       for(Panel p : comixApp.getComixStrip().getPanels()){
           PanelView panelView = new PanelView(p.getPanelShot(), p.getId());
           //add handler to each panel
           addPanelEventHandler(panelView);
           panelViewArray.add(panelView);
       }
       view.refreshComicStrip(panelViewArray);
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

   private void changeTitlePrompt() {
       if(comixApp.getComicTitle() == "HomiesComix"){
           if(view.confirmTitleChange()){
               setComicTitle();
           }
       }
   }

   private void changeCreditsPrompt() {
       if(comixApp.getComicCredits() == "HomiesComix"){
           if(view.confirmCreditsChange()){
               setComicCredits();
           }
       }
   }

   private BufferedImage endPanelPrompt(){
       if(view.confirmEndPanel()){
            File file = view.getEndPanelWindow();
            if(file != null){
                try {
                    BufferedImage img = ImageIO.read(file);
                    return img;
                } catch (IOException e) {
                    view.userErrorAlert("Failed to open file", "Failed to open the selected image you selected as a closing panel." +
                            "\nImporting default closing panel.");
                    return defaultEndPanel();
                }
            }
            else{
                return defaultEndPanel();
            }
       }
       return null;
   }

   private BufferedImage defaultEndPanel(){
       try {
           BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/resources/closingPanel.png"));
           return img;
       } catch (IOException e) {
           view.userErrorAlert("Failed to open file", "Failed to import default closing panel");
           return null;
       }
   }

    private void saveAsHTML()
    {
        if(comixApp.noPanels()){
            view.userErrorAlert("Failed to save ", "Comic strip is empty");
            return;
        }
        view.userInformationAlert("Save as HTML", "Please select a directory for the panels," +
                " then enter the name of the html file and select file destination");
        File dir = view.setHTMLDirectory();
        File outputter = view.saveHTMLFileWindow();

        if(dir != null && outputter != null){
            changeTitlePrompt();
            changeCreditsPrompt();
            BufferedImage closingPanel = endPanelPrompt();
            ArrayList <Image> arraySnaps = comixApp.getComixStrip().sendSnapshot();
            HtmlCreator htmlCreator = new HtmlCreator();
            htmlCreator.snapToHTML(arraySnaps, outputter, dir, comixApp.getComicTitle(), comixApp.getComicCredits(), closingPanel);

            if(htmlCreator.isSaved()){
                view.userInformationAlert("Saved HTML", "Html file saved successfully");
            }
            else{
                view.userErrorAlert("Failed to save", "Failed to save the html and panels properly");
            }
        }
    }

   private void saveComiXML(){
       if(comixApp.noPanels()){
           view.userErrorAlert("Failed to save ", "Comic strip is empty");
           return;
       }

       File xmlFile = view.saveXMLFileWindow();
       if(xmlFile != null){
           changeTitlePrompt();
           changeCreditsPrompt();

           boolean created = GenerateComiXML.createXML(comixApp.getComixStrip().getPanels(), xmlFile, comixApp.getComicTitle(), comixApp.getComicCredits());
           if(!created){
               view.userErrorAlert("Failed to save ", "Failed to save the XML file.\nPlease check the log file for more details.");
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
               LoadComiXML loader = new LoadComiXML(xmlFile, view.getDefaultCharactersDirectory());
               loader.createComicStripFromComiXML();
               ArrayList<Panel> panels = loader.getPanels();
               if(!panels.isEmpty()){
                   comixApp.clearComixStrip();
                   for(Panel p : panels){
                       loadPanelToWorkingSpace(p);
                       saveNewPanel();
                   }
                   //panels loaded in the model, refresh view comic strip based on model
                   refreshViewComicStrip();

                   if(loader.getTitle() != null){
                       comixApp.setComicTitle(loader.getTitle());
                   }

                   if(loader.getCredits() != null){
                       comixApp.setComicCredits(loader.getCredits());
                   }

                   view.userInformationAlert("Loaded ComiXML File", "Xml file loaded successfully." +
                           " Please check the log file for any errors encountered while loading the file." +
                           "\n[Log file located in the same directory as the executable]");
               }
               else{
                   if(loader.failedToParse()){
                       view.userErrorAlert("Corrupted XML File", "Provided ComiXML file is corrupted and could not be parsed");
                   }
                   view.userInformationAlert("Loading status", "No panels were loaded");
               }
           }
       }
   }

   //used to load panels parsed from the xml file into the scene/working space
    private void loadPanelToWorkingSpace(Panel panel){
       comixApp.loadPanel(panel);
       Character leftChar = panel.getCharacterLeft();
       Character rightChar = panel.getCharacterRight();

       //parameters: Image leftCharacter, Image rightCharacter, main.project_enums.BubbleType leftBubbleType, main.project_enums.BubbleType rightBubbleType,
       // String leftBubbleText, String rightBubbleText, String topNarrativeText, String bottomNarrativeText
       view.loadSelectedPanel(leftChar.getCharacterImage(), rightChar.getCharacterImage(), panel.getLeftBubbleType(),
                panel.getRightBubbleType(), panel.getLeftBubbleText(), panel.getRightBubbleText(),
                panel.getNarrativeTextTop(), panel.getNarrativeTextBottom());
    }

    private void unsavedChanges(){
       isSaved = false;
    }

    private void savedChanges(){
       isSaved = true;
    }
}