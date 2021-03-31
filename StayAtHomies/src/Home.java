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
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;

public class Home extends Application
{
	private Stage stage;
	private BorderPane layout;
	private Scene scene;
	private ImageView selectedCharacter = null;
	ImageView leftChar = new ImageView();
	ImageView rightChar = new ImageView();

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
        importLftChar.setOnAction(event -> insertModel(leftChar));
        
        Button importRightChar = new Button();
        buttonCommonStyles(importRightChar);
        importRightChar.setGraphic(setButtonImg(40, "importRightChar.png"));
        importRightChar.setOnAction(event -> insertModel(rightChar));
        
        Button flip = new Button();
        buttonCommonStyles(flip);
        flip.setGraphic(setButtonImg(40, "flip.png"));
        flip.setOnAction(event ->{
            if(selectedCharacter != null){
                selectedCharacter.setImage(flipImage(selectedCharacter.getImage()));
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

        Button colorPalette = new Button();
        buttonCommonStyles(colorPalette);
        //colorPalette.setGraphic(setButtonImg(40, "colorPalette.png"));
        colorPalette.setOnAction(event ->{

            colorPalette.setOnMouseClicked(new PopupOpenEvent());

        });
        
        Button btn7 = new Button();
        btn7.setText("BTN7");
        
        Button btn8 = new Button();
        btn8.setText("BTN8");

        Button btn9 = new Button();
        btn9.setText("BTN8");

        Button btn10 = new Button();
        btn10.setText("BTN8");

        Button btn11 = new Button();
        btn11.setText("BTN8");

        Button b112 = new Button();
        b112.setText("BTN8");
        
        vbox.getChildren().addAll(importLftChar, importRightChar, flip, rotateLeft, rotateRight, colorPalette, btn7, btn8, btn9, btn10, btn11, b112);

        layout.setLeft(scrollPane);
    }

    // Color Palette Button popup window :
    class PopupOpenEvent implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            Popup popup = new Popup();
            ColorPicker cp = new ColorPicker();

            HBox hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setPadding(new Insets(25, 50, 50, 60));
            hbox.getChildren().addAll(cp);

            popup.getContent().add(hbox);
            popup.setX(e.getScreenX());
            popup.setY(e.getScreenY());
            popup.show(stage);
        }
    }

    private void insertModel(ImageView imgv){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            Image img = new Image(file.toURI().toString());
            imgv.setImage(img);
        }
    }

    private Image flipImage(Image image){
        int width = (int) image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pr = image.getPixelReader();
        WritableImage img = new WritableImage(width,  height);
        PixelWriter pw = img.getPixelWriter();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                pw.setArgb(j, i, pr.getArgb((width - 1) - j, i));
            }
        }
        return img;
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
}