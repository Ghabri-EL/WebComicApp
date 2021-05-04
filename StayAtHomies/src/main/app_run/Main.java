package main.app_run;

import javafx.application.Application;
import javafx.stage.Stage;
import main.model.ComixApp;
import main.view.AppGUI;
import main.controller.Controller;

public class Main extends Application{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        ComixApp comixApp = new ComixApp();
        AppGUI view = new AppGUI(primaryStage);
        view.createUI();
        Controller controller = new Controller(comixApp, view);
        controller.execution();
    }
}