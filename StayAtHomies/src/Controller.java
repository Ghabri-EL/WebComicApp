import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

//Controller.java represents the Controller following the MVC pattern
public class Controller {
   private ComixApp comixApp;
   private AppGUI view;

   public Controller(ComixApp comixApp, AppGUI view){
       this.comixApp = comixApp;
       this.view = view;
   }
   public void execution(){
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

       view.getImportLeftCharButton().setOnAction(actionEvent -> {
           Image charImage = view.importModel();
           if(charImage != null){
               Character character = new Character(charImage);
               comixApp.setCharacterLeft(character);
               view.getLeftCharView().setImage(character.getCharacterImage());

               //if the selected character was the left character and a new one was imported
               //then set the new model to selected
               if(comixApp.isCharacterSelected()){
                   if(comixApp.getSelectedCharacter() != comixApp.getCharacterRight()){
                       comixApp.setSelectedCharacter(character);
                   }
               }
           }
       });

       view.getImportRightCharButton().setOnAction(actionEvent -> {
           Image charImage = view.importModel();
           if(charImage != null){
               Character character = new Character(charImage);
               comixApp.setCharacterRight(character);
               view.getRightCharView().setImage(character.getCharacterImage());

               if(comixApp.isCharacterSelected()){
                   if(comixApp.getSelectedCharacter() != comixApp.getCharacterLeft()){
                       comixApp.setSelectedCharacter(character);
                   }
               }
           }
       });

       view.getFlipButton().setOnAction(event ->{
           if(view.isSelected() && comixApp.isCharacterSelected()){
               comixApp.getSelectedCharacter().flipImage();
               Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
               view.getSelectedCharacterView().setImage(newCharImage);
           }
           else{
               notSelectedMsg();
           }
       });

       view.getGenderSwapButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               comixApp.getSelectedCharacter().switchGenders();
               Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
               view.getSelectedCharacterView().setImage(newCharImage);
           }
           else{
               notSelectedMsg();
           }
       });

       view.getChangeSkinToneButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               comixApp.getSelectedCharacter().skinChange(view.getSelectedColor());
               Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
               view.getSelectedCharacterView().setImage(newCharImage);
           }
           else{
               notSelectedMsg();
           }
       });

       view.getChangeHairColorButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               comixApp.getSelectedCharacter().hairChange(view.getSelectedColor());
               Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
               view.getSelectedCharacterView().setImage(newCharImage);
           }
           else{
               notSelectedMsg();
           }
       });

       view.getChangeLipsColorButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               comixApp.getSelectedCharacter().changeLipsColor(view.getSelectedColor());
               Image newCharImage = comixApp.getSelectedCharacter().getCharacterImage();
               view.getSelectedCharacterView().setImage(newCharImage);
           }
           else{
               notSelectedMsg();
           }
       });

       view.getAddSpeechBubbleButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               String bubbleText;
               bubbleText = view.importSpeechBubble();
               if(bubbleText != null){
                   comixApp.getSelectedCharacter().setBubbleText(bubbleText);
               }
           }
           else{
               notSelectedMsg();
           }
       });

       view.getAddThoughtBubbleButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               String bubbleText;
               bubbleText = view.importThoughtBubble();
               if(bubbleText != null){
                   comixApp.getSelectedCharacter().setBubbleText(bubbleText);
               }
           }
           else{
               notSelectedMsg();
           }
       });

       view.getRemoveBubbleButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               if(view.getSelectedCharacterView() == view.getLeftCharView()){
                   view.getLeftBubble().setImage(null);
                   view.getLeftBubbleText().setText("");
               }
               else{
                   view.getRightBubble().setImage(null);
                   view.getRightBubbleText().setText("");
               }
               comixApp.getSelectedCharacter().setBubbleText("");
           }
           else{
               notSelectedMsg();
           }
       });

       view.getAddTextTopButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               String narrativeText = view.addNarrativeTextTop();
               if(narrativeText != null){
                   comixApp.setNarrativeTextTop(narrativeText);
               }
           }
           else{
               notSelectedMsg();
           }
       });

       view.getAddTextBottomButton().setOnAction(actionEvent -> {
           if(view.isSelected() && comixApp.isCharacterSelected()){
               String narrativeText = view.addNarrativeTextBottom();
               if(narrativeText != null){
                   comixApp.setNarrativeTextBottom(narrativeText);
               }
           }
           else{
               notSelectedMsg();
           }
       });

       view.getPanelSave().setOnAction(actionEvent -> {
//           WritableImage image = mainPane.snapshot(new SnapshotParameters(), null);
//           bottom1.setImage(image);
//           view.createNewPanel(characterLeft,characterRight,leftBubbleText,rightBubbleText,narrativeTextTop, narrativeTextBottom);
       });
   }

   private void notSelectedMsg(){
       System.out.println("Select one of the characters on which you want to perform the operation");
   }
}
