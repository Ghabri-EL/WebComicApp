import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    ImageView leftBubble = new ImageView();
    ImageView rightBubble = new ImageView();
    ImageView leftCharView = new ImageView();
    ImageView rightCharView = new ImageView();

    Image thoughtImage = new Image("/resources/thoughtBubble.png");
    Image speechImage = new Image("/resources/speechBubble.png");
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
        leftBubble.setFitHeight(300);
        leftBubble.setFitWidth(300);
        leftBubble.setPreserveRatio(true);
        //leftSpeechView.setImage(thoughtImage);

        rightBubble.setFitHeight(300);
        rightBubble.setFitWidth(300);
        rightBubble.setPreserveRatio(true);
        //rightSpeechView.setImage(speechImage);

        leftCharView.setFitHeight(300);
        leftCharView.setFitWidth(300);
        leftCharView.setPreserveRatio(true);

        rightCharView.setFitHeight(300);
        rightCharView.setFitWidth(300);
        rightCharView.setPreserveRatio(true);

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

        GridPane mainPane = new GridPane();
        //(Node, colIndex, rowIndex, colSpan, rowSpan)
        mainPane.add(leftBubble, 0,0,1,1);
        mainPane.add(rightBubble, 1,0,1,1);
        mainPane.add(leftCharView, 0, 1, 1, 1);
        mainPane.add(rightCharView, 1, 1, 1, 1);


        mainPane.setStyle("-fx-cursor: hand; -fx-background-color: white");
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

    public void createButtons()
    {
        //VBox within a BorderPane within another BorderPane
        VBox vbox = new VBox();
        vbox.setMinWidth(60);
        vbox.setPrefWidth(60);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(5, 10 ,5, 5));
        vbox.setStyle("-fx-background-color: #103859");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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
        colorPalette.setPrefHeight(40);
        colorPalette.setStyle("-fx-background-insets: 0 2 0 2; -fx-background-color: rgba(0, 0, 0, 1); -fx-background-radius: 2; -fx-cursor: hand");
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
        speechBubble.setGraphic(setButtonImg(40, "speechBubble.png"));
        speechBubble.setOnAction(actionEvent ->
        {
            if(selectedCharacterView==leftCharView)
            {
                //leftBubble.setImage(speechImage);
                TextField txtField = new TextField();
                StackPane sb = new StackPane(leftBubble, txtField);
            }
            else if(selectedCharacterView==rightCharView)
            {
                Image tempBubble = flipBubble(speechImage);
                rightBubble.setImage(tempBubble);
            }
            else
            {
                leftBubble.setImage(null);
            }
        });

        Button thoughtBubble = new Button();
        buttonCommonStyles(thoughtBubble);
        thoughtBubble.setGraphic(setButtonImg(40, "thoughtBubble.png"));
        thoughtBubble.setOnAction(actionEvent ->
        {

            if(selectedCharacterView==leftCharView)
            {
                Image tempBubble = flipBubble(thoughtImage);
                leftBubble.setImage(tempBubble);
            }
            else if(selectedCharacterView==rightCharView)
            {
                rightBubble.setImage(thoughtImage);
            }
        });

        Button removeBubble = new Button();
        buttonCommonStyles(removeBubble);
        removeBubble.setGraphic(setButtonImg(40, "removeBubble.png"));
        removeBubble.setOnAction(actionEvent ->
        {
            if(selectedCharacterView==leftCharView)
            {
                leftBubble.setImage(null);
            }
            else if(selectedCharacterView==rightCharView)
            {
                rightBubble.setImage(null);
            }
        });

        vbox.getChildren().addAll(colorPalette, importLeftChar, importRightChar, flip, rotateLeft, rotateRight, genderSwap, changeSkinTone, changeHairColor, lipsColor, speechBubble, thoughtBubble, removeBubble);

        layout.setLeft(scrollPane);
    }

    private Image flipBubble(Image image)
    {
        int width = (int) image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        WritableImage flippedImage = new WritableImage(width,  height);
        PixelWriter pixelWriter = flippedImage.getPixelWriter();

        // x y axis of the image
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixelWriter.setArgb(x, y, pixelReader.getArgb((width - 1) - x, y));
            }
        }
        return flippedImage;
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