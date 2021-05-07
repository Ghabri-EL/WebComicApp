package main.view;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.project_enums.BubbleType;
import main.project_enums.Selected;
import java.io.File;

//main.view.AppGUI.java represents the View following the MVC pattern
public class AppGUI
{
    private static final double SCENE_WIDTH = 1300;
    private static final double SCENE_HEIGHT = 850;
    private final double WORKING_PANE_WIDTH = 610;
    private final double WORKING_PANE_HEIGHT = 600;
    private final double BUBBLE_WIDTH = 290;
    private final double BUBBLE_HEIGHT = 220;
    private final double CHARACTER_VIEW_SIZE = 300;
    private final double COMIX_STRIP_PANE_HEIGHT = 160;
    private final double PANEL_SIZE = COMIX_STRIP_PANE_HEIGHT - 10;
    private final double LEFT_BUTTONS_PANEL_WIDTH = 200;
    private final String APP_THEME_COLOR = "#103859";
    private final Image THOUGHT_BUBBLE_IMAGE = new Image("/resources/thoughtBubble.png");
    private final Image SPEECH_BUBBLE_IMAGE = new Image("/resources/speechBubble.png");
    private File defaultCharactersDirectory = new File("./");
    private File defaultHTMLDirectory = new File("./");

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
    private HBox comixStrip;
    private PanelView selectedPanel;
    private HelpPage helpPageClass;

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

    //TOP BAR MENU BUTTONS
    private Menu fileMenu;
    private Menu viewMenu;
    private Menu panelMenu;
    private Menu helpMenu;
    private Menu messageMenu;

    //MENU OPTIONS
    private MenuItem fileMenuSaveXML = new MenuItem("Save");
    private MenuItem fileMenuLoadXML = new MenuItem("Load");
    private MenuItem fileMenuCharactersDir = new MenuItem("Characters Directory");
    private MenuItem saveAsHtml = new MenuItem("Save as HTML");
    private MenuItem viewMenuOne = new MenuItem("Option1");
    private MenuItem viewMenuTwo = new MenuItem("Option2");
    private MenuItem viewMenuThree = new MenuItem("Option3");
    private MenuItem panelMenuNew = new MenuItem("New");
    private MenuItem panelMenuSave = new MenuItem("Save");
    private MenuItem panelMenuDelete = new MenuItem("Delete");
    private MenuItem help = new MenuItem("Help");
    private MenuItem about = new MenuItem("About");
    private MenuItem gettingStarted = new MenuItem("Getting Started");

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
        stage.setWidth(SCENE_WIDTH);
        //stage.setHeight(SCENE_HEIGHT);
        //stage.setMaximized(true);
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
        narrativeTextStyle(topNarrativeText);
        narrativeTextStyle(bottomNarrativeText);

        //size for each row and col
        //first & last row: height= 40 & width= gridspan
        //second row height 220 and width 300
        //third row size is 300 x 300
        RowConstraints row0 = new RowConstraints();
        row0.setMaxHeight(40);
        row0.setMinHeight(40);
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(BUBBLE_HEIGHT);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(CHARACTER_VIEW_SIZE);
        RowConstraints row3 = new RowConstraints();
        row3.setMaxHeight(40);
        row3.setMinHeight(40);
        mainPane.getRowConstraints().addAll(row0, row1, row2, row3);

        //(Node, colIndex, rowIndex, colSpan, rowSpan)
        mainPane.add(topNarrativeText, 0, 0, 2, 1);
        mainPane.add(leftBubbleWrapper, 0,1,1,1);
        mainPane.add(rightBubbleWrapper, 1,1,1,1);
        mainPane.add(leftCharView, 0, 2, 1, 1);
        mainPane.add(rightCharView, 1, 2, 1, 1);
        mainPane.add(bottomNarrativeText, 0, 3, 2, 1);

        GridPane.setValignment(leftBubbleWrapper, VPos.BOTTOM);
        GridPane.setValignment(rightBubbleWrapper, VPos.BOTTOM);

