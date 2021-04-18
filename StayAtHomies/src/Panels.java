import javafx.scene.control.*;
import javafx.scene.image.Image;

public class Panels
{
    private Character left;
    private Character right;
    private String narratorText;
    private String leftBubbleText;
    private String rightBubbleText;
    private Image panelShot;
    private int id;

    public Character getLeft() {
        return left;
    }

    public void setLeft(Character left) {
        this.left = left;
    }

    public Character getRight() {
        return right;
    }

    public void setRight(Character right) {
        this.right = right;
    }

    public String getNarratorText() {
        return narratorText;
    }

    public void setNarratorText(String narratorText) {
        this.narratorText = narratorText;
    }

    public String getLeftBubbleText() {
        return leftBubbleText;
    }

    public void setLeftBubbleText(String leftBubbleText) {
        this.leftBubbleText = leftBubbleText;
    }

    public String getRightBubbleText() {
        return rightBubbleText;
    }

    public void setRightBubbleText(String rightBubbleText) {
        this.rightBubbleText = rightBubbleText;
    }

    public Image getPanelShot() {
        return panelShot;
    }

    public void setPanelShot(Image panelShot) {
        this.panelShot = panelShot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}