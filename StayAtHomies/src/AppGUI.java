import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

//AppGUI.java represents the View following the MVC pattern
public class AppGUI
{
    private static final double SCENE_WIDTH = 700;
    private static final double SCENE_HEIGHT = 850;
    private final double WORKING_PANE_WIDTH = 610;
    private final double WORKING_PANE_HEIGHT = 600;
    private final double BUBBLE_WIDTH = 290;
    private final double BUBBLE_HEIGHT = 220;
    private final double CHARACTER_VIEW_SIZE = 300;
    private final Image THOUGHT_BUBBLE_IMAGE = new Image("/resources/thoughtBubble.png");
    private final Image SPEECH_BUBBLE_IMAGE = new Image("/resources/speechBubble.png");

    private Stage stage;
    private Scene scene;
    private BorderPane layout;
    private GridPane mainPane = new GridPane();
    private ImageView leftBubble = new ImageView();
    private ImageView rightBubble = new ImageView();
    private ImageView leftCharView = new ImageView();
    private ImageView rightCharView = new ImageView();
    private Label leftBubbleText = new Label();
    private Label rightBubbleText = new Label();
    private Label topNarrativeText = new Label();
    private Label bottomNarrativeText = new Label();
    private ImageView selectedCharacterView = null;
    private Color selectedColor = Color.WHITE;
    ImageView panelOne = new ImageView();

    //SIDE BUTTONS
    private ColorPicker colorPalette;
    private Button importLeftCharButton;
    private Button importRightCharButton;
    private Button flipButton;
    private Button genderSwapButton;
    private Button changeSkinToneButton;
    private Button changeHairColorButton;
    private Button changeLipsColorButton;
    private Button addSpeechBubbleButton;
    private Button addThoughtBubbleButton;
    private Button removeBubbleButton;
    private Button addTextTopButton;
    private Button addTextBottomButton;
    private Button panelSave;

    public AppGUI(Stage stage){
        this.stage = stage;
    }

    public void createUI()
    {
        layout = new BorderPane();
        createTopMenuBar();
        createButtons();
        createMainPane();
        createBottomPane();
        scene = new Scene(layout);
        stage.setScene(scene);
        stage.setMinWidth(SCENE_WIDTH);
        stage.setMinHeight(SCENE_HEIGHT);
        stage.setTitle("HomiesComix");
        stage.show();
    }

    public void createMainPane()
    {
        leftBubble.setFitHeight(BUBBLE_HEIGHT);
        leftBubble.setFitWidth(BUBBLE_WIDTH);

        rightBubble.setFitHeight(BUBBLE_HEIGHT);
        rightBubble.setFitWidth(BUBBLE_WIDTH);

        leftCharView.setFitHeight(CHARACTER_VIEW_SIZE);
        leftCharView.setFitWidth(CHARACTER_VIEW_SIZE);
        leftCharView.setPreserveRatio(true);
        leftCharView.setStyle("-fx-cursor: hand");

        rightCharView.setFitHeight(CHARACTER_VIEW_SIZE);
        rightCharView.setFitWidth(CHARACTER_VIEW_SIZE);
        rightCharView.setPreserveRatio(true);
        rightCharView.setStyle("-fx-cursor: hand");

        //Stack pane wrapper for the bubble and text
        StackPane leftBubbleWrapper = new StackPane(leftBubble, leftBubbleText);
        StackPane rightBubbleWrapper = new StackPane(rightBubble, rightBubbleText);

        leftBubbleWrapper.setMaxSize(300, 220);
        leftBubbleWrapper.setAlignment(Pos.TOP_CENTER);
        rightBubbleWrapper.setMaxSize(300, 220);
        rightBubbleWrapper.setAlignment(Pos.TOP_CENTER);

        StackPane.setMargin(leftBubbleText, new Insets(15, 0, 0,0));
        StackPane.setMargin(rightBubbleText, new Insets(15, 0, 0,0));

        bubbleTextStyle(leftBubbleText);
        bubbleTextStyle(rightBubbleText);

        //(Node, colIndex, rowIndex, colSpan, rowSpan)
        mainPane.add(leftBubbleWrapper, 0,1,1,1);
        mainPane.add(rightBubbleWrapper, 2,1,1,1);
        mainPane.add(leftCharView, 0, 2, 1, 1);
        mainPane.add(rightCharView, 2, 2, 1, 1);
        mainPane.add(topNarrativeText, 0, 0, 3, 1);
        mainPane.add(bottomNarrativeText, 0, 3, 3, 1);

        narrativeTextStyle(topNarrativeText);
        narrativeTextStyle(bottomNarrativeText);

        GridPane.setValignment(leftBubbleWrapper, VPos.BOTTOM);
        GridPane.setValignment(rightBubbleWrapper, VPos.BOTTOM);

        mainPane.setStyle("-fx-background-color: white");
        mainPane.setPrefSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setMaxSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setHgap(5);
        //mainPane.setGridLinesVisible(true);

        //size for each row and col
        //first & last row: height= 40 & width= gridspan
        //second row height 220 and width 300
        //third row size is 300 x 300
        RowConstraints row0 = new RowConstraints();
        row0.setMaxHeight(40);
        row0.setMinHeight(40);
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(220);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(300);
        RowConstraints row3 = new RowConstraints();
        row3.setMaxHeight(40);
        row3.setMinHeight(40);
        mainPane.getRowConstraints().addAll(row0, row1, row2, row3);

        BorderPane.setAlignment(mainPane, Pos.CENTER);
        BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        layout.setCenter(mainPane);
    }

