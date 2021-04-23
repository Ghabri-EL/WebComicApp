import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

//AppGUI.java represents the View following the MVC pattern
public class AppGUI
{
    private static final double SCENE_WIDTH = 900;
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
    private MenuItem fileMenuOne = new MenuItem("Load");
    private MenuItem fileMenuTwo = new MenuItem("Save");
    private MenuItem fileMenuThree = new MenuItem("FileOption");
    private MenuItem viewMenuOne = new MenuItem("Option1");
    private MenuItem viewMenuTwo = new MenuItem("Option2");
    private MenuItem viewMenuThree = new MenuItem("Option3");
    private MenuItem panelMenuSave = new MenuItem("Save");
    private MenuItem panelMenuDelete = new MenuItem("Delete");
    private MenuItem panelMenuNew = new MenuItem("New");
    private MenuItem help = new MenuItem("Help");

    private HelpPage helpPageClass = new HelpPage();

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
        row1.setPrefHeight(BUBBLE_HEIGHT);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(CHARACTER_VIEW_SIZE);
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

        fileMenu.getItems().add(fileMenuOne);
        fileMenu.getItems().add(fileMenuTwo);
        fileMenu.getItems().add(fileMenuThree);

        viewMenu.getItems().add(viewMenuOne);
        viewMenu.getItems().add(viewMenuTwo);
        viewMenu.getItems().add(viewMenuThree);

        panelMenu.getItems().add(panelMenuSave);

        panelMenu.getItems().add(panelMenuDelete);
        panelMenu.getItems().add(panelMenuNew);

        helpMenu.getItems().add(help);

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
        //leftBarButtonsWrapper.setPadding(new Insets(5, 5 ,5, 5));
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

        flipButton = new Button("Change Direction", setButtonImg( "flip.png"));
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

    public void createRightPane() {
//        Pagination pagination = new Pagination(28, 0);
//        pagination.setStyle("fx-border-color:red;");
//        StackPane sp = new StackPane();
//        sp.getChildren().add(pagination);
//        AnchorPane anchor = new AnchorPane();
//        AnchorPane.setTopAnchor(pagination, 10.0);
//        AnchorPane.setRightAnchor(pagination, 10.0);
//        AnchorPane.setBottomAnchor(pagination, 10.0);
//        AnchorPane.setLeftAnchor(pagination, 10.0);
//        anchor.getChildren().addAll(pagination);

        layout.setRight(helpPageClass.helpPage());
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-font-size: 18px;-fx-cursor: hand; -fx-background-radius: 1;"+
                "-fx-text-fill: black");
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.prefWidthProperty().setValue(LEFT_BUTTONS_PANEL_WIDTH);
        btn.setOnMouseEntered(mouseEvent ->{
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-font-size: 18px;-fx-cursor: hand; -fx-background-radius: 1;"+
                    "-fx-text-fill: rgb(229, 235, 195)");
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-font-size: 18px;-fx-cursor: hand; -fx-background-radius: 1;"+
                    "-fx-text-fill: black");
        });
    }

    public File importModel()    //Uploads character to workspace pane
    {
        File defaultDir = new File("./images");
        if(!defaultDir.exists()){
            defaultDir = new File("./");
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultDir);
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
        text.setPrefSize(250, 150);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Arial", 18));
    }
    //===> END BUBBLE IMPORT METHODS

    //===> NARRATIVE TEXT METHODS
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
    //END NARRATIVE TEXT METHODS

    //===> BOTTOM PANE(comixStrip) METHODS
    private Image snapCurrentPanel(){
        WritableImage image;
        if(isCharacterSelected()){
            Effect selectedEffect = selectedCharacterView.getEffect();
            selectedCharacterView.setEffect(null);
            image = mainPane.snapshot(new SnapshotParameters(), null);
            selectedCharacterView.setEffect(selectedEffect);
        }
        else{
             image = mainPane.snapshot(new SnapshotParameters(), null);
        }
        return image;
    }

    public void addPanelToStrip(PanelView panel){
        comixStrip.getChildren().add(panel);
    }

    public PanelView createPanel(){
        Image snapshot = snapCurrentPanel();
        PanelView panel = new PanelView(snapshot);
        setPanelAttributes(panel);
        return panel;
    }

    public PanelView editSelectedPanel(){
        Image snapshot = snapCurrentPanel();
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
    private void recomputeIds(int id){
        ObservableList list = comixStrip.getChildren();

        for(int i = id; i < list.size(); i++){
            PanelView panel = (PanelView) list.get(i);
            panel.setPanelId(panel.getPanelId() - 1);
        }
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

    public MenuItem getFileMenuOne() {
        return fileMenuOne;
    }

    public MenuItem getFileMenuTwo() {
        return fileMenuTwo;
    }

    public MenuItem getFileMenuThree() {
        return fileMenuThree;
    }

    public MenuItem getViewMenuOne() {
        return viewMenuOne;
    }

    public MenuItem getViewMenuTwo() {
        return viewMenuTwo;
    }

    public MenuItem getViewMenuThree() {
        return viewMenuThree;
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

    public MenuItem getHelpMenuPage() {
        return helpMenu;
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
}

