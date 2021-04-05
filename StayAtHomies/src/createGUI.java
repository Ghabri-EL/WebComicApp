import javafx.application.Application;
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
import java.io.File;

public class createGUI extends Application
{
    private enum Gender{FEMALE, MALE}
    private enum Frame{LEFT, RIGHT}
    private enum Direction{LEFT, RIGHT}
    private Stage stage;
    private BorderPane layout;
    private Scene scene;
    private ImageView selectedCharacter = null;
    ImageView leftChar = new ImageView();
    ImageView rightChar = new ImageView();
    private static final Color DEFAULT_FEMALE_HAIR_COLOR = Color.web("0xf0ff00ff");
    private static final Color DEFAULT_MALE_HAIR_COLOR = Color.web("0xf9ff00ff");
    private static final Color DEFAULT_SKIN_COLOR = Color.web("0xffe8d8ff");
    private static final Color DEFAULT_LIPS_COLOR = Color.web("0xff0000ff");
    private static final Color DEFAULT_RIBBON_COLOR = Color.web("0xecb4b5ff");
    Color skinColour = DEFAULT_SKIN_COLOR;
    Color newSkinColour = Color.WHITE;
    Color newHairColour = Color.WHITE;
    Color hairColour = Color.web("0xf9ff00ff");
    Color femaleLips = DEFAULT_LIPS_COLOR;
    Color ribbon = DEFAULT_RIBBON_COLOR;
    Gender leftCharGender = Gender.FEMALE;
    Gender rightCharGender = Gender.FEMALE;
    Image leftFemaleHairMask = null;
    Image leftMaleHairMask = null;
    Image rightFemaleHairMask = null;
    Image rightMaleHairMask = null;
    Image leftLipsMask = null;
    Image rightLipsMask = null;
    Image leftBodyMask = null;
    Image rightBodyMask = null;
    Direction leftCharDirection;
    Direction righCharDirection;

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
        leftChar.setFitHeight(300);
        leftChar.setFitWidth(300);
        leftChar.setPreserveRatio(true);

        rightChar.setFitHeight(300);
        rightChar.setFitWidth(300);
        rightChar.setPreserveRatio(true);

