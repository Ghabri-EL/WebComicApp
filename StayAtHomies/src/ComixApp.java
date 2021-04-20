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
        selectedCharacter.setBubbleText(text);
    }

    public void setBubbleType(BubbleType bubbleType){
        selectedCharacter.setBubbleType(bubbleType);
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
        setNarrativeTextTop(null);
        setNarrativeTextBottom(null);
        selectedCharacter = null;
    }

    public Panel createPanel(){
        Panel newPanel = new Panel(getId(), getPanelShot(), getCharacterLeft(), getCharacterRight(),
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

    public void loadSelectedPanel(Panel panel){
        setId(panel.getId());
        setCharacterLeft(panel.getCharacterLeft());
        setCharacterRight(panel.getCharacterRight());
        setNarrativeTextTop(panel.getNarrativeTextTop());
        setNarrativeTextBottom(panel.getNarrativeTextBottom());
        setPanelShot(panel.getPanelShot());
    }

    public void deletePanel(int id){
        comixStrip.removePanel(id);
    }

    public boolean readyToCreate(){
        return getId() >= 0 && getCharacterLeft() != null && getCharacterRight() != null;
    }
}
