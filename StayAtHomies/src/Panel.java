import javafx.scene.control.*;
import javafx.scene.image.Image;

public class Panel extends WorkingPane
{
    public Panel(){}

    public Panel(int id, Image panelShot, Character leftChar, Character rightChar,
                 String narrativeTextTop, String narrativeTextBottom){
        super(id, panelShot, leftChar, rightChar, narrativeTextTop, narrativeTextBottom);
    }
}