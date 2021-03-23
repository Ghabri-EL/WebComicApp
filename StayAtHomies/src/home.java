import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 
public class home extends Application 
{
	private Stage stage;
	private BorderPane layout;
	private Scene scene;
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
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setTitle("User Report 1");  
        stage.show(); 
    } 
    public void createMainPane()
    {
    	Image image1 = new Image("images/neutral.png");
    	Image image2 = new Image("images/joy.png");
    	
    	leftChar.setFitHeight(300);
    	leftChar.setFitWidth(300);
    	
   		rightChar.setFitHeight(300);
    	rightChar.setFitWidth(300);
    	
    	leftChar.setImage(image1);
    	rightChar.setImage(image2);
    		
    	
    	
    	
    	HBox hbox = new HBox();
    	hbox.getChildren().addAll(leftChar,rightChar);
    	
    	
    	hbox.setStyle("-fx-background-insets: 10 ;");
    	hbox.setAlignment(Pos.BOTTOM_CENTER);
    	
    	layout.setCenter(hbox);
    }
    public void createBottomPane()
    {
    	//Hbox
    	HBox hbox = new HBox();
    	
    	ListView bottom1 = new ListView();
    	bottom1.setStyle("-fx-border-color: black ;");
    	bottom1.setPrefWidth(200);
    	bottom1.setPrefHeight(100);
    	ListView bottom2 = new ListView();
    	bottom2.setStyle("-fx-border-color: black ;");
    	bottom2.setPrefWidth(200);
    	bottom2.setPrefHeight(200);
    	ListView bottom3 = new ListView();
    	bottom3.setStyle("-fx-border-color: black ;");
    	bottom3.setPrefWidth(200);
    	bottom3.setPrefHeight(200);
    	
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
    	
    	ScrollPane scrollPane = new ScrollPane(vbox);
    	scrollPane.setFitToHeight(true);
    	
    	Button btn1 = new Button();
        btn1.setText("Rotate");
        btn1.setPrefWidth(100);
        btn1.setPrefHeight(50);
        btn1.setOnAction(event -> 
        {
        	rightChar.setRotate(90);
        });
        
        Button btn2 = new Button();
        btn2.setText("BTN2");
        btn2.setPrefWidth(100);
        btn2.setPrefHeight(50);
        
        Button btn3 = new Button();
        btn3.setText("BTN3");
        btn3.setPrefWidth(100);
        btn3.setPrefHeight(50);
        
        Button btn4 = new Button();
        btn4.setText("BTN4");
        btn4.setPrefWidth(100);
        btn4.setPrefHeight(50);
        
        Button btn5 = new Button();
        btn5.setText("BTN5");
        btn5.setPrefWidth(100);
        btn5.setPrefHeight(50);
        
        Button btn6 = new Button();
        btn6.setText("BTN6");
        btn6.setPrefWidth(100);
        btn6.setPrefHeight(50);
        
        Button btn7 = new Button();
        btn7.setText("BTN7");
        btn7.setPrefWidth(100);
        btn7.setPrefHeight(50);
        
        Button btn8 = new Button();
        btn8.setText("BTN8");
        btn8.setPrefWidth(100);
        btn8.setPrefHeight(50);
        
        vbox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8);

    	BorderPane buttons = new BorderPane(scrollPane);
    	
        layout.setLeft(buttons);
    } 
}