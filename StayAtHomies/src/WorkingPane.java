import javafx.scene.image.Image;

public abstract class WorkingPane {
    private int id;
    private Image panelShot;
    private Character characterLeft;
    private Character characterRight;
    private String leftBubbleText;
    private String rightBubbleText;
    private BubbleType leftBubbleType = BubbleType.NONE;
    private BubbleType rightBubbleType = BubbleType.NONE;
    private String narrativeTextTop;
    private String narrativeTextBottom;

    public WorkingPane(){};

    public WorkingPane(int id, Image panelShot, Character characterLeft, Character characterRight, String leftBubbleText, String rightBubbleText,
                       BubbleType leftBubbleType, BubbleType rightBubbleType, String narrativeTextTop, String narrativeTextBottom){
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
        this.leftBubbleText = leftBubbleText;
    }

    public String getRightBubbleText() {
        return rightBubbleText;
    }

    public void setRightBubbleText(String rightBubbleText) {
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

    public String getNarrativeTextTop() {
        return narrativeTextTop;
    }

    public void setNarrativeTextTop(String narrativeTextTop) {
        this.narrativeTextTop = narrativeTextTop;
    }

    public String getNarrativeTextBottom() {
        return narrativeTextBottom;
    }

    public void setNarrativeTextBottom(String narrativeTextBottom) {
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
                ", narrativeTextTop='" + narrativeTextTop + '\'' +
                ", narrativeTextBottom='" + narrativeTextBottom + '\'' +
                '}';
    }
}
