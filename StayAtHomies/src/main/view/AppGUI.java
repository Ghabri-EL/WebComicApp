package main.view;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.model.NarrativeText;
import main.project_enums.BubbleType;
import main.project_enums.NarrativeTextWrap;
import main.project_enums.Selected;
import java.io.File;
import java.util.ArrayList;

//main.view.AppGUI.java represents the View following the MVC pattern
public class AppGUI implements ViewDefaultValues
{
    private File defaultCharactersDirectory;
    private File defaultHTMLDirectory;

    private Stage stage;
    private Scene scene;
    private BorderPane layout;
    private GridPane mainPane;
    private ImageView leftBubble;
    private ImageView rightBubble;
    private ImageView leftCharView;
    private ImageView rightCharView;
    private Label leftBubbleText;
    private Label rightBubbleText;
    private Text topNarrativeText;
    private Text bottomNarrativeText;
    private ImageView selectedCharacterView;
    private Color selectedColor = Color.WHITE;
    private HBox comicStrip;
    private PanelView selectedPanel;
    private HelpPage helpPageClass;
    private Label panelPosition;

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
    private Button setComicTitle;
    private Button setComicCredits;

    //TOP BAR MENU BUTTONS
    private Menu fileMenu;
    private Menu panelMenu;
    private Menu helpMenu;

    //TOP BAR MENU OPTIONS
    private final MenuItem fileMenuSaveXML = new MenuItem("Save");
    private final MenuItem fileMenuLoadXML = new MenuItem("Load");
    private final MenuItem fileMenuCharactersDir = new MenuItem("Characters Directory");
    private final MenuItem saveAsHtml = new MenuItem("Save as HTML");
    private final MenuItem panelMenuNew = new MenuItem("New");
    private final MenuItem panelMenuSave = new MenuItem("Save");
    private final MenuItem panelMenuDelete = new MenuItem("Delete");
    private final MenuItem help = new MenuItem("Help");
    private final MenuItem about = new MenuItem("About");
    private final MenuItem gettingStarted = new MenuItem("Getting Started");

    //SELECTED PANEL CONTEXT MENU
    private final ContextMenu SELECTED_PANEL_MENU = new ContextMenu();

    //SELECTED PANEL CONTEXT MENU ITEMS
    private final MenuItem SAVE_PANEL = new MenuItem("Save");
    private final MenuItem DELETE_PANEL = new MenuItem("Delete");
    private final MenuItem CHANGE_PANEL_POSITION = new MenuItem("Change panel position");

    //NARRATIVE TEXT CONTEXT MENU (right click menu)
    private final ContextMenu TOP_NARRATIVE_TEXT_MENU = new ContextMenu();
    private final ContextMenu BOTTOM_NARRATIVE_TEXT_MENU = new ContextMenu();

    //NARRATIVE TEXT CONTEXT MENU OPTIONS
    private final MenuItem SINGLE_LINE_OPTION_TOP = new MenuItem("Single line");
    private final MenuItem MULTI_LINES_OPTION_TOP = new MenuItem("Wrap text");
    private final MenuItem SINGLE_LINE_OPTION_BOTTOM = new MenuItem("Single line");
    private final MenuItem MULTI_LINES_OPTION_BOTTOM = new MenuItem("Wrap text");

    //PANEL POSITION BAR
    private Button POSITION_TO_LEFT_BUTTON = new Button();
    private Button POSITION_TO_RIGHT_BUTTON = new Button();

    public AppGUI(Stage stage){
        this.stage = stage;
        this.mainPane = new GridPane();
        this.leftBubble = new ImageView();
        this.rightBubble = new ImageView();
        this.leftCharView = new ImageView();
        this.rightCharView = new ImageView();
        this.leftBubbleText = new Label();
        this.rightBubbleText = new Label();
        this.topNarrativeText = new Text();
        this.bottomNarrativeText = new Text();
        this.selectedCharacterView = null;

        this.defaultCharactersDirectory = new File("./");
        this.defaultHTMLDirectory = new File("./");
    }

