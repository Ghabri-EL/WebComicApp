import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HelpPage {
    private Pagination pagination;

    public int itemsPerPage() {
        return 5;
    }

    public VBox createHelpPage(int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for(int i = page; i < page + itemsPerPage(); i++) {
            VBox element = new VBox();
            Label testText = new Label("This is a test text");

            element.getChildren().add(testText);
            box.getChildren().add(element);
        }

        return box;
    }

    public void helpPage(final Stage stage) {
        pagination = new Pagination(28, 0);
        pagination.setStyle("fx-border-color:red;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createHelpPage(pageIndex);
            }
        });

        StackPane sp = new StackPane();
        sp.getChildren().add(pagination);
//        AnchorPane anchor = new AnchorPane();
//        AnchorPane.setTopAnchor(pagination, 10.0);
//        AnchorPane.setRightAnchor(pagination, 10.0);
//        AnchorPane.setBottomAnchor(pagination, 10.0);
//        AnchorPane.setLeftAnchor(pagination, 10.0);
//        anchor.getChildren().addAll(pagination);
        Scene scene = new Scene(sp);
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.show();
    }
}
