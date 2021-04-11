import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class createGUI extends Application
{
    private Stage stage;
    private BorderPane layout;
    private Scene scene;
    private ImageView leftBubble = new ImageView();
    private ImageView rightBubble = new ImageView();
    private ImageView leftCharView = new ImageView();
    private ImageView rightCharView = new ImageView();
    private Image thoughtImage = new Image("/resources/thoughtBubble.png");
    private Image speechImage = new Image("/resources/speechBubble.png");
    private Label leftBubbleText = new Label();
    private Label rightBubbleText = new Label();
    private ImageView selectedCharacterView = null;
    private Character selectedCharacter = null;
    Color selectedColor = Color.WHITE;
    Character characterLeft = null;
    Character characterRight = null;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.stage = primaryStage;
        createUI();
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
        stage.setMinWidth(700);
        stage.setMinHeight(850);
        stage.setTitle("HomiesComix");
        stage.show();
    }
    public void createMainPane()
    {
        leftBubble.setFitHeight(250);
        leftBubble.setFitWidth(290);
        leftBubble.setPreserveRatio(true);

        rightBubble.setFitWidth(250);
        rightBubble.setFitWidth(290);
        rightBubble.setPreserveRatio(true);

        leftCharView.setFitHeight(300);
        leftCharView.setFitWidth(300);
        leftCharView.setPreserveRatio(true);
        leftCharView.setStyle("-fx-cursor: hand");

        rightCharView.setFitHeight(300);
        rightCharView.setFitWidth(300);
        rightCharView.setPreserveRatio(true);
        rightCharView.setStyle("-fx-cursor: hand");

        leftCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectFrame(leftCharView, characterLeft);
                event.consume();
            }
        });

        rightCharView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectFrame(rightCharView, characterRight);
                event.consume();
            }
        });

        //Stack pane wrapper for the bubble and text
        StackPane leftBubbleWrapper = new StackPane(leftBubble, leftBubbleText);
        StackPane rightBubbleWrapper = new StackPane(rightBubble, rightBubbleText);

        leftBubbleWrapper.setMaxSize(300, 250);
        leftBubbleWrapper.setAlignment(Pos.TOP_CENTER);
        rightBubbleWrapper.setMaxSize(300, 250);
        rightBubbleWrapper.setAlignment(Pos.TOP_CENTER);

        StackPane.setMargin(leftBubbleText, new Insets(15, 0, 0,0));
        StackPane.setMargin(rightBubbleText, new Insets(15, 0, 0,0));

        bubbleTextStyle(leftBubbleText);
        bubbleTextStyle(rightBubbleText);

        GridPane mainPane = new GridPane();
        //(Node, colIndex, rowIndex, colSpan, rowSpan)
        mainPane.add(leftBubbleWrapper, 0,0,1,1);
        mainPane.add(rightBubbleWrapper, 1,0,1,1);
        mainPane.add(leftCharView, 0, 1, 1, 1);
        mainPane.add(rightCharView, 1, 1, 1, 1);

        GridPane.setValignment(leftBubbleWrapper, VPos.BOTTOM);
        GridPane.setValignment(rightBubbleWrapper, VPos.BOTTOM);

        mainPane.setStyle("-fx-background-color: white");
        mainPane.setMaxSize(600, 600);
        mainPane.setHgap(2);
        mainPane.setVgap(2);
        //mainPane.setGridLinesVisible(true);

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(50);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        mainPane.getRowConstraints().addAll(row0, row1);

        BorderPane.setAlignment(mainPane, Pos.CENTER);
        BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        layout.setCenter(mainPane);
    }

    public void createBottomPane()
    {
        //Hbox
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setStyle("-fx-background-color: #103859; -fx-border-color: #d4d4d4");

        ListView bottom1 = new ListView();
        bottom1.setStyle("-fx-border-color: black ;");
        bottom1.setPrefWidth(150);
        bottom1.setPrefHeight(150);
        ListView bottom2 = new ListView();
        bottom2.setStyle("-fx-border-color: black ;");
        bottom2.setPrefWidth(150);
        bottom2.setPrefHeight(150);
        ListView bottom3 = new ListView();
        bottom3.setStyle("-fx-border-color: black ;");
        bottom3.setPrefWidth(150);
        bottom3.setPrefHeight(150);

        hbox.getChildren().addAll(bottom1,bottom2,bottom3);
        layout.setBottom(hbox);
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

    public void createButtons()    {
        //VBox within a BorderPane within another BorderPane
        VBox vbox = new VBox();
        vbox.setMinWidth(50);
        vbox.setPrefWidth(50);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(5, 5 ,5, 5));
        vbox.setStyle("-fx-background-color: #103859");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Button importLeftChar = new Button();
        buttonCommonStyles(importLeftChar);
        importLeftChar.setGraphic(setButtonImg(40, "importLeftChar.png"));
        importLeftChar.setOnAction(event -> {
            characterLeft = insertModel(leftCharView);
        });

        Button importRightChar = new Button();
        buttonCommonStyles(importRightChar);
        importRightChar.setGraphic(setButtonImg(40, "importRightChar.png"));
        importRightChar.setOnAction(event -> {
            characterRight = insertModel(rightCharView);
        });

        Button flip = new Button();
        buttonCommonStyles(flip);
        flip.setGraphic(setButtonImg(40, "flip.png"));
        flip.setOnAction(event ->{
            if(selectedCharacter != null){
                selectedCharacter.flipImage();
                selectedCharacterView.setImage(selectedCharacter.getCharacterImage());
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button rotateLeft = new Button();
        buttonCommonStyles(rotateLeft);
        rotateLeft.setGraphic(setButtonImg(40, "rotateLeft.png"));
        rotateLeft.setOnAction(event ->{
            if(selectedCharacter != null){
                double rotate = selectedCharacterView.getRotate();
                rotate -= 90;

                if(rotate == 360.0 || rotate == -360.0){
                    rotate = 0.0;
                }
                selectedCharacterView.setRotate(rotate);
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button rotateRight = new Button();
        buttonCommonStyles(rotateRight);
        rotateRight.setGraphic(setButtonImg(40, "rotateRight.png"));
        rotateRight.setOnAction(event ->{
            if(selectedCharacter != null){
                double rotate = selectedCharacterView.getRotate();
                rotate += 90;

                if(rotate == 360.0 || rotate == -360.0){
                    rotate = 0.0;
                }
                selectedCharacterView.setRotate(rotate);
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        ColorPicker colorPalette = new ColorPicker();
        colorPalette.setMinHeight(40);
        colorPalette.setMinWidth(40);
        colorPalette.setStyle("-fx-background-color: rgba(0, 0, 0, 1); -fx-background-radius: 2; -fx-cursor: hand");
        colorPalette.setOnAction(event ->{
            selectedColor = colorPalette.getValue();
        });

        Button genderSwap = new Button();
        buttonCommonStyles(genderSwap);
        genderSwap.setGraphic(setButtonImg(40, "changeGender.png"));
        genderSwap.setOnAction(event ->{
            if(selectedCharacterView != null && selectedCharacter != null){
                selectedCharacter.switchGenders();
                selectedCharacterView.setImage(selectedCharacter.getCharacterImage());
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button changeSkinTone = new Button();
        buttonCommonStyles(changeSkinTone);
        changeSkinTone.setGraphic(setButtonImg(40, "bodyColor.png"));
        changeSkinTone.setOnAction(event ->{
            if(selectedCharacterView != null && selectedCharacter != null){
                selectedCharacter.skinChange(selectedColor);
                selectedCharacterView.setImage(selectedCharacter.getCharacterImage());
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button changeHairColor = new Button();
        buttonCommonStyles(changeHairColor);
        changeHairColor.setGraphic(setButtonImg(40, "hairColor.png"));
        changeHairColor.setOnAction(event ->{
            if(selectedCharacterView != null && selectedCharacter != null){
                selectedCharacter.hairChange(selectedColor);
                selectedCharacterView.setImage(selectedCharacter.getCharacterImage());
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button lipsColor = new Button();
        buttonCommonStyles(lipsColor);
        lipsColor.setGraphic(setButtonImg(40, "lipsColor.png"));
        lipsColor.setOnAction(actionEvent -> {
            selectedCharacter.lipsColor(selectedColor);
            selectedCharacterView.setImage(selectedCharacter.getCharacterImage());
        });

        Button speechBubble = new Button();
        buttonCommonStyles(speechBubble);
        speechBubble.setGraphic(setButtonImg(40, "speechBubbleButton.png"));
        speechBubble.setOnAction(actionEvent -> {
            if(selectedCharacter != null){
                importBubble("speechBubble.png");
            }
        });

        Button thoughtBubble = new Button();
        buttonCommonStyles(thoughtBubble);
        thoughtBubble.setGraphic(setButtonImg(40, "thoughtBubbleButton.png"));
        thoughtBubble.setOnAction(actionEvent -> {
            if(selectedCharacter != null){
                importBubble("thoughtBubble.png");
            }
        });

        Button removeBubble = new Button();
        buttonCommonStyles(removeBubble);
        removeBubble.setGraphic(setButtonImg(40, "removeBubbleButton.png"));
        removeBubble.setOnAction(actionEvent ->
        {
            if(selectedCharacterView == leftCharView)
            {
                leftBubble.setImage(null);
                leftBubbleText.setText("");
                selectedCharacter.setBubbleText("");
            }
            else if(selectedCharacterView==rightCharView)
            {
                rightBubble.setImage(null);
                rightBubbleText.setText("");
                selectedCharacter.setBubbleText("");
            }
        });

        vbox.getChildren().addAll(colorPalette, importLeftChar, importRightChar, flip, rotateLeft, rotateRight, genderSwap, changeSkinTone, changeHairColor, lipsColor, speechBubble, thoughtBubble, removeBubble);

        layout.setLeft(scrollPane);
    }

    private Character insertModel(ImageView imgView)    //Uploads character to workspace pane
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null)
        {
            Image image = new Image(file.toURI().toString());   //image
            Character newCharacter = new Character(image);
            imgView.setImage(image);              //ImageView
            return newCharacter;
        }
        return null;
    }

    private ImageView setButtonImg(int size, String filename){
        ImageView imgV =new ImageView("/resources/"+filename);
        imgV.setFitHeight(size);
        imgV.setFitWidth(size);
        return imgV;
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand");
    }

    // sets the given character view and character to currently selected
    private void selectFrame(ImageView characterView, Character character){
        if(selectedCharacterView != null){
            selectedCharacterView.setEffect(null);
        }
        selectedCharacterView = characterView;
        selectedCharacterView.setEffect(new DropShadow(10, Color.BLACK));
        selectedCharacter = character;

        if(selectedCharacterView == leftCharView){
            System.out.println("LEFT CHARACTER SELECTED");
        }
        else if(selectedCharacterView == rightCharView){
            System.out.println("RIGHT CHARACTER SELECTED");
        }
    }

    private void importBubble(String type){
        ImageView bubble = new ImageView("/resources/"+ type);
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
                selectedCharacter.setBubbleText(text);
                leftBubbleText.setText(text);
            }
            else if(selectedCharacterView == rightCharView){
                rightBubble.setImage(bubble.getImage());
                selectedCharacter.setBubbleText(text);
                rightBubbleText.setText(text);
            }
        }
    }

    private void bubbleTextStyle(Label text){
        text.setWrapText(true);
        text.setPrefSize(250, 150);
        text.setFont(Font.font("Arial", 20));
        text.setTextAlignment(TextAlignment.CENTER);
        //text.setStyle("-fx-background-color: red");
    }
//    public static void saveToFile(Image image) {
//        File outputFile = new File("../image.png");
//        BufferedImage buffImage = SwingFXUtils.fromFXImage(image, null);
//        try {
//            ImageIO.write(buffImage, "png", outputFile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}