    public void createBottomPane()
    {
        //Hbox
        HBox bottomPanelsWrapper = new HBox();
        bottomPanelsWrapper.setSpacing(15);
        bottomPanelsWrapper.setPadding(new Insets(5, 5, 5, 5));
        bottomPanelsWrapper.setStyle("-fx-background-color: #103859; -fx-border-color: #d4d4d4");
        bottomPanelsWrapper.setMinHeight(160);

        panelOne.setStyle("-fx-border-color: black ;");
        panelOne.setFitHeight(150);
        panelOne.setFitWidth(150);

        ListView panelTwo = new ListView();
        panelTwo.setStyle("-fx-border-color: black ;");
        panelTwo.setPrefWidth(150);
        panelTwo.setPrefHeight(150);

        ListView panelThree = new ListView();
        panelThree.setStyle("-fx-border-color: black ;");
        panelThree.setPrefWidth(150);
        panelThree.setPrefHeight(150);

        bottomPanelsWrapper.getChildren().addAll(panelOne, panelTwo, panelThree);
        layout.setBottom(bottomPanelsWrapper);
    }
    public void createTopMenuBar()
    {
        //Creating the Top Menu bar (File, View, Configure)
        Menu fileTopBar = new Menu("File");
        Menu viewTopBar = new Menu("View");
        Menu configureTopBar = new Menu("Configure");

        fileTopBar.getItems().add(new MenuItem("FileOption1"));
        fileTopBar.getItems().add(new MenuItem("FileOption2"));
        fileTopBar.getItems().add(new MenuItem("FileOption3"));

        viewTopBar.getItems().add(new MenuItem("ViewOption1"));
        viewTopBar.getItems().add(new MenuItem("ViewOption2"));
        viewTopBar.getItems().add(new MenuItem("ViewOption3"));

        configureTopBar.getItems().add(new MenuItem("ConfigureOption1"));
        configureTopBar.getItems().add(new MenuItem("ConfigureOption2"));
        configureTopBar.getItems().add(new MenuItem("ConfigureOption3"));

        MenuBar topMenu = new MenuBar();
        topMenu.getMenus().addAll(fileTopBar, viewTopBar, configureTopBar);

        layout.setTop(topMenu);
    }

    private void createButtons()    {
        //VBox within a BorderPane within another BorderPane
        VBox leftBarButtonsWrapper = new VBox();
        leftBarButtonsWrapper.setMinWidth(50);
        leftBarButtonsWrapper.setPrefWidth(50);
        leftBarButtonsWrapper.setSpacing(5);
        leftBarButtonsWrapper.setAlignment(Pos.TOP_CENTER);
        leftBarButtonsWrapper.setPadding(new Insets(5, 5 ,5, 5));
        leftBarButtonsWrapper.setStyle("-fx-background-color: #103859");

        ScrollPane scrollPane = new ScrollPane(leftBarButtonsWrapper);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);

        importLeftCharButton = new Button();
        buttonCommonStyles(importLeftCharButton);
        importLeftCharButton.setGraphic(setButtonImg(40, "importLeftChar.png"));

        importRightCharButton = new Button();
        buttonCommonStyles(importRightCharButton);
        importRightCharButton.setGraphic(setButtonImg(40, "importRightChar.png"));

        flipButton = new Button();
        buttonCommonStyles(flipButton);
        flipButton.setGraphic(setButtonImg(40, "flip.png"));