    public void createUI()
    {
        layout = new BorderPane();
        layout.setStyle("-fx-background-color: " + APP_THEME_COLOR_SCENE);
        createTopMenuBar();
        createButtons();
        createMainPane();
        createBottomPane();
        //creates the 'right click' menus
        createContextMenu();
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

        StackPane.setMargin(leftBubbleText, new Insets(10, 0, 0,0));
        StackPane.setMargin(rightBubbleText, new Insets(10, 0, 0,0));

        bubbleTextStyle(leftBubbleText);
        bubbleTextStyle(rightBubbleText);
        narrativeTextStyle();

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
        GridPane.setHalignment(topNarrativeText, HPos.CENTER);
        GridPane.setHalignment(bottomNarrativeText, HPos.CENTER);

        mainPane.setStyle("-fx-background-color: white");
        mainPane.setMinSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setMaxSize(WORKING_PANE_WIDTH, WORKING_PANE_HEIGHT);
        mainPane.setHgap(10);

        BorderPane.setAlignment(mainPane, Pos.CENTER);
        BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        layout.setCenter(mainPane);
    }
    public void createBottomPane()
    {
        //Hbox containing the panels(the comic strip)
        comicStrip = new HBox();
        comicStrip.setSpacing(15);
        comicStrip.setPadding(new Insets(5, 5, 5, 5));
        comicStrip.setStyle("-fx-background-color: " + APP_THEME_COLOR + ";");
        comicStrip.setMinHeight(COMIC_STRIP_PANE_HEIGHT);

        //scroll pane wrapper for the comic strip
        ScrollPane scrollPane = new ScrollPane(comicStrip);
        //the value of 15 added to the default height is to account for the scroll bar
        scrollPane.setMinHeight(COMIC_STRIP_PANE_HEIGHT + 15);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: " + APP_THEME_COLOR + ";" +
                "-fx-border-color: rgba(240, 240, 240, 0.2); -fx-border-width: 1 0 0 0");

        //add the scroll pane, containing the comic strip pane, and the bar/label showing
        //the id of a panel into a vbox
        VBox bottomPaneWrapper = new VBox();
        bottomPaneWrapper.setAlignment(Pos.CENTER);
        bottomPaneWrapper.setStyle("-fx-border-color: " + BORDER_COLOR_LIGHT + "; -fx-border-width: 1 0 0 0");

        //contains the buttons and label for the panel position (aka. the panelPositionBar)
        HBox panelPositionBar = new HBox();
        panelPositionBar.setMaxHeight(50);
        panelPositionBar.setAlignment(Pos.CENTER);

        panelPosition = new Label("No Panels");
        panelPosition.setPrefSize(100, 30);
        panelPosition.setAlignment(Pos.CENTER);
        panelPosition.setStyle("-fx-text-fill: white;");

        setPositionButtonsAttributes();
        POSITION_TO_LEFT_BUTTON.setDisable(true);
        POSITION_TO_RIGHT_BUTTON.setDisable(true);

