import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ComixApp comixApp = new ComixApp();
        AppGUI view = new AppGUI(primaryStage);
        HelpPage helpPage = new HelpPage();
        view.createUI();
        Controller controller = new Controller(comixApp, view, helpPage);
        controller.execution();
    }
}