        colorPalette = new ColorPicker();
        colorPalette.setMinHeight(40);
        colorPalette.setMinWidth(40);
        colorPalette.setStyle("-fx-background-color: rgba(0, 0, 0, 1); -fx-background-radius: 2; -fx-cursor: hand");
        colorPalette.setOnAction(event ->{
            selectedColor = colorPalette.getValue();
        });

        genderSwapButton = new Button();
        buttonCommonStyles(genderSwapButton);
        genderSwapButton.setGraphic(setButtonImg(40, "changeGender.png"));

        changeSkinToneButton = new Button();
        buttonCommonStyles(changeSkinToneButton);
        changeSkinToneButton.setGraphic(setButtonImg(40, "bodyColor.png"));

        changeHairColorButton = new Button();
        buttonCommonStyles(changeHairColorButton);
        changeHairColorButton.setGraphic(setButtonImg(40, "hairColor.png"));

        changeLipsColorButton = new Button();
        buttonCommonStyles(changeLipsColorButton);
        changeLipsColorButton.setGraphic(setButtonImg(40, "lipsColor.png"));

        addSpeechBubbleButton = new Button();
        buttonCommonStyles(addSpeechBubbleButton);
        addSpeechBubbleButton.setGraphic(setButtonImg(40, "speechBubbleButton.png"));

        addThoughtBubbleButton = new Button();
        buttonCommonStyles(addThoughtBubbleButton);
        addThoughtBubbleButton.setGraphic(setButtonImg(40, "thoughtBubbleButton.png"));

        removeBubbleButton = new Button();
        buttonCommonStyles(removeBubbleButton);
        removeBubbleButton.setGraphic(setButtonImg(40, "removeBubbleButton.png"));

        addTextTopButton = new Button();
        buttonCommonStyles(addTextTopButton);
        addTextTopButton.setGraphic(setButtonImg(40, "narrativeTextTop.png"));

        addTextBottomButton = new Button();
        buttonCommonStyles(addTextBottomButton);
        addTextBottomButton.setGraphic(setButtonImg(40, "narrativeTextBottom.png"));

        panelSave = new Button();
        buttonCommonStyles(panelSave);
        panelSave.setGraphic(setButtonImg(40, "panelSave.png"));
        panelSave.setOnAction(actionEvent -> {
            WritableImage image = mainPane.snapshot(new SnapshotParameters(), null);
            //bottom1.setImage(image);
            //createNewPanel(characterLeft,characterRight,leftBubbleText,rightBubbleText,narrativeText);
        });

        leftBarButtonsWrapper.getChildren().addAll(colorPalette, importLeftCharButton, importRightCharButton, flipButton, genderSwapButton, changeSkinToneButton, changeHairColorButton,
                changeLipsColorButton, addSpeechBubbleButton, addThoughtBubbleButton, removeBubbleButton, addTextTopButton, addTextBottomButton, panelSave);

