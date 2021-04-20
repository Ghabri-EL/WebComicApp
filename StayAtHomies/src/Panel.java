import javafx.scene.control.*;
import javafx.scene.image.Image;

public class Panel extends WorkingPane
{
    public Panel(){}

    public Panel(int id, Image panelShot, Character characterLeft, Character characterRight, String leftBubbleText, String rightBubbleText,
                 BubbleType leftBubbleType, BubbleType rightBubbleType, String narrativeTextTop, String narrativeTextBottom){
        super(id, panelShot, characterLeft, characterRight, leftBubbleText, rightBubbleText,
                leftBubbleType, rightBubbleType, narrativeTextTop, narrativeTextBottom);
    }
}