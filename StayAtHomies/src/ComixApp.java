import javafx.scene.image.Image;

//ComixApp.java represents the Model following the MVC pattern
public class ComixApp extends WorkingPane
{
    private Character selectedCharacter = null;
    private ComixStrip comixStrip = new ComixStrip();

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public ComixStrip getComixStrip() {
        return comixStrip;
    }

    public void setBubbleText(String text){
        if(leftCharSelected()){
            setLeftBubbleText(text);
        }
        else if(rightCharSelected()){
            setRightBubbleText(text);
        }
    }

    public void setBubbleType(BubbleType bubbleType){
        if(leftCharSelected()){
            setLeftBubbleType(bubbleType);
        }
        else if(rightCharSelected()){
            setRightBubbleType(bubbleType);
        }
    }

    public void selectCharacter(Selected select){
        if(select == Selected.LEFT){
            selectedCharacter = getCharacterLeft();
        }
        else{
            selectedCharacter = getCharacterRight();
        }
    }

    public boolean isCharacterSelected(){
        return selectedCharacter != null;
    }

    public int generateId(){
        setId(comixStrip.size());
        return getId();
    }

    public void resetWorkingSpace(){
        generateId();
        setPanelShot(null);
        setCharacterLeft(null);
        setCharacterRight(null);
        setLeftBubbleText(null);
        setRightBubbleText(null);
        setLeftBubbleType(BubbleType.NONE);
        setRightBubbleType(BubbleType.NONE);
        setNarrativeTextTop(null);
        setNarrativeTextBottom(null);
        resetSelectedCharacter();
    }

    public Panel createPanel(){
        //set the bubble type and text for each char before creating the panel
        getCharacterLeft().setBubbleText(getLeftBubbleText());
        getCharacterLeft().setBubbleType(getLeftBubbleType());
        getCharacterRight().setBubbleText(getRightBubbleText());
        getCharacterRight().setBubbleType(getRightBubbleType());
        //parameters: int id, Image panelShot, Character characterLeft, Character characterRight, String leftBubbleText, String rightBubbleText,
        //BubbleType leftBubbleType, BubbleType rightBubbleType, String narrativeTextTop, String narrativeTextBottom
        Panel newPanel = new Panel(getId(), getPanelShot(), getCharacterLeft(), getCharacterRight(),
                getLeftBubbleText(), getRightBubbleText(), getLeftBubbleType(), getRightBubbleType(),
                getNarrativeTextTop(), getNarrativeTextBottom());
        return newPanel;
    }

    public void createPanelAndAddToStrip(){
        Panel panel = createPanel();
        comixStrip.addPanel(panel);
    }

    public void editPanel(int id, Panel panel){
        comixStrip.setPanel(id, panel);
    }

    public boolean loadSelectedPanel(int id){
        Panel panel = comixStrip.getPanel(id);

        if(panel == null){
            return false;
        }
        //create a copy of the characters in the panel to prevent any
        //changes applied directly on the characters in the panel without
        //the user saving those changes
        Character leftChar = new Character(panel.getCharacterLeft());
        Character rightCharacter = new Character(panel.getCharacterRight());

        resetSelectedCharacter();
        setId(panel.getId());
        setCharacterLeft(leftChar);
        setCharacterRight(rightCharacter);
        setLeftBubbleText(panel.getLeftBubbleText());
        setRightBubbleText(panel.getRightBubbleText());
        setLeftBubbleType(panel.getLeftBubbleType());
        setRightBubbleType(panel.getRightBubbleType());
        setNarrativeTextTop(panel.getNarrativeTextTop());
        setNarrativeTextBottom(panel.getNarrativeTextBottom());
        setPanelShot(panel.getPanelShot());

        //testing the loading values to match with what's presented on screen
        System.out.println("LCHAR: "+ getCharacterLeft() + "\n" +
                        "RCHAR: "+ getCharacterRight() + "\n" +
                        "LBTxt: "+getCharacterLeft().getBubbleText() + "\n" +
                        "RBTxt: " + getCharacterRight().getBubbleText() + "\n" +
                        "LBTp: " + getCharacterLeft().getBubbleType() + "\n"+
                        "RBTp: " + getCharacterRight().getBubbleType() + "\n" +
                        "TNT: " + getNarrativeTextTop() + "\n" +
                        "BNT: " + getNarrativeTextBottom());
        return true;
    }

    public void deletePanel(int id){
        comixStrip.removePanel(id);
        resetWorkingSpace();
    }

    public boolean readyToCreate(){
        return getId() >= 0 && getCharacterLeft() != null && getCharacterRight() != null;
    }

    private void resetSelectedCharacter(){
        selectedCharacter = null;
    }

    private boolean leftCharSelected(){
        return selectedCharacter == getCharacterLeft();
    }

    private boolean rightCharSelected(){
        return selectedCharacter == getCharacterRight();
    }

    public void refreshSelectedCharacter(){
        //if the selected character was the left character and a new one was imported
        //then set the new model to selected
        if(isCharacterSelected()){
            if(getSelectedCharacter() != getCharacterRight()){
                setSelectedCharacter(getCharacterLeft());
            }
            else if(getSelectedCharacter() != getCharacterLeft()){
                setSelectedCharacter(getCharacterRight());
            }
        }
    }
}
