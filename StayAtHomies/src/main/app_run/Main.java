package main.app_run;

import javafx.application.Application;
import javafx.stage.Stage;
import main.model.ComixApp;
import main.view.AppGUI;
import main.controller.Controller;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application{
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        loggerInitialize();
        ComixApp comixApp = new ComixApp();
        AppGUI view = new AppGUI(primaryStage);
        view.createUI();
        Controller controller = new Controller(comixApp, view);
        controller.execution();
    }

    private static void loggerInitialize(){
        try{
            FileHandler fileHandler = new FileHandler("./logfile.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}