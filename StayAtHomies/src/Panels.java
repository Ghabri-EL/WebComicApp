import javafx.scene.control.*;
public class Panels
{
    private Character left;
    private Character right;
    private Label narratorText;
    private Label leftBubbleText;
    private Label rightBubbleText;
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

    public Label getNarratorText() {
        return narratorText;
    }

    public void setNarratorText(Label narratorText) {
        this.narratorText = narratorText;
    }

    public Label getLeftBubbleText() {
        return leftBubbleText;
    }

    public void setLeftBubbleText(Label leftBubbleText) {
        this.leftBubbleText = leftBubbleText;
    }

    public Label getRightBubbleText() {
        return rightBubbleText;
    }

    public void setRightBubbleText(Label rightBubbleText) {
        this.rightBubbleText = rightBubbleText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}