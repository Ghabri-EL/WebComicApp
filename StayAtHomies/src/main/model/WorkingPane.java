package main.model;
import javafx.scene.image.Image;
import main.project_enums.BubbleType;

public abstract class WorkingPane {
    private int id;
    private Image panelShot;
    private Character characterLeft;
    private Character characterRight;
    private String leftBubbleText;
    private String rightBubbleText;
    private BubbleType leftBubbleType;
    private BubbleType rightBubbleType;
    private NarrativeText narrativeTextTop;
    private NarrativeText narrativeTextBottom;

    public WorkingPane(){
        this.leftBubbleType = BubbleType.NONE;
        this.rightBubbleType = BubbleType.NONE;
        this.narrativeTextTop = new NarrativeText();
        this.narrativeTextBottom = new NarrativeText();
    };

    public WorkingPane(int id, Image panelShot, Character characterLeft, Character characterRight, String leftBubbleText, String rightBubbleText,
                       BubbleType leftBubbleType, BubbleType rightBubbleType, NarrativeText narrativeTextTop, NarrativeText narrativeTextBottom){
        this.id = id;
        this.panelShot = panelShot;
        this.characterLeft = characterLeft;
        this.characterRight = characterRight;
        this.leftBubbleText = leftBubbleText;
        this.rightBubbleText = rightBubbleText;
        this.leftBubbleType = leftBubbleType;
        this.rightBubbleType = rightBubbleType;
        this.narrativeTextTop = narrativeTextTop;
        this.narrativeTextBottom = narrativeTextBottom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getPanelShot() {
        return panelShot;
    }

    public void setPanelShot(Image panelShot) {
        this.panelShot = panelShot;
    }

    public Character getCharacterLeft() {
        return characterLeft;
    }

    public void setCharacterLeft(Character characterLeft) {
        this.characterLeft = characterLeft;
    }

    public Character getCharacterRight() {
        return characterRight;
    }

    public void setCharacterRight(Character characterRight) {
        this.characterRight = characterRight;
    }

    public String getLeftBubbleText() {
        return leftBubbleText;
    }

    public void setLeftBubbleText(String leftBubbleText) {
        if(leftBubbleText != null){
            leftBubbleText = (leftBubbleText.length() < 250 ? leftBubbleText : leftBubbleText.substring(0 , 250));
        }
        this.leftBubbleText = leftBubbleText;
    }

    public String getRightBubbleText() {
        return rightBubbleText;
    }

    public void setRightBubbleText(String rightBubbleText) {
        if(rightBubbleText != null){
            rightBubbleText = (rightBubbleText.length() < 250 ? rightBubbleText : rightBubbleText.substring(0 , 250));
        }
        this.rightBubbleText = rightBubbleText;
    }

    public BubbleType getLeftBubbleType() {
        return leftBubbleType;
    }

    public void setLeftBubbleType(BubbleType leftBubbleType) {
        this.leftBubbleType = leftBubbleType;
    }

    public BubbleType getRightBubbleType() {
        return rightBubbleType;
    }

    public void setRightBubbleType(BubbleType rightBubbleType) {
        this.rightBubbleType = rightBubbleType;
    }

    public NarrativeText getNarrativeTextTop() {
        return narrativeTextTop;
    }

    public void setNarrativeTextTop(NarrativeText narrativeTextTop) {
        if(narrativeTextTop == null){
            narrativeTextTop = new NarrativeText();
        }
        this.narrativeTextTop = narrativeTextTop;
    }

    public NarrativeText getNarrativeTextBottom() {
        return narrativeTextBottom;
    }

    public void setNarrativeTextBottom(NarrativeText narrativeTextBottom) {
        if(narrativeTextBottom == null){
            narrativeTextBottom = new NarrativeText();
        }
        this.narrativeTextBottom = narrativeTextBottom;
    }

    @Override
    public String toString() {
        return "WorkingPane{" +
                "id=" + id +
                ", panelShot=" + panelShot +
                ", characterLeft=" + characterLeft +
                ", characterRight=" + characterRight +
                ", leftBubbleText='" + leftBubbleText + '\'' +
                ", rightBubbleText='" + rightBubbleText + '\'' +
                ", leftBubbleType=" + leftBubbleType +
                ", rightBubbleType=" + rightBubbleType +
                ", narrativeTextTop=" + narrativeTextTop +
                ", narrativeTextBottom=" + narrativeTextBottom +
                '}';
    }
}
