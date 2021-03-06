package main.view;

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
            + "1.4 \"Gender Swap\" button switches the gender of characters. Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.5 The color palette at the top lets you select a colour for colour changing operations .\n"
                    + "\n"
            + "1.6 \"Skin Tone\" button changes the colour of the Character's skin . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.7 \"Hair Color\" button changes the colour of the Character's hair . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n",
            "1.8 \"Lip Color\" button changes the colour of the Character's lips . A colour has to be selected from the colour palette first .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.9 \"Speech Bubble\" button allows you to add a speech bubble at the top of the character .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.10  \"Thought Bubble\" button allows you to add a thought bubble at the top of the character .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.11  \"Remove Bubble\" button allows you to remove any speech/thought bubble .\n" +
                    "Character has to be selected in order for the operation to work .\n"
                    + "\n"
            + "1.12 \"Top Narration\" button allows you to add narrative text at the TOP of the panel. When you first add the text, the text is displayed in a single line." +
                    "If the font size is too small you can right click the [Top Narration] button and select wrap text to display the text in multiple lines. If the wrap feature" +
                    "decreases the font size of the text, smaller than it is in a single line, then the text will be preserved in a single line. If the text is wrapped and you " +
                    "want to have it in a single line, you right click the button again and select single line option.\n"
                    + "\n"
            + "1.13 \"Bottom Narration\" button allows you to add narrative text at the BOTTOM of the panel. When you first add the text, the text is displayed in a single line." +
                    "If the font size is too small you can right click the [Bottom Narration] button and select wrap text to display the text in multiple lines. If the wrap feature" +
                    "decreases the font size of the text, smaller than it is in a single line, then the text will be preserved in a single line. If the text is wrapped and you " +
                    "want to have it in a single line, you right click the button again and select single line option.\n"
                    + "\n"
            + "1.14 \"Comic Title\" button allows you to set the title of the WebComic. Default name is HomiesComix and the character limit is 100 .\n"
                    + "\n"
            + "1.15 \"Comic Credits\" button allows you to set the final credits of the Webcomic . Default name is Homiescomix and the character limit is 100 .\n"
                    + "\n",
            "2. Top Bar Buttons\n"
                    + "\n"
            + "2.1 To save a panel select Panel->Save from the Top Bar or, if the panel exists in the comic strip, right click the panel and select the Save option." +
                    " Make sure two characters have been imported.\n"
                    + "\n"
            + "2.2 To go back and work on a previous panel , simply click on the panel you desire to open again .\n"
                    + "\n"
            + "2.3 To delete a panel you can either select Panel->Delete or right click the panel and select the Delete option." +
                    "A popup will show on screen to make sure you want to delete the panel . By clicking yes the panel will be deleted .\n"
                    + "\n"
            + "2.4 To save file in xml format select File->Save and select directory where file should be saved . " +
                    "The log file is saved in the directory as the executable file . Make sure the panel is saved first .\n"
                    + "\n"
            + "2.5 To load an xml file select File->Load. Make sure to set the directory of the characters first, File -> Characters Directory." +
                    " This will allow the app to load the proper pose for each figure in the xml\n"
                    + "\n"
            + "2.6 To save file in html format select File->Save as HTML. A popup will show with instructions on how and where to save " +
                    "the file .\n"
                    + "\n"
            + "2.7 To change panel position in the comic strip right click on the panel you wish to re-arrange and select Change Panel Position ." +
                    "Then introduce the new position of the panel. You can also use the arrow buttons at the top of the strip to change the position" +
                    "of the selected panel to the left or to the right.\n"

    };

    final String[] aboutTextPages = new String[] {
            "About StayAtHomies\n"
                    + "\n"
            + "Hello !\n"
                    + "\n"
            + "We are StayAtHomies , a Software Engineering Team made up of 3 nerds : Gabriel , Niall and Daiana and this " +
                    "is our project for the Software Engineering 3 module .\n"
                    + "\n"
            + "We hope you enjoy using our app and managed to make some fun comics with it !\n" , "", ""
    };

    final String[] gettingStartedTextPages = new String[] {
            "First Comic Panel\n"
                    + "\n"
            + "This is a step by step tutorial on how to create your first ever comic panel using our application !\n"
                    + "\n"
            + "Step 1 : Selecting our characters ! In order to import our characters we use the \"Import Left\" and \"Import Right\"" +
                    "buttons . These will pop open a window where we select our images folder . Select and import the desired characters .\n"
                    + "\n"
            + "Step 2 : First things first , making sure our characters are facing each other , we do this by clicking on the character " +
                    "on the right hand side and selecting the \"Orientation\" button . This will flip the character to face the right direction .\n"
                    + "\n"
            +"Step 3 : Customizing Characters ! Now comes the fun part , by selecting a colour on the Color Palette located above our buttons , " +
                    "we can play around and customize the characters . For this tutorial we are going to select the colour blue and then the button \"Skin Tone\" ," +
                    "then the colour Orange and the button \"Hair Color\" for the character on the LEFT .\n"
                    + "\n"
            + "Lastly we are going to make this character a male by pressing the \"Gender Swap\" button .\n"
                    + "\n"
            + "Now its up to you what the character on the Right side is going to look like . Make sure you play around with the " +
                    "buttons and colour to customize it as you wish .\n"
                    + "\n",
            "Step 4 : Lets add a setting to our characters . By using the buttons \"Top Narration\" and \"Bottom Narration\" " +
                    "we will add some text such as \"In a galaxy far far away ... \" , but i will leave that to your imagination .\n"
                    + "\n"
            + "Step 5 : \"But why are we here ?\" well lets find out by adding some speech bubbles to these characters . " +
                    "First lets select the character on the LEFT and the button \"Speech Bubble\" . For text i'm thinking of a joke " +
                    "\"Two oranges walk into a bar ..\" , it is up to you to continue the conversation . \n"
                    + "\n"
            + "If a joke is not what you had in mind , you can always use the \"Remove Bubble\" button to delete the speech bubble " +
                    "and create a new one .\n"
                    + "\n"
            + "Step 6 : Now that our first panel looks done , we can save it in the comic strip by selecting model.Panel->Save from the " +
                    "top bar . It is up to you to continue this story line , or delete it and start all over again !\n"
                    + "\n"
            + "For more help on what each button does , select Help->Help from the Top Bar .\n"
                    + "\n"
            + "Thank you for using our App :)\n"
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
        pagination = new Pagination();
        pagination.setStyle("fx-border-color:red;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if(page == "HELP") {
                    if(pageIndex >= helpTextPages.length) {
                        return null;
                    }
                    else {
                        pagination.setPageCount(3);
                        return createPage(pageIndex, helpTextPages);
                    }
                }
                if(page == "STARTED") {
                    if(pageIndex >= gettingStartedTextPages.length) {
                        return null;
                    }
                    else {
                        pagination.setPageCount(2);
                        return createPage(pageIndex, gettingStartedTextPages);
                    }
                }
                if(page == "ABOUT") {
                    if(pageIndex >= gettingStartedTextPages.length) {
                        return null;
                    }
                    else {
                        pagination.setPageCount(1);
                        return createPage(pageIndex, aboutTextPages);
                    }
                }
                else {
                    pagination.setPageCount(3);
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