        layout.setLeft(scrollPane);
    }

    public void setSelectingHandler(EventHandler<MouseEvent> leftViewEvent, EventHandler<MouseEvent> rightViewEvent){
        leftCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, leftViewEvent);
        rightCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, rightViewEvent);
    }

    public Image importModel()    //Uploads character to workspace pane
    {
        File defaultDir = new File("./images");
        if(!defaultDir.exists()){
            defaultDir = new File("./");
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            Image image = new Image(file.toURI().toString());   //image
            return image;
        }
        return null;
    }

    private ImageView setButtonImg(int size, String filename){
        ImageView imgV = new ImageView("/resources/"+filename);
        imgV.setFitHeight(size);
        imgV.setFitWidth(size);
        return imgV;
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand");
    }

    // sets the given character view and character to currently selected
    public void selectFrame(Selected select){
        if(selectedCharacterView != null){
            selectedCharacterView.setEffect(null);
        }
        if(select == Selected.LEFT){
            selectedCharacterView = leftCharView;
        }
        else{
            selectedCharacterView = rightCharView;
        }
        selectedCharacterView.setEffect(new DropShadow(10, Color.BLACK));

        if(selectedCharacterView == leftCharView){
            System.out.println("LEFT CHARACTER SELECTED");
        }
        else if(selectedCharacterView == rightCharView){
            System.out.println("RIGHT CHARACTER SELECTED");
        }
    }

    private String importBubble(Image bubbleImage){
        ImageView bubble = new ImageView(bubbleImage);
        bubble.setFitWidth(30);
        bubble.setFitHeight(30);
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Bubble Text");
        textInput.setGraphic(bubble);
        textInput.setHeaderText("Enter bubble text...");
        textInput.showAndWait();

        if(textInput.getResult() != null){
            String text = textInput.getResult();

            //if the entered string is longer than 60 characters, get only the first 60 chars
            text = (text.length() < 120 ? text : text.substring(0 , 120));
            if(selectedCharacterView == leftCharView){
                leftBubble.setImage(bubble.getImage());
                leftBubbleText.setText(text);
            }
            else if(selectedCharacterView == rightCharView){
                rightBubble.setImage(bubble.getImage());
                rightBubbleText.setText(text);
            }
            return text;
        }
        return null;
    }
    public String importSpeechBubble(){
        return importBubble(SPEECH_BUBBLE_IMAGE);
    }

    public String importThoughtBubble(){
        return importBubble(THOUGHT_BUBBLE_IMAGE);
    }

    private void bubbleTextStyle(Label text){
        text.setAlignment(Pos.CENTER);
        text.setWrapText(true);
        text.setPrefSize(250, 150);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Arial", 18));
    }

    private String addNarrativeText(String position) {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Narrative Text");
        textInput.setHeaderText("Enter narrative text...");
        textInput.showAndWait();

        if(textInput.getResult() != null){
            String text = textInput.getResult();
            //text = (text.length() < 150 ? text : text.substring(0 , 150))

            //if the given possition is bottom the the narrative text is placed at the bottom
            if(position.toUpperCase() == "BOTTOM"){
                bottomNarrativeText.setText(text);
            }
            else{
                topNarrativeText.setText(text);
            }
            return text;
        }
        return null;
    }

    public String addNarrativeTextTop(){
        return addNarrativeText("TOP");
    }

    public String addNarrativeTextBottom(){
        return addNarrativeText("BOTTOM");
    }

    private void narrativeTextStyle(Label narrativeText){
        narrativeText.setAlignment(Pos.CENTER);
        narrativeText.setWrapText(true);
        narrativeText.setPrefSize(WORKING_PANE_WIDTH, 40);
        narrativeText.setFont(Font.font("Arial", 18));
    }

    private void createNewPanel(Character left, Character right, Label leftBubbleText, Label rightBubbleText, Label narrativeText)
    {
        Panels newPanel = new Panels();
        newPanel.setLeft(left);
        newPanel.setRight(right);
        newPanel.setLeftBubbleText(leftBubbleText.getText());
        newPanel.setRightBubbleText(rightBubbleText.getText());
        newPanel.setNarratorText(narrativeText.getText());
        addPanelToList(newPanel);
    }
    private void addPanelToList(Panels panel)
    {
        //panelList.add(panel);
        System.out.println("PANEL SAVED");
    }

    public ImageView getLeftBubble() {
        return leftBubble;
    }

    public ImageView getRightBubble() {
        return rightBubble;
    }

    public ImageView getLeftCharView() {
        return leftCharView;
    }

    public ImageView getRightCharView() {
        return rightCharView;
    }

    public ImageView getSelectedCharacterView() {
        return selectedCharacterView;
    }

    public Label getLeftBubbleText() {
        return leftBubbleText;
    }

    public Label getRightBubbleText() {
        return rightBubbleText;
    }

    public Label getTopNarrativeText() {
        return topNarrativeText;
    }

    public Label getBottomNarrativeText() {
        return bottomNarrativeText;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Button getImportLeftCharButton() {
        return importLeftCharButton;
    }

    public Button getImportRightCharButton() {
        return importRightCharButton;
    }

    public Button getFlipButton() {
        return flipButton;
    }

    public Button getGenderSwapButton() {
        return genderSwapButton;
    }

    public Button getChangeSkinToneButton() {
        return changeSkinToneButton;
    }

    public Button getChangeHairColorButton() {
        return changeHairColorButton;
    }

    public Button getChangeLipsColorButton() {
        return changeLipsColorButton;
    }

    public Button getAddSpeechBubbleButton() {
        return addSpeechBubbleButton;
    }

    public Button getAddThoughtBubbleButton() {
        return addThoughtBubbleButton;
    }

    public Button getRemoveBubbleButton() {
        return removeBubbleButton;
    }

    public Button getAddTextTopButton() {
        return addTextTopButton;
    }

    public Button getAddTextBottomButton() {
        return addTextBottomButton;
    }

    public Button getPanelSave() {
        return panelSave;
    }

    public boolean isSelected(){
        return selectedCharacterView != null;
    }
}

