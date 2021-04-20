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
        Panel rePanel = panels.remove(id);
        recomputeIds();
        return rePanel;
    }

    private void recomputeIds(){
        for(int i = 0; i < panels.size(); i++){
            Panel panel = panels.get(i);
            if(panel.getId() != i){
                panel.setId(i);
            }
        }
    }

    public Panel getPanel(int id){
        return panels.get(id);
    }

    public void setPanel(int id, Panel panel){
        panels.set(id, panel);
    }

    public int size(){
        return panels.size();
    }
}