        leftChar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedCharacter != null){
                    selectedCharacter.setEffect(null);
                }
                selectedCharacter = leftChar;
                selectedCharacter.setEffect(new DropShadow(10, Color.BLACK));
                System.out.println("LEFT SELECTED");
                event.consume();
            }
        });

        rightChar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedCharacter != null){
                    selectedCharacter.setEffect(null);
                }
                selectedCharacter = rightChar;
                selectedCharacter.setEffect(new DropShadow(10, Color.BLACK));
                System.out.println("RIGHT SELECTED");
                event.consume();
            }
        });

        GridPane mainPane = new GridPane();
        //(Node, colIndex, rowIndex, colSpan, rowSpan)
        mainPane.add(leftChar, 0, 1, 1, 1);
        mainPane.add(rightChar, 1, 1, 1, 1);
        mainPane.setStyle("-fx-cursor: hand; -fx-background-color: white");
        mainPane.setMaxSize(600, 600);
        mainPane.setHgap(2);
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

        Button importLftChar = new Button();
        buttonCommonStyles(importLftChar);
        importLftChar.setGraphic(setButtonImg(40, "importLeftChar.png"));
        importLftChar.setOnAction(event -> {
            insertModel(Frame.LEFT ,leftChar);
            leftCharDirection = Direction.RIGHT;
        });

        Button importRightChar = new Button();
        buttonCommonStyles(importRightChar);
        importRightChar.setGraphic(setButtonImg(40, "importRightChar.png"));
        importRightChar.setOnAction(event -> {
            insertModel(Frame.RIGHT, rightChar);
            righCharDirection = Direction.RIGHT;
        });

        Button flip = new Button();
        buttonCommonStyles(flip);
        flip.setGraphic(setButtonImg(40, "flip.png"));
        flip.setOnAction(event ->{
            if(selectedCharacter != null){
                selectedCharacter.setImage(flipImage(selectedCharacter.getImage()));

                //code required to flip the masks along with the image
                //otherwise we wont be able to maintain a one to one pixel relationship with the mask
                if(selectedCharacter.equals(leftChar)){
                    leftFemaleHairMask = flipImage(leftFemaleHairMask);
                    leftMaleHairMask = flipImage(leftMaleHairMask);
                    leftLipsMask = flipImage(leftLipsMask);
                    leftBodyMask = flipImage(leftBodyMask);
                }
                else{
                    rightFemaleHairMask = flipImage(rightFemaleHairMask);
                    rightMaleHairMask = flipImage(rightMaleHairMask);
                    rightLipsMask = flipImage(rightLipsMask);
                    rightBodyMask = flipImage(rightBodyMask);
                }
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
                double rotate = selectedCharacter.getRotate();
                rotate -= 90;

                if(rotate == 360.0 || rotate == -360.0){
                    rotate = 0.0;
                }
                selectedCharacter.setRotate(rotate);
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
                double rotate = selectedCharacter.getRotate();
                rotate += 90;

                if(rotate == 360.0 || rotate == -360.0){
                    rotate = 0.0;
                }
                selectedCharacter.setRotate(rotate);
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        ColorPicker colorPalette = new ColorPicker();
        colorPalette.setPrefHeight(40);
        colorPalette.setStyle("-fx-background-insets: 0 2 0 2; -fx-background-color: rgba(0, 0, 0, 1); -fx-background-radius: 2; -fx-cursor: hand");
        colorPalette.setOnAction(event ->{
            newSkinColour = colorPalette.getValue();
            newHairColour = colorPalette.getValue();
        });

        Button genderSwap = new Button();
        buttonCommonStyles(genderSwap);
        genderSwap.setGraphic(setButtonImg(40, "changeGender.png"));
        genderSwap.setOnAction(event ->{
            if(selectedCharacter != null){
                Image changeCharacter = null;
                if(selectedCharacter.equals(leftChar)){
                    changeCharacter = switchGenders(selectedCharacter.getImage(), leftFemaleHairMask, leftMaleHairMask, leftLipsMask, leftCharGender);
                    leftCharGender = (leftCharGender == Gender.FEMALE ? Gender.MALE : Gender.FEMALE);
                }
                else{
                    changeCharacter = switchGenders(selectedCharacter.getImage(), rightFemaleHairMask, rightMaleHairMask, rightLipsMask, rightCharGender);
                    rightCharGender = (rightCharGender == Gender.FEMALE ? Gender.MALE : Gender.FEMALE);
                }
                selectedCharacter.setImage(changeCharacter);
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button changeSkinTone = new Button();
        buttonCommonStyles(changeSkinTone);
        changeSkinTone.setGraphic(setButtonImg(40, "bodyColor.png"));
        changeSkinTone.setOnAction(event ->{
            if(selectedCharacter != null){
                if(selectedCharacter.equals(leftChar)){
                    selectedCharacter.setImage(skinChange(selectedCharacter.getImage(), leftBodyMask, leftLipsMask, leftCharGender));
                }
                else{
                    selectedCharacter.setImage(skinChange(selectedCharacter.getImage(), rightBodyMask, rightLipsMask, rightCharGender));
                }
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button changeHairColor = new Button();
        buttonCommonStyles(changeHairColor);
        changeHairColor.setGraphic(setButtonImg(40, "hairColor.png"));
        changeHairColor.setOnAction(event ->{
            if(selectedCharacter != null){
                Image mask = null;

                if(selectedCharacter.equals(leftChar)){
                    mask = leftMaleHairMask;
                    if(leftCharGender == Gender.FEMALE){
                        mask = leftFemaleHairMask;
                    }
                }
                else{
                    mask = rightMaleHairMask;
                    if(rightCharGender == Gender.FEMALE){
                        mask = rightFemaleHairMask;
                    }
                }
                selectedCharacter.setImage(hairChange(selectedCharacter.getImage(), mask));
            }
            else{
                System.out.println("Select one of the characters on which you want to perform the operation");
            }
        });

        Button btn9 = new Button();
        btn9.setText("BTN8");

        Button btn10 = new Button();
        btn10.setText("BTN8");

        Button btn11 = new Button();
        btn11.setText("BTN8");

        Button b112 = new Button();
        b112.setText("BTN8");

        vbox.getChildren().addAll(colorPalette, importLftChar, importRightChar, flip, rotateLeft, rotateRight, genderSwap, changeSkinTone, changeHairColor, btn9, btn10, btn11, b112);

        layout.setLeft(scrollPane);
    }

    //frame refers to the right side or left side of workspace pane
    private void insertModel(Frame frame,ImageView imgv)    //Uploads images to comix strip
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            Image image = new Image(file.toURI().toString());
            setMasks(frame, image);
            imgv.setImage(image);
        }
    }

    private Image flipImage(Image image)
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

    private ImageView setButtonImg(int size, String filename){
        ImageView imgV =new ImageView("/resources/"+filename);
        imgV.setFitHeight(size);
        imgV.setFitWidth(size);
        return imgV;
    }

    private Image skinChange(Image image, Image skinMask, Image lipMask, Gender gender){
        int width = (int) image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pixelReaderImage = image.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        PixelReader pixelReaderSkinMask = skinMask.getPixelReader();
        PixelReader pixelReaderLipsMask = lipMask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReaderSkinMask.getColor(x,y);
                Color lipPixel = pixelReaderLipsMask.getColor(x, y);

                if(!pixel.equals(Color.TRANSPARENT) || (!lipPixel.equals(Color.TRANSPARENT) && gender != Gender.FEMALE)){
                    pixelWriterImage.setColor(x, y, newSkinColour);
                }
            }
        }
        skinColour = newSkinColour;
        return newImage;
    }

    private Image hairChange(Image image, Image mask){
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pixelReaderImage = image.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        PixelReader pixelReaderMask = mask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReaderMask.getColor(x,y);

                if(!pixel.equals(Color.TRANSPARENT) && !pixel.equals(ribbon)){
                    pixelWriterImage.setColor(x, y, newSkinColour);
                }
            }
        }
        hairColour = newHairColour;
        return newImage;
    }

    private Image switchGenders(Image image, Image femaleHairMask, Image maleHairMask, Image lipsMask, Gender gender){
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pixelReaderImage = image.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        PixelReader pixelReaderFemHairMask = femaleHairMask.getPixelReader();
        PixelReader pixelReaderMaleHairMask = maleHairMask.getPixelReader();
        PixelReader pixelReaderLipsMask = lipsMask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color femHairPixel = pixelReaderFemHairMask.getColor(x,y);
                Color maleHairPixel = pixelReaderMaleHairMask.getColor(x,y);
                Color lipsPixel = pixelReaderLipsMask.getColor(x,y);

                if(gender == Gender.FEMALE){
                    if(!femHairPixel.equals(Color.TRANSPARENT) && maleHairPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, Color.WHITE);
                    }

                    if(!lipsPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, skinColour);
                    }
                }
                else{
                    if(!femHairPixel.equals(Color.TRANSPARENT)){
                        if(femHairPixel.equals(ribbon)){
                            pixelWriterImage.setColor(x, y, ribbon);
                        }
                        else{
                            pixelWriterImage.setColor(x, y, hairColour);
                        }
                    }

                    if(!lipsPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, femaleLips);
                    }
                }
            }
        }
        return newImage;
    }

    private void buttonCommonStyles(Button btn){
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand");
    }

    private void setMasks(Frame frame, Image image){
        int width = (int) image.getWidth();
        int height = (int)image.getHeight();
        PixelReader pixelReader = image.getPixelReader();
        WritableImage femaleHairMask = new WritableImage(width,  height);
        PixelWriter pixelWriterFHM = femaleHairMask.getPixelWriter();

        WritableImage maleHairMask = new WritableImage(width,  height);
        PixelWriter pixelWriterMHM = maleHairMask.getPixelWriter();

        WritableImage lipsMask = new WritableImage(width,  height);
        PixelWriter pixelWriterLM = lipsMask.getPixelWriter();

        WritableImage bodyMask = new WritableImage(width,  height);
        PixelWriter pixelWriterBM = bodyMask.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReader.getColor(x, y);
                if(pixel.equals(DEFAULT_FEMALE_HAIR_COLOR) || pixel.equals(ribbon) || pixel.equals(DEFAULT_MALE_HAIR_COLOR)){
                    pixelWriterFHM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterFHM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_MALE_HAIR_COLOR)){
                    pixelWriterMHM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterMHM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_LIPS_COLOR)){
                    pixelWriterLM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterLM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_SKIN_COLOR)){
                    pixelWriterBM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterBM.setColor(x, y, Color.TRANSPARENT);
                }
            }
        }

        if(frame == Frame.LEFT){
            leftFemaleHairMask = femaleHairMask;
            leftMaleHairMask = maleHairMask;
            leftLipsMask = lipsMask;
            leftBodyMask = bodyMask;
        }else{
            rightFemaleHairMask = femaleHairMask;
            rightMaleHairMask = maleHairMask;
            rightLipsMask = lipsMask;
            rightBodyMask = bodyMask;
        }
    }
}