package main.app_run;

import javafx.application.Application;
import javafx.stage.Stage;
import main.model.ComixApp;
import main.view.AppGUI;
import main.controller.Controller;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.*;

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
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.WARNING);
        consoleHandler.setFormatter(new SimpleFormatter(){
            private static final String formatOne = "[%1$s] %2$-7s: %3$s%n";
            @Override
            public synchronized String format(LogRecord record){
                return String.format(formatOne, record.getSourceClassName(), record.getLevel().getLocalizedName(), record.getMessage());
            }
        });
        logger.addHandler(consoleHandler);

        try{
            FileHandler fileHandler = new FileHandler("./logfile.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter(){
                private static final String formatOne = "%1$-10s| %2$s%n";
                private static final String formatTwo = "%n|*-*-*-*-*-*-*-*-*-*-* %1$s *-*-*-*-*-*-*-*-*-*-*|%n";

                @Override
                public synchronized String format(LogRecord record){
                    if(record.getLevel() == Level.FINEST){
                        return String.format(formatTwo, record.getMessage());
                    }
                    else{
                        return String.format(formatOne, record.getLevel().getLocalizedName(), record.getMessage());
                    }
                }
            });
            logger.addHandler(fileHandler);
        }catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create the log file", e);
        }
    }
}