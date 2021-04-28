import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;

//Controller.java represents the Controller following the MVC pattern
public class Controller {
   private final ComixApp comixApp;
   private final AppGUI view;

   public Controller(ComixApp comixApp, AppGUI view){
       this.comixApp = comixApp;
       this.view = view;
   }
   public void execution(){
       selectHandler();
       view.getImportLeftCharButton().setOnAction(actionEvent -> importLeftModelEvent());
       view.getImportRightCharButton().setOnAction(actionEvent -> importRightModelEvent());
       view.getFlipButton().setOnAction(event -> flipCharacterEvent());
       view.getGenderSwapButton().setOnAction(actionEvent -> genderSwapEvent());
       view.getChangeSkinToneButton().setOnAction(actionEvent -> changeSkinToneEvent());
       view.getChangeHairColorButton().setOnAction(actionEvent -> changeHairColorEvent());
       view.getChangeLipsColorButton().setOnAction(actionEvent -> changeLipsColorEvent());
       view.getAddSpeechBubbleButton().setOnAction(actionEvent -> addSpeechBubbleEvent());
       view.getAddThoughtBubbleButton().setOnAction(actionEvent -> addThoughtBubbleEvent());
       view.getRemoveBubbleButton().setOnAction(actionEvent -> removeBubbleEvent());
       view.getAddTextTopButton().setOnAction(actionEvent -> addNarrativeTextTopEvent());
       view.getAddTextBottomButton().setOnAction(actionEvent -> addNarrativeTextBottomEvent());
       view.getFileMenuCharactersDir().setOnAction(actionEvent -> openCharacterDirectory());
       view.getPanelMenuSave().setOnAction(actionEvent -> savePanelEvent());
       view.getPanelMenuNew().setOnAction(event -> newPanelEvent());
       view.getPanelMenuDelete().setOnAction(event -> deletePanelEvent());
       view.getHelpPage().setOnAction(event -> helpPage());
       view.getHelpStartedPage().setOnAction(event -> gettingStarted());
       view.getAboutPage().setOnAction(event -> aboutPage());
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
           String pose = imageFile.getName();
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
           String pose = imageFile.getName();
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
           notSelectedMsg();
       }
   }

   private void genderSwapEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           comixApp.getSelectedCharacter().switchGenders();
           Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           notSelectedMsg();
       }
   }

   private void changeSkinToneEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           comixApp.getSelectedCharacter().skinChange(view.getSelectedColor());
           Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
           view.getSelectedCharacterView().setImage(newCharImage);
       }
       else{
           notSelectedMsg();
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
           notSelectedMsg();
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
           notSelectedMsg();
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
           notSelectedMsg();
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
           notSelectedMsg();
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
           notSelectedMsg();
       }
   }

   private void addNarrativeTextTopEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           String narrativeText = view.addNarrativeTextTop();
           if(narrativeText != null){
               comixApp.setNarrativeTextTop(narrativeText);
               view.getTopNarrativeText().setText(narrativeText);
           }
       }
       else{
           notSelectedMsg();
       }
   }

   private void addNarrativeTextBottomEvent(){
       if(view.isCharacterSelected() && comixApp.isCharacterSelected()){
           String narrativeText = view.addNarrativeTextBottom();
           if(narrativeText != null){
               comixApp.setNarrativeTextBottom(narrativeText);
               view.getBottomNarrativeText().setText(narrativeText);
           }
       }
       else{
           notSelectedMsg();
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
           System.out.println("You need to have the two characters imported in order to save the panel");
           view.userInformationAlert("You need to have the two characters imported in order to save the panel");
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
           System.out.println("View Panel ID: " + panel.getPanelId());
            loadSelectedPanel(panel.getPanelId());
           mouseEvent.consume();
       });
   }

   private void loadSelectedPanel(int id){
       boolean loaded = comixApp.loadSelectedPanel(id);

       if(loaded){
           Character leftChar = comixApp.getCharacterLeft();
           Character rightChar = comixApp.getCharacterRight();
           System.out.println("Base Panel ID: " + comixApp.getId());

           //parameters: Image leftCharacter, Image rightCharacter, BubbleType leftBubbleType, BubbleType rightBubbleType,
           //String leftBubbleText, String rightBubbleText, String topNarrativeText, String bottomNarrativeText
           view.loadSelectedPanel(leftChar.getCharacterImage(), rightChar.getCharacterImage(), leftChar.getBubbleType(),
                   rightChar.getBubbleType(), leftChar.getBubbleText(), rightChar.getBubbleText(),
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

   private void notSelectedMsg(){
       System.out.println("Select one of the characters on which you want to perform the operation");
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
}