        mainPane.setStyle("-fx-background-color: white");
        mainPane.setMinSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setMaxSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setHgap(10);
        //mainPane.setGridLinesVisible(true);

        BorderPane.setAlignment(mainPane, Pos.CENTER);
        BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        layout.setCenter(mainPane);
    }
    public void createBottomPane()
    {
        //Hbox
        comixStrip = new HBox();
        comixStrip.setSpacing(15);
        comixStrip.setPadding(new Insets(5, 5, 5, 5));
        comixStrip.setStyle("-fx-background-color: " + APP_THEME_COLOR + "; -fx-border-color: #d4d4d4");
        comixStrip.setMinHeight(COMIX_STRIP_PANE_HEIGHT);

        ScrollPane scrollPane = new ScrollPane(comixStrip);
        //the value of 15 added to the default height is to account for the scroll bar
        scrollPane.setMinHeight(COMIX_STRIP_PANE_HEIGHT + 15);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);

        layout.setBottom(scrollPane);
    }

    public void createTopMenuBar()
    {
        //Creating the Top Menu bar (File, View, Panel)
        fileMenu = new Menu("File");
        viewMenu = new Menu("View");
        panelMenu = new Menu("Panel");
        helpMenu = new Menu("Help");
        messageMenu = new Menu("Messages");

        fileMenu.getItems().add(fileMenuLoadXML);
        fileMenu.getItems().add(fileMenuSaveXML);
        fileMenu.getItems().add(fileMenuCharactersDir);
        fileMenu.getItems().add(saveAsHtml);

        viewMenu.getItems().add(viewMenuOne);
        viewMenu.getItems().add(viewMenuTwo);
        viewMenu.getItems().add(viewMenuThree);

        panelMenu.getItems().add(panelMenuNew);
        panelMenu.getItems().add(panelMenuSave);
        panelMenu.getItems().add(panelMenuDelete);

        helpMenu.getItems().add(help);
        helpMenu.getItems().add(gettingStarted);
        helpMenu.getItems().add(about);

        MenuBar topMenuBar = new MenuBar();
        topMenuBar.getMenus().addAll(fileMenu, viewMenu, panelMenu, helpMenu, messageMenu);

        layout.setTop(topMenuBar);
    }

    //LEFT SIDE BUTTONS
    private void createButtons()    {
        //VBox within a BorderPane within another BorderPane
        VBox leftBarButtonsWrapper = new VBox();
        leftBarButtonsWrapper.setMinWidth(LEFT_BUTTONS_PANEL_WIDTH);
        leftBarButtonsWrapper.setPrefWidth(LEFT_BUTTONS_PANEL_WIDTH);
        leftBarButtonsWrapper.setSpacing(2);
        leftBarButtonsWrapper.setAlignment(Pos.TOP_CENTER);
        leftBarButtonsWrapper.setStyle("-fx-background-color: #103859");

        ScrollPane scrollPane = new ScrollPane(leftBarButtonsWrapper);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);

        importLeftCharButton = new Button("Import Left", setButtonImg( "importLeftChar.png"));
        buttonCommonStyles(importLeftCharButton);

        importRightCharButton = new Button("Import Right", setButtonImg( "importRightChar.png"));
        buttonCommonStyles(importRightCharButton);

        flipButton = new Button("Orientation", setButtonImg("flip.png"));
        buttonCommonStyles(flipButton);

        colorPalette = new ColorPicker();
        colorPalette.setMinHeight(30);
        colorPalette.setMinWidth(LEFT_BUTTONS_PANEL_WIDTH);
        colorPalette.setStyle("-fx-background-color: rgba(30, 194, 227, 0.5); -fx-background-radius: 1;-fx-highlight-fill: white;-fx-cursor: hand");
        colorPalette.setOnAction(event ->{
            selectedColor = colorPalette.getValue();
        });

        genderSwapButton = new Button("Gender Swap", setButtonImg( "changeGender.png"));
        buttonCommonStyles(genderSwapButton);

        changeSkinToneButton = new Button("Skin Tone", setButtonImg( "bodyColor.png"));
        buttonCommonStyles(changeSkinToneButton);

        changeHairColorButton = new Button("Hair Color", setButtonImg( "hairColor.png"));
        buttonCommonStyles(changeHairColorButton);

        changeLipsColorButton = new Button("Lip Color", setButtonImg( "lipsColor.png"));
        buttonCommonStyles(changeLipsColorButton);

        addSpeechBubbleButton = new Button("Speech Bubble", setButtonImg( "speechBubbleButton.png"));
        buttonCommonStyles(addSpeechBubbleButton);

        addThoughtBubbleButton = new Button("Thought Bubble", setButtonImg( "thoughtBubbleButton.png"));
        buttonCommonStyles(addThoughtBubbleButton);

        removeBubbleButton = new Button("Remove Bubble", setButtonImg( "removeBubbleButton.png"));
        buttonCommonStyles(removeBubbleButton);

        addTextTopButton = new Button("Top Narration", setButtonImg( "narrativeTextTop.png"));
        buttonCommonStyles(addTextTopButton);

        addTextBottomButton = new Button("Bottom Narration", setButtonImg("narrativeTextBottom.png"));
        buttonCommonStyles(addTextBottomButton);

        leftBarButtonsWrapper.getChildren().addAll(colorPalette, importLeftCharButton, importRightCharButton, flipButton, genderSwapButton, changeSkinToneButton, changeHairColorButton,
                changeLipsColorButton, addSpeechBubbleButton, addThoughtBubbleButton, removeBubbleButton, addTextTopButton, addTextBottomButton);

        layout.setLeft(scrollPane);
    }

    public void createRightPaneHelp() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("HELP"));
    }

    public void createRightPaneGS() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("STARTED"));
    }

    public void createRightPaneAbout() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("ABOUT"));
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1;"+
                "-fx-text-fill: rgb(184, 205, 217); -fx-font-weight: bold; -fx-padding: 10");
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setGraphicTextGap(15);
        btn.prefWidthProperty().setValue(LEFT_BUTTONS_PANEL_WIDTH);
        btn.setOnMouseEntered(mouseEvent ->{
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1 50 50 1;"+
                    "-fx-text-fill: rgb(237, 237, 237); -fx-font-weight: bold; -fx-padding: 10");
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1;"+
                    "-fx-text-fill: rgb(184, 205, 217); -fx-font-weight: bold; -fx-padding: 10");
        });
    }

    public void setCharactersDirectory(){
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(defaultCharactersDirectory);
        File dir = dirChooser.showDialog(stage);

        if(dir != null){
            defaultCharactersDirectory = dir;
        }
    }
    public File importModel()    //Uploads character to workspace pane
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultCharactersDirectory);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);

        return file;
    }

    private ImageView setButtonImg(String filename){
        int size = 25;
        ImageView imgV = new ImageView("/resources/"+filename);
        imgV.setFitHeight(size);
        imgV.setFitWidth(size);
        return imgV;
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
    }

    public void setSelectingHandler(EventHandler<MouseEvent> leftViewEvent, EventHandler<MouseEvent> rightViewEvent){
        leftCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, leftViewEvent);
        rightCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, rightViewEvent);
    }

    //deals with the selection of panels in the strip
    public void selectPanel(PanelView panel){
        if(selectedPanel != null){
            selectedPanel.setEffect(null);
        }
        selectedPanel = panel;
        selectedPanel.setEffect(new DropShadow(15, Color.TURQUOISE));
    }

    //====> BUBBLE IMPORT METHODS
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
        text.setMaxSize(250, 150);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Arial", 18));
    }
    //===> END BUBBLE IMPORT METHODS

    //===> NARRATIVE TEXT METHODS
    private String addNarrativeText() {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Narrative Text");
        textInput.setHeaderText("Enter narrative text...");
        textInput.showAndWait();

        if(textInput.getResult() != null){
            String text = textInput.getResult();
            //limit the narrative text to 70 characters
            text = (text.length() <= 70 ? text : text.substring(0 , 70));
            return text;
        }
        return null;
    }

    public String addNarrativeTextTop(){
        return addNarrativeText();
    }

    public String addNarrativeTextBottom(){
        return addNarrativeText();
    }

    private void narrativeTextStyle(Label narrativeText){
        narrativeText.setAlignment(Pos.CENTER);
        narrativeText.setPrefSize(WORKING_PANE_WIDTH, 40);
        narrativeText.setMaxWidth(WORKING_PANE_WIDTH);
        narrativeText.setFont(Font.font("Arial", 18));
    }
    //END NARRATIVE TEXT METHODS

    //===> BOTTOM PANE(comixStrip) METHODS
    private Image snapshotCurrentPanel(){
        WritableImage image = new WritableImage((int)WORKING_PANE_WIDTH, (int)WORKING_PANE_HEIGHT);
        if(isCharacterSelected()){
            Effect selectedEffect = selectedCharacterView.getEffect();
            selectedCharacterView.setEffect(null);
            image = mainPane.snapshot(new SnapshotParameters(), image);
            selectedCharacterView.setEffect(selectedEffect);
        }
        else{
             image = mainPane.snapshot(new SnapshotParameters(), image);
        }
        return image;
    }

    public void addPanelToStrip(PanelView panel){
        comixStrip.getChildren().add(panel);
    }

    public PanelView createPanel(){
        Image snapshot = snapshotCurrentPanel();
        PanelView panel = new PanelView(snapshot);
        setPanelAttributes(panel);
        return panel;
    }

    public PanelView editSelectedPanel(){
        Image snapshot = snapshotCurrentPanel();
        if(isPanelSelected()){
            selectedPanel.setImage(snapshot);
        }
        return selectedPanel;
    }

    private void setPanelAttributes(PanelView panel){
        panel.setStyle("-fx-border-color: black ; -fx-cursor: hand");
        panel.setFitHeight(PANEL_SIZE);
        panel.setFitWidth(PANEL_SIZE);
    }

    public void loadSelectedPanel(Image leftCharacter, Image rightCharacter, BubbleType leftBubbleType, BubbleType rightBubbleType,
                                  String leftBubbleText, String rightBubbleText, String topNarrativeText, String bottomNarrativeText){
        this.leftCharView.setImage(leftCharacter);
        this.rightCharView.setImage(rightCharacter);
        this.leftBubbleText.setText(leftBubbleText);
        this.rightBubbleText.setText(rightBubbleText);
        this.topNarrativeText.setText(topNarrativeText);
        this.bottomNarrativeText.setText(bottomNarrativeText);

        if(leftBubbleType == BubbleType.SPEECH){
            this.leftBubble.setImage(SPEECH_BUBBLE_IMAGE);
        }
        else if(leftBubbleType == BubbleType.THOUGHT){
            this.leftBubble.setImage(THOUGHT_BUBBLE_IMAGE);
        }
        else{
            this.leftBubble.setImage(null);
        }

        if(rightBubbleType == BubbleType.SPEECH){
            this.rightBubble.setImage(SPEECH_BUBBLE_IMAGE);
        }
        else if(rightBubbleType == BubbleType.THOUGHT){
            this.rightBubble.setImage(THOUGHT_BUBBLE_IMAGE);
        }
        else{
            this.rightBubble.setImage(null);
        }
        //reset selected character
        if(isCharacterSelected()){
            selectedCharacterView.setEffect(null);
            selectedCharacterView = null;
        }
    }

    public void resetWorkingPane(){
        leftBubble.setImage(null);
        rightBubble.setImage(null);
        leftCharView.setImage(null);
        rightCharView.setImage(null);
        leftBubbleText.setText(null);
        rightBubbleText.setText(null);
        topNarrativeText.setText(null);
        bottomNarrativeText.setText(null);
        resetSelectedCharacter();
        resetSelectedPanel();
    }

    public boolean confirmWorkingPaneReset(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Create new panel", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("New Panel");
        confirmation.setHeaderText("Creating new panel will reset the current panel. Any unsaved changes will be lost.");
        confirmation.setContentText("Are you sure you want to continue?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    public int deletePanel(){
        if(!isPanelSelected()){
            return -1;
        }
        else{
            int id = selectedPanel.getPanelId();
            comixStrip.getChildren().remove(selectedPanel);
            recomputeIds(id);
            resetWorkingPane();
            return id;
        }
    }

    public boolean confirmDeletePanel(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Create new panel", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Remove Panel");
        confirmation.setHeaderText("Panel removal cannot be undone and the panel cannot be recovered.");
        confirmation.setContentText("Are you sure you want to continue?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    private void recomputeIds(int id){
        ObservableList list = comixStrip.getChildren();

        for(int i = id; i < list.size(); i++){
            PanelView panel = (PanelView) list.get(i);
            panel.setPanelId(panel.getPanelId() - 1);
        }
    }

    public void clearComicStrip(){
        comixStrip.getChildren().removeAll(comixStrip.getChildren());
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

    public MenuItem getFileMenuLoadXML() {
        return fileMenuLoadXML;
    }

    public MenuItem getFileMenuSaveXML() {
        return fileMenuSaveXML;
    }

    public MenuItem getSaveAsHtml() {
        return saveAsHtml;
    }

    public MenuItem getFileMenuCharactersDir() {
        return fileMenuCharactersDir;
    }

    public MenuItem getPanelMenu(){
        return panelMenu;
    }

    public MenuItem getPanelMenuSave() {
        return panelMenuSave;
    }

    public MenuItem getPanelMenuDelete() {
        return panelMenuDelete;
    }

    public MenuItem getPanelMenuNew() {
        return panelMenuNew;
    }

    public MenuItem getHelpPage() {
        return help;
    }

    public MenuItem getHelpStartedPage() {
        return gettingStarted;
    }

    public MenuItem getAboutPage() {
        return about;
    }

    public File getDefaultCharactersDirectory() {
        return defaultCharactersDirectory;
    }

    public File getDefaultHTMLDirectory() {
        return defaultHTMLDirectory;
    }

    public boolean isCharacterSelected(){
        return selectedCharacterView != null;
    }

    public PanelView getSelectedPanel() {
        return selectedPanel;
    }

    public boolean isPanelSelected(){
        return selectedPanel != null;
    }
    private void resetSelectedCharacter(){
        if(isCharacterSelected()){
            selectedCharacterView.setEffect(null);
            selectedCharacterView = null;
        }
    }
    private void resetSelectedPanel(){
        if(isPanelSelected()){
            selectedPanel.setEffect(null);
            selectedPanel = null;
        }
    }

    public File saveXMLFileWindow(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(stage);

        return file;
    }

    public File saveHTMLFileWindow()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files (*.html)","*.html"));
        File file = fileChooser.showSaveDialog(stage);

        return file;
    }
    public File setHTMLDirectory()
    {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(defaultHTMLDirectory);
        File dir = dirChooser.showDialog(stage);

        if(dir != null){
            defaultHTMLDirectory = dir;
        }

        return getDefaultHTMLDirectory();
    }

    //opens up window to let user select the xml file to load
    public File loadXMLFileWindow() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    //loading confirmation alert
    public boolean confirmLoadXML(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Loading new comic", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Loading new comic strip...");
        confirmation.setHeaderText("Current project will be lost if not saved.");
        confirmation.setContentText("Are do you want to continue without saving?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    public void userInformationAlert(String title, String msg){
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        information.setTitle(title);
        information.setHeaderText(msg);
        information.show();
    }

    public void userErrorAlert(String title, String msg){
        Alert information = new Alert(Alert.AlertType.ERROR);
        information.setTitle(title);
        information.setHeaderText(msg);
        information.show();
    }
}

