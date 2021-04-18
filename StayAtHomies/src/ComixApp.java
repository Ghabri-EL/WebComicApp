import java.util.ArrayList;

//ComixApp.java represents the Model following the MVC pattern
public class ComixApp {
    private Character characterLeft = null;
    private Character characterRight = null;
    private Character selectedCharacter = null;
    private String narrativeTextTop;
    private String narrativeTextBottom;
    private ArrayList<Panels> panelList = new ArrayList<Panels>();

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

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
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

    public ArrayList<Panels> getPanelList() {
        return panelList;
    }

    public void selectCharacter(Selected select){
        if(select == Selected.LEFT){
            selectedCharacter = characterLeft;
        }
        else{
            selectedCharacter = characterRight;
        }
    }

    public boolean isCharacterSelected(){
        return selectedCharacter != null;
    }
}
