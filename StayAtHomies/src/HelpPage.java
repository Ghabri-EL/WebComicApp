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
            + "1.1 \"Import Left\" button opens up a window for you to search and select the image Character that is to be placed on the left side of the panel.\n"
                    + "\n"
            + "1.2 \"Import Right\" button does the same thing, it lets you import a Character for the right side of the panel.\n"
                    + "\n"
            + "1.3 \"Orientation\" button flips the Character image horizontally. Character has to be selected in order for the operation to work.\n"
                    + "\n"
            + "1.4 \"Gender Swap\" button switches the gender of characters . Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.5 The color palette at the top lets you select a colour for colour changing operations .\n"
                    + "\n"
            + "1.6 \"Skin Tone\" button changes the colour of the Character's skin . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.7 \"Hair Color\" button changes the colour of the Character's hair . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.8 \"Lip Color\" button changes the colour of the Character's lips . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n",
            "1.9 \"Speech Bubble\" button allows you to add a speech bubble at the top of the character .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.10  \"Thought Bubble\" button allows you to add a thought bubble at the top of the character .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.11  \"Remove Bubble\" button allows you to remove any speech/thought bubble .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.12 \"Top Narration\" button allows you to add narrative text at the TOP of the panel .\n"
                    + "\n"
            + " 1.13 \"Bottom Narration\" button allows you to add narrative text at the BOTTOM of the panel .\n"
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
