import java.util.ArrayList;

public class ComixStrip {
    private ArrayList<Panel> panels = new ArrayList<>();

    public void addPanel(Panel panel){
        panels.add(panel);
    }

    public Panel removePanel(Panel panel){
        if(panels.remove(panel)){
            return panel;
        }
        return null;
    }

    public Panel removePanel(int id){
        return panels.remove(id);
    }

    public Panel getPanel(int id){
        return panels.get(id);
    }

    public int size(){
        return panels.size();
    }
}
