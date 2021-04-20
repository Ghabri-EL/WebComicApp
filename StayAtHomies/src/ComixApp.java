import javafx.scene.control.Label;
import javafx.scene.image.Image;

//ComixApp.java represents the Model following the MVC pattern
public class ComixApp extends WorkingPane
{
    private Character selectedCharacter = null;
    private ComixStrip panelList = new ComixStrip();

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public ComixStrip getPanelList() {
        return panelList;
    }

    public void setBubbleText(String text){
        selectedCharacter.setBubbleText(text);
        if(selectedCharacter == getCharacterLeft()){
            setLeftBubbleText(selectedCharacter.getBubbleText());
        }
        else if(selectedCharacter == getCharacterRight()){
            setRightBubbleText(selectedCharacter.getBubbleText());
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
        setId(panelList.size());
        return getId();
    }

    public void createPanel(){
        Panel newPanel = new Panel(getId(), getPanelShot(), getCharacterLeft(), getCharacterRight(),
                getNarrativeTextTop(), getNarrativeTextBottom(), getLeftBubbleText(), getRightBubbleText());
        panelList.addPanel(newPanel);
    }
}
