import javafx.scene.image.Image;

public abstract class WorkingPane {
    private int id;
    private Image panelShot;
    private Character characterLeft;
    private Character characterRight;
    private String narrativeTextTop;
    private String narrativeTextBottom;
    private String leftBubbleText;
    private String rightBubbleText;

    public WorkingPane(){};

    public WorkingPane(int id, Image panelShot, Character characterLeft, Character characterRight, String narrativeTextTop,
                       String narrativeTextBottom, String leftBubbleText, String rightBubbleText){
        this.id = id;
        this.panelShot = panelShot;
        this.characterLeft = characterLeft;
        this.characterRight = characterRight;
        this.narrativeTextTop = narrativeTextTop;
        this.narrativeTextBottom = narrativeTextBottom;
        this.leftBubbleText = leftBubbleText;
        this.rightBubbleText = rightBubbleText;
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

    public boolean readyToCreate(){
        return id >= 0 && panelShot != null && (characterLeft != null || characterRight != null);
    }
}