        panelPositionBar.getChildren().addAll(POSITION_TO_LEFT_BUTTON, panelPosition, POSITION_TO_RIGHT_BUTTON);
        bottomPaneWrapper.getChildren().addAll(panelPositionBar,scrollPane);
        layout.setBottom(bottomPaneWrapper);
    }

    public void createTopMenuBar()
    {
        //Creating the Top Menu bar (File, View, Panel)
        fileMenu = new Menu("File");
        panelMenu = new Menu("Panel");
        helpMenu = new Menu("Help");

        fileMenu.getItems().add(fileMenuLoadXML);
        fileMenu.getItems().add(fileMenuSaveXML);
        fileMenu.getItems().add(fileMenuCharactersDir);
        fileMenu.getItems().add(saveAsHtml);

        panelMenu.getItems().add(panelMenuNew);
        panelMenu.getItems().add(panelMenuSave);
        panelMenu.getItems().add(panelMenuDelete);

        helpMenu.getItems().add(help);
        helpMenu.getItems().add(gettingStarted);
        helpMenu.getItems().add(about);

        MenuBar topMenuBar = new MenuBar();
        topMenuBar.getMenus().addAll(fileMenu, panelMenu, helpMenu);

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
        leftBarButtonsWrapper.setStyle("-fx-background-color: " + APP_THEME_COLOR + ";");


        ScrollPane scrollPane = new ScrollPane(leftBarButtonsWrapper);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: " + APP_THEME_COLOR);

        VBox leftButtonsPaneWrapper = new VBox();
        leftButtonsPaneWrapper.setStyle("-fx-background-color: " + APP_THEME_COLOR + ";" +
                "-fx-border-color: " + BORDER_COLOR_DARK + "; -fx-border-width: 0 1 0 0");

        importLeftCharButton = new Button("Import Left", setButtonImg( "importLeftChar.png"));
        buttonCommonStyles(importLeftCharButton);

        importRightCharButton = new Button("Import Right", setButtonImg( "importRightChar.png"));
        buttonCommonStyles(importRightCharButton);

        flipButton = new Button("Orientation", setButtonImg("flip.png"));
        buttonCommonStyles(flipButton);

        colorPalette = new ColorPicker();
        colorPalette.setMinHeight(40);
        colorPalette.setMinWidth(LEFT_BUTTONS_PANEL_WIDTH);
        colorPalette.setStyle("-fx-background-color: rgb(127, 118, 85);" + "-fx-background-radius: 1; -fx-cursor: hand");
        colorPalette.setOnAction(event ->{
            selectedColor = colorPalette.getValue();
        });

        genderSwapButton = new Button("Appearance", setButtonImg( "changeGender.png"));
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
        addTextTopButton.setContextMenu(TOP_NARRATIVE_TEXT_MENU);

        addTextBottomButton = new Button("Bottom Narration", setButtonImg("narrativeTextBottom.png"));
        buttonCommonStyles(addTextBottomButton);
        addTextBottomButton.setContextMenu(BOTTOM_NARRATIVE_TEXT_MENU);

        setComicTitle = new Button("Comic Title", setButtonImg("comicTitleButton.png"));
        buttonCommonStyles(setComicTitle);

        setComicCredits = new Button("Comic Credits", setButtonImg("comicCreditsButton.png"));
        buttonCommonStyles(setComicCredits);

        leftBarButtonsWrapper.getChildren().addAll(importLeftCharButton, importRightCharButton, flipButton, genderSwapButton, changeSkinToneButton, changeHairColorButton,
                changeLipsColorButton, addSpeechBubbleButton, addThoughtBubbleButton, removeBubbleButton, addTextTopButton, addTextBottomButton, setComicTitle, setComicCredits);
        leftButtonsPaneWrapper.getChildren().addAll(colorPalette, scrollPane);

        layout.setLeft(leftButtonsPaneWrapper);
    }

    public void createRightPaneHelp() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("HELP"));
        stage.setWidth(SCENE_WIDTH);
    }

    public void createRightPaneGS() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("STARTED"));
        stage.setWidth(SCENE_WIDTH);
    }

    public void createRightPaneAbout() {
        helpPageClass = new HelpPage();
        layout.setRight(helpPageClass.helpPage("ABOUT"));
        stage.setWidth(SCENE_WIDTH);
    }

    private void createContextMenu(){
        SELECTED_PANEL_MENU.getItems().addAll(SAVE_PANEL, DELETE_PANEL, CHANGE_PANEL_POSITION);
        TOP_NARRATIVE_TEXT_MENU.getItems().addAll(SINGLE_LINE_OPTION_TOP, MULTI_LINES_OPTION_TOP);
        BOTTOM_NARRATIVE_TEXT_MENU.getItems().addAll(SINGLE_LINE_OPTION_BOTTOM, MULTI_LINES_OPTION_BOTTOM);
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1;"+
                "-fx-text-fill: rgb(237, 237, 237); -fx-font-weight: bold; -fx-padding: 10");
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setGraphicTextGap(15);
        btn.prefWidthProperty().setValue(LEFT_BUTTONS_PANEL_WIDTH);
        btn.setOnMouseEntered(mouseEvent ->{
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1 50 50 1;"+
                    "-fx-text-fill: rgb(237, 237, 237); -fx-font-weight: bold; -fx-padding: 10");
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-font-size: 16px;-fx-cursor: hand; -fx-background-radius: 1;"+
                    "-fx-text-fill: rgb(237, 237, 237); -fx-font-weight: bold; -fx-padding: 10");
        });
    }

    private void setPositionButtonsAttributes(){
        ImageView leftButtonGraphic = new ImageView("/resources/positionPanelLeft.png");
        leftButtonGraphic.setFitHeight(20);
        leftButtonGraphic.setPreserveRatio(true);
        POSITION_TO_LEFT_BUTTON.setGraphic(leftButtonGraphic);
        POSITION_TO_LEFT_BUTTON.setStyle("-fx-background-color: transparent; -fx-cursor: hand");
        POSITION_TO_LEFT_BUTTON.setOnMouseEntered(mouseEvent -> {
            POSITION_TO_LEFT_BUTTON.setEffect(new DropShadow(5, Color.TURQUOISE));
        });
        POSITION_TO_LEFT_BUTTON.setOnMouseExited(mouseEvent -> {
            POSITION_TO_LEFT_BUTTON.setEffect(null);
        });

        ImageView rightButtonGraphic = new ImageView("/resources/positionPanelRight.png");
        rightButtonGraphic.setFitHeight(20);
        rightButtonGraphic.setPreserveRatio(true);
        POSITION_TO_RIGHT_BUTTON.setGraphic(rightButtonGraphic);
        POSITION_TO_RIGHT_BUTTON.setStyle("-fx-background-color: transparent; -fx-cursor: hand");
        POSITION_TO_RIGHT_BUTTON.setOnMouseEntered(mouseEvent -> {
            POSITION_TO_RIGHT_BUTTON.setEffect(new DropShadow(5, Color.TURQUOISE));
        });
        POSITION_TO_RIGHT_BUTTON.setOnMouseExited(mouseEvent -> {
            POSITION_TO_RIGHT_BUTTON.setEffect(null);
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
        refreshPanelPositionBar();
    }

    //deals with the selection of panels in the strip
    public void selectPanel(int id){
        PanelView panel = (PanelView) comicStrip.getChildren().get(id);
        if(selectedPanel != null){
            selectedPanel.setEffect(null);
        }
        selectedPanel = panel;
        selectedPanel.setEffect(new DropShadow(15, Color.TURQUOISE));
        refreshPanelPositionBar();
    }

    //====> BUBBLE IMPORT METHODS
    private String importBubble(Image bubbleImage){
        ImageView bubble = new ImageView(bubbleImage);
        bubble.setFitWidth(30);
        bubble.setFitHeight(30);
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Bubble Text");
        textInput.setGraphic(bubble);
        textInput.setHeaderText("Enter bubble text. The limit is 250 characters.");
        textInput.showAndWait();

        if(textInput.getResult() != null){
            String text = textInput.getResult();

            //if the entered string is longer than 250 characters, get only the first 250 chars
            text = (text.length() < 250 ? text : text.substring(0 , 250));
            if(selectedCharacterView == leftCharView){
                leftBubble.setImage(bubble.getImage());
                setLeftBubbleText(text);
            }
            else if(selectedCharacterView == rightCharView){
                rightBubble.setImage(bubble.getImage());
                setRightBubbleText(text);
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

    private void setLeftBubbleText(String text) {
        leftBubbleText.setText(text);
        bubbleTextFormatting(leftBubbleText);
    }

    private void setRightBubbleText(String text){
        rightBubbleText.setText(text);
        bubbleTextFormatting(rightBubbleText);
    }

    private void bubbleTextStyle(Label text){
        text.setTextAlignment(TextAlignment.CENTER);
        text.setAlignment(Pos.CENTER);
        text.setMaxSize(250, 150);
        text.setWrapText(true);
        text.setFont(Font.font("Arial"));
    }

    private void bubbleTextFormatting(Label textBox){
        //sets the starting font to 10 then based iteratively increase the font
        //until the height of the text box is greater than 135 px or the font size
        //is greater or equal to 40
        //A temporary Text object is used to preform the calculations, then the correct
        //font size is applied to the bubble text(obj of type label)
        int fontSize = 10;
        Text measurementsTextBox = new Text(textBox.getText());
        measurementsTextBox.setWrappingWidth(240);
        measurementsTextBox.setFont(Font.font(fontSize));

        int maxHeight = 130;
        while(measurementsTextBox.getLayoutBounds().getHeight() < maxHeight && fontSize <= 40){
            measurementsTextBox.setFont(Font.font(fontSize));
            int height = (int)measurementsTextBox.getLayoutBounds().getHeight();
            if(height > maxHeight){
                fontSize--;
                measurementsTextBox.setFont(Font.font(fontSize));
                break;
            }
            fontSize++;
        }
        textBox.setFont(Font.font(fontSize));
    }
    //===> END BUBBLE IMPORT METHODS

    //===> NARRATIVE TEXT METHODS
    private String addNarrativeText() {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Narrative Text");
        textInput.setHeaderText("Enter narrative text. The limit is 300 characters.");
        textInput.showAndWait();

        if(textInput.getResult() != null){
            String text = textInput.getResult();
            //limit the narrative text to 300 characters
            text = (text.length() <= 300 ? text : text.substring(0 , 300));
            System.out.println(text.length());
            return text;
        }
        return null;
    }

    public String addNarrativeTextTop(){
        String text = addNarrativeText();
        if(text != null){
            setNarrativeTextTop(text);
            return text;
        }
        return null;
    }

    public String addNarrativeTextBottom(){
        String text = addNarrativeText();
        if(text != null){
            setNarrativeTextBottom(text);
            return text;
        }
        return null;
    }

    //set text with single line format
    private void setNarrativeTextTop(String text){
        topNarrativeText.setText(text);
        narrativeTextFormat(topNarrativeText);
    }
    //set text with single line format
    private void setNarrativeTextBottom(String text){
        bottomNarrativeText.setText(text);
        narrativeTextFormat(bottomNarrativeText);
    }

    private void narrativeTextStyle(){
        topNarrativeText.setTextAlignment(TextAlignment.CENTER);
        topNarrativeText.setFont(Font.font("Arial"));
        bottomNarrativeText.setTextAlignment(TextAlignment.CENTER);
        bottomNarrativeText.setFont(Font.font("Arial"));
    }

    //formats font size based on width and height in a single line
    public void narrativeTextFormat(Text narrativeText){
        int fontSize = (int)WORKING_PANE_WIDTH / 20;
        int maxWidth = (int)WORKING_PANE_WIDTH - 10;
        narrativeText.setFont(Font.font(fontSize));
        narrativeText.setWrappingWidth(0.0);
        while(narrativeText.getLayoutBounds().getHeight() > 40 || narrativeText.getLayoutBounds().getWidth() > maxWidth){
            fontSize -= 1;
            narrativeText.setFont(Font.font(fontSize));
        }
    }

    //formats font size based on width and height in multiple lines
    public void narrativeTextFormatTextWrapping(Text narrativeText){
        double height = narrativeText.getLayoutBounds().getHeight();
        narrativeText.setWrappingWidth(WORKING_PANE_WIDTH - 10);
        int fontSize = (int)WORKING_PANE_WIDTH / 20;
        narrativeText.setFont(Font.font(fontSize));
        while(narrativeText.getLayoutBounds().getHeight() > 40){
            fontSize -= 1;
            narrativeText.setFont(Font.font(fontSize));
        }
        if(height == narrativeText.getLayoutBounds().getHeight()){
            userInformationAlert("Narrative text", "Wrapping the text will decrease the font size. Text preserved in single line.");
        }
    }
    //END NARRATIVE TEXT METHODS

    public String setComicTitleDialog(){
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Comic Title");
        textInput.setHeaderText("Enter comic title. Max 200 characters.");
        textInput.showAndWait();

        String title = textInput.getResult();
        if(title != null){
            if(title.length() <= 200){
                return title;
            }
            else{
                userErrorAlert("Set title error", "Failed to set comic title. Over 200 characters entered");
            }
        }
        return null;
    }

    public String setComicCreditsDialog() {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Comic Credits");
        textInput.setHeaderText("Enter comic credits. Max 200 characters.");
        textInput.showAndWait();

        String credits = textInput.getResult();
        if(credits != null){
            if(credits.length() <= 200){
                return credits;
            }
            else{
                userErrorAlert("Set credits error", "Failed to set comic credits. Over 200 characters entered");
            }
        }
        return null;
    }

    //===> BOTTOM PANE(comicStrip) METHODS
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

    public PanelView createPanel(){
        Image snapshot = snapshotCurrentPanel();
        PanelView panel = new PanelView(snapshot);
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
                                  String leftBubbleText, String rightBubbleText, NarrativeText topNarrativeText, NarrativeText bottomNarrativeText){
        this.leftCharView.setImage(leftCharacter);
        this.rightCharView.setImage(rightCharacter);
        setLeftBubbleText(leftBubbleText);
        setRightBubbleText(rightBubbleText);

        if(topNarrativeText.getNarrativeTextWrap() == NarrativeTextWrap.WRAP){
            this.topNarrativeText.setText(topNarrativeText.getNarrativeText());
            narrativeTextFormatTextWrapping(this.topNarrativeText);
        }
        else{
            setNarrativeTextTop(topNarrativeText.getNarrativeText());
        }

        if(bottomNarrativeText.getNarrativeTextWrap() == NarrativeTextWrap.WRAP){
            this.bottomNarrativeText.setText(bottomNarrativeText.getNarrativeText());
            narrativeTextFormatTextWrapping(this.bottomNarrativeText);
        }
        else{
            setNarrativeTextBottom(bottomNarrativeText.getNarrativeText());
        }

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

    public boolean confirmChangingPanel() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Change Panel", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Change Panel");
        confirmation.setHeaderText("Unsaved changes will be lost when changing the panel.");
        confirmation.setContentText("Do you wish to save panel before continuing ?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES) {
            return true;
        }
        return false;
    }

    //loads panels based on model
    public void refreshComicStrip(ArrayList<PanelView> panels){
        clearComicStrip();
        for(PanelView panel : panels){
            setPanelAttributes(panel);
            comicStrip.getChildren().add(panel);
        }
        refreshPanelPositionBar();
    }

    public void refreshPanelPositionBar(){
        int numberOfPanels = comicStrip.getChildren().size();
        int panelId = 0;

        if(isPanelSelected()){
            panelId = selectedPanel.getPanelId() + 1;
        }

        String text;
        if(numberOfPanels < 1){
            text = "No panels";
        }
        else{
            text = "Panel " + panelId + " / " + numberOfPanels;
        }
        panelPositionButtonsState();
        panelPosition.setText(text);
    }

    private void panelPositionButtonsState(){
        if(comicStripEmpty() || panelsInStrip() == 1 || !isPanelSelected()){
            POSITION_TO_LEFT_BUTTON.setDisable(true);
            POSITION_TO_RIGHT_BUTTON.setDisable(true);
        }
        else{
            if(isPanelSelected()){
                POSITION_TO_LEFT_BUTTON.setDisable(false);
                POSITION_TO_RIGHT_BUTTON.setDisable(false);
                if(selectedPanel.getPanelId() == 0){
                    POSITION_TO_LEFT_BUTTON.setDisable(true);
                }

                if(selectedPanel.getPanelId() == (panelsInStrip() - 1)){
                    POSITION_TO_RIGHT_BUTTON.setDisable(true);
                }
            }
        }
    }

    public String changePanelIdWindow(){
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Change panel position");
        textInput.setHeaderText("Enter the new position for the selected panel");
        textInput.showAndWait();

        return textInput.getResult();
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
        refreshPanelPositionBar();
    }

    public void clearComicStrip(){
        comicStrip.getChildren().removeAll(comicStrip.getChildren());
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

    public Text getTopNarrativeText() {
        return topNarrativeText;
    }

    public Text getBottomNarrativeText() {
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

    public MenuItem getSingleLineOptionTop() {
        return SINGLE_LINE_OPTION_TOP;
    }

    public MenuItem getMultiLinesOptionTop() {
        return MULTI_LINES_OPTION_TOP;
    }

    public Button getAddTextBottomButton() {
        return addTextBottomButton;
    }

    public MenuItem getSingleLineOptionBottom() {
        return SINGLE_LINE_OPTION_BOTTOM;
    }

    public MenuItem getMultiLinesOptionBottom() {
        return MULTI_LINES_OPTION_BOTTOM;
    }

    public Button getSetComicTitle() {
        return setComicTitle;
    }

    public Button getSetComicCredits() {
        return setComicCredits;
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

    public ContextMenu getSelectedPanelMenu(){
        return SELECTED_PANEL_MENU;
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

    public MenuItem getSavePanel() {
        return SAVE_PANEL;
    }

    public MenuItem getDeletePanel() {
        return DELETE_PANEL;
    }

    public MenuItem getChangePanelPosition() {
        return CHANGE_PANEL_POSITION;
    }

    public Button getPositionToLeft() {
        return POSITION_TO_LEFT_BUTTON;
    }

    public Button getPositionToRight() {
        return POSITION_TO_RIGHT_BUTTON;
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
    private boolean comicStripEmpty(){
        return comicStrip.getChildren().isEmpty();
    }

    private int panelsInStrip(){
        return comicStrip.getChildren().size();
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
        information.showAndWait();
    }

    public void userErrorAlert(String title, String msg){
        Alert information = new Alert(Alert.AlertType.ERROR);
        information.setTitle(title);
        information.setHeaderText(msg);
        information.showAndWait();
    }

    public boolean confirmTitleChange() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Comic Title Change", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Comic Title");
        confirmation.setHeaderText("Comic title is set to default .");
        confirmation.setContentText("Do you wish to change title before continuing ?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    public boolean confirmCreditsChange() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Comic Credits Change", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Comic Credits");
        confirmation.setHeaderText("Comic credits is set to default .");
        confirmation.setContentText("Do you wish to change credits before continuing ?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    public boolean confirmEndPanel() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Comic End Panel", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("End Panel");
        confirmation.setHeaderText("Saving in HTML format without an end panel .");
        confirmation.setContentText("Do you wish to add an End Panel before saving ?");
        confirmation.showAndWait();

        if(confirmation.getResult() == ButtonType.YES){
            return true;
        }
        return false;
    }

    public File getEndPanelWindow(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultCharactersDirectory);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
}

