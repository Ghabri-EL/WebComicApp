import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HelpPage {
    private Pagination pagination;
    private Button exitHelp;
    private AnchorPane anchor = new AnchorPane();

    final String[] helpTextPages = new String[] {
            "1. Left Bar Buttons\n"
                    + "\n"
            + "1.1 Second button on the left bar opens up a window for you to search and select the image Character that is to be placed on the left side of the panel.\n"
                    + "\n"
            + "1.2 The third button does the same thing, it lets you import a Character for the right side of the panel.\n"
                    + "\n"
            + "1.3 The fourth button flips the Character image horizontally. Image has to be selected in order for the operation to work.\n"
                    + "\n"
            + "1.4 The 5th button from the left hand side bar switches the gender of characters . Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.5 In order to change skin colour , first use the first button on the left hand side bar which will open the color palette . Once the preferred color" +
                    " is selected , press the 6th button down which will change the skin color . Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.6 To change hair color again select a color from the color palette (first button on left hand side bar) . Then use the 7th button down to change " +
                    "the hair color to the selected color . Character has to be selected in order for the operation to work ."
                    + "\n"
            + "1.7 To change lip colour select a colour from the colour palette (first button on left hand side bar) . Then use the 8th button down to change" +
                    "the lips colour . Character has to be selected in order for the operation to work .\n"
                    + "\n",
            "1.8 First insert a character by pressing the second button on the left hand side bar , then selecting the image of the desired character . Once" +
                    "thats done , select the speech button which is 9th button down on the left hand side bar . A window will pop up where you can introduce the" +
                    "desired text , and press ok . A speech bubble will appear above the character with the desired text . Make sure the character is selected before" +
                    "inserting the speech bubble .\n"
                    + "\n"
            + "1.9 Follow the same steps as described above in \"Story 7\" in order to insert a thought bubble by using the 10th button down from the left hand side bar ." +
                    "Make sure character is selected before inserting the thought bubble .\n"
                    + "\n"
            + "1.10 If you wish to delete a speech bubble or a thought bubble , use the 11th button down from the left hand side bar . In order for the operation to work ," +
                    "make sure you select the character of which you want to delete the speech bubble .\n"
                    + "\n"
            + "1.11 Once a character is selected , use the 12th button down on the left hand side bar which will bring up a popup . Insert the text you wish to add" +
                    "at the TOP of the panel .\n"
                    + "\n"
            + " 1.12 Once a character is selected , use the 13th button down from the left hand side bar which will bring up a popup . Insert the text you wish to add" +
                    "at the BOTTOM of the panel.\n"
                    + "\n",
            "2. Top Bar Buttons\n"
                    + "\n"
            + "2.1 In order to save panels to a list , use the top bar \"Panel\" menu . Make sure you have 2 characters into the current panel , then use the \"save\" option" +
                    "on the Panel menu . This will save the panels to the bottom comic strip section .\n"
                    + "\n"
            + "2.2 To go back and work on a previous panel , simply click on the panel you desire to open again .\n"
                    + "\n"
            +"2.3 To delete a panel you no longer need , click on the panel from the comic strip then use the \"Panel\" menu in the top bar and press on \"Delete\" ." +
                    "A popup will show on screen to make sure you want to delete the panel . By clicking yes the panel will be deleted .\n"

    };

    final String[] aboutTextPages = new String[] {
            "About StayAtHomies\n"
    };

    final String[] gettingStartedTextPages = new String[] {
            "First comic Panel\n"
    };

    public int itemsPerPage() {
        return 1;
    }

    public VBox createPage(int pageIndex, String[] textPages) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for(int i = page; i < page + itemsPerPage(); i++) {
            TextArea text = new TextArea(textPages[i]);
            text.setWrapText(true);
            text.setPrefSize(400, 1000);
            text.setEditable(false);
            text.setMouseTransparent(true);
            box.getChildren().add(text);
        }

        return box;
    }

    public AnchorPane helpPage(String page) {
        pagination = new Pagination(3, 0);
        pagination.setStyle("fx-border-color:red;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if(page == "HELP") {
                    if(pageIndex >= helpTextPages.length) {
                        return null;
                    }
                    else {
                        return createPage(pageIndex, helpTextPages);
                    }
                }
                if(page == "STARTED") {
                    if(pageIndex >= gettingStartedTextPages.length) {
                        return null;
                    }
                    else {
                        return createPage(pageIndex, gettingStartedTextPages);
                    }
                }
                if(page == "ABOUT") {
                    if(pageIndex >= gettingStartedTextPages.length) {
                        return null;
                    }
                    else {
                        return createPage(pageIndex, aboutTextPages);
                    }
                }
                else {
                    return createPage(pageIndex, helpTextPages);
                }
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);

        exitHelp = new Button("X");
        exitHelp.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand");
        exitHelp.setOnAction(event -> closePage());

        AnchorPane.setBottomAnchor(exitHelp, 10.0);
        AnchorPane.setLeftAnchor(exitHelp, 10.0);

        anchor.getChildren().addAll(pagination, exitHelp);
        anchor.setStyle("-fx-border-color: rgb(209, 209, 209)");

        return anchor;
    }

    private AnchorPane closePage() {
        anchor.setVisible(false);
        anchor.setPrefSize(0,0);
        return anchor;
    }
}
