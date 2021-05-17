package main.model;

//model.ComixApp.java represents the Model following the MVC pattern
import main.project_enums.*;

public class ComixApp extends WorkingPane
{
    private Character selectedCharacter;
    private ComixStrip comixStrip;
    private String comicTitle;
    private String comicCredits;

    public ComixApp(){
        this.selectedCharacter = null;
        this.comixStrip = new ComixStrip();
        this.comicTitle = "HomiesComix";
        this.comicCredits = "HomiesComix";
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
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

    public void setComicTitle(String title){
        this.comicTitle = title;
    }

    public String getComicTitle(){
        return this.comicTitle;
    }

    public void setComicCredits(String credits) {
        this.comicCredits = credits;
    }

    public String getComicCredits() {
        return this.comicCredits;
    }

    private int generateId(){
        setId(comixStrip.size());
        return getId();
    }

    public boolean noPanels(){
        return comixStrip.getPanels().isEmpty();
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
        setNarrativeTextTop(new NarrativeText());
        setNarrativeTextBottom(new NarrativeText());
        resetSelectedCharacter();
    }

    public void clearComixStrip(){
        comixStrip.clearStrip();
    }

    public int getNumberOfPanels(){
        return comixStrip.size();
    }

    public Panel createPanel(){
        //parameters: int id, Image panelShot, model.main.model.Character characterLeft, model.main.model.Character characterRight, String leftBubbleText, String rightBubbleText,
        //main.project_enums.BubbleType leftBubbleType, main.project_enums.BubbleType rightBubbleType, String narrativeTextTop, String narrativeTextBottom
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
        NarrativeText topText = new NarrativeText(panel.getNarrativeTextTop());
        NarrativeText bottomText = new NarrativeText(panel.getNarrativeTextBottom());

        resetSelectedCharacter();
        setId(panel.getId());
        setCharacterLeft(leftChar);
        setCharacterRight(rightCharacter);
        setLeftBubbleText(panel.getLeftBubbleText());
        setRightBubbleText(panel.getRightBubbleText());
        setLeftBubbleType(panel.getLeftBubbleType());
        setRightBubbleType(panel.getRightBubbleType());
        setNarrativeTextTop(topText);
        setNarrativeTextBottom(bottomText);
        setPanelShot(panel.getPanelShot());
        return true;
    }

    //loads a panel and its elements in the working space
    public boolean loadPanel(Panel panel){
        if(panel == null){
            return false;
        }
        resetSelectedCharacter();
        setId(panel.getId());
        setCharacterLeft(panel.getCharacterLeft());
        setCharacterRight(panel.getCharacterRight());
        setLeftBubbleText(panel.getLeftBubbleText());
        setRightBubbleText(panel.getRightBubbleText());
        setLeftBubbleType(panel.getLeftBubbleType());
        setRightBubbleType(panel.getRightBubbleType());
        setNarrativeTextTop(panel.getNarrativeTextTop());
        setNarrativeTextBottom(panel.getNarrativeTextBottom());
        return true;
    }

    public void deletePanel(int id){
        comixStrip.removePanel(id);
        resetWorkingSpace();
    }

    public void changePanelPosition(int panelId, int newId){
        comixStrip.changePanelPosition(panelId, newId);
    }

    public boolean readyToCreate(){
        return getId() >= 0 && getCharacterLeft() != null && getCharacterRight() != null;
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

    private void resetSelectedCharacter(){
        selectedCharacter = null;
    }

    public boolean leftCharSelected(){
        if(!isCharacterSelected()){
            return false;
        }
        return selectedCharacter == getCharacterLeft();
    }

    public boolean rightCharSelected(){
        if(!isCharacterSelected()){
            return false;
        }
        return selectedCharacter == getCharacterRight();
    }
}
