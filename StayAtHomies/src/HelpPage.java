import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HelpPage {
    private Pagination pagination;
    final String[] textPages = new String[] {
            "Left Bar Buttons\n"
                    + "\n"
            + "First button on the left bar opens up a window for you to search and select the image Character that is to be placed on the left side of the panel.\n"
                    + "\n"
            + "The second button does the same thing, it lets you import a Character for the right side of the panel.\n"
                    + "\n"
            + "The third button flips the Character image horizontally. Image has to be selected in order for the operation to work.\n"
                    + "\n"
            + "The 7th button from the left hand side bar switches the gender of characters . Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "In order to change skin colour , first use the first button on the left hand side bar which will open the color palette . Once the preferred color" +
                    " is selected , press the 8th button down which will change the skin color . Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "To change hair color again select a color from the color palette (first button on left hand side bar) . Then use the 9th button down to change " +
                    "the hair color to the selected color . Character has to be selected in order for the operation to work ."
                    + "\n",
            "First insert a character by pressing the second button on the left hand side bar , then selecting the image of the desired character . Once" +
                    "thats done , select the speech button which is 11th button down on the left hand side bar . A window will pop up where you can introduce the" +
                    "desired text , and press ok . A speech bubble will appear above the character with the desired text . Make sure the character is selected before" +
                    "inserting the speech bubble .\n"
                    + "\n"
            + "Follow the same steps as described above in \"Story 7\" in order to insert a thought bubble by using the 12th button down from the left hand side bar ." +
                    "Make sure character is selected before inserting the thought bubble .\n"
                    + "\n"
            + "If you wish to delete a speech bubble or a thought bubble , use the 13th button down from the left hand side bar . In order for the operation to work ," +
                    "make sure you select the character of which you want to delete the speech bubble .\n"
                    + "\n"
            + "Once a character is selected , use the 12th button down on the left hand side bar which will bring up a popup . Insert the text you wish to add" +
                    "at the TOP of the panel .\n"
                    + "\n"
            + "Once a character is selected , use the 13th button down from the left hand side bar which will bring up a popup . Insert the text you wish to add" +
                    "at the BOTTOM of the panel.\n"
                    + "\n",
            "Top Bar Buttons\n"
                    + "\n"

    };

    public int itemsPerPage() {
        return 1;
    }

    public VBox createHelpPage(int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for(int i = page; i < page + itemsPerPage(); i++) {
            TextArea text = new TextArea(textPages[i]);
            text.setWrapText(true);
            box.getChildren().add(text);
        }

        return box;
    }

    public AnchorPane helpPage() {
        pagination = new Pagination(3, 0);
        pagination.setStyle("fx-border-color:red;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if(pageIndex >= textPages.length) {
                    return null;
                }
                else {
                    return createHelpPage(pageIndex);
                }
            }
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);

        return anchor;
    }
}
