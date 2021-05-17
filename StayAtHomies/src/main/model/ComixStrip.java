package main.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class ComixStrip {
    private ArrayList<Panel> panels = new ArrayList<>();

    public void addPanel(Panel panel){
        panels.add(panel);
    }

    public void changePanelPosition(int panelId, int newId){
        Panel panel = panels.remove(panelId);
        panels.add(newId, panel);
        recomputeIds();
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

    public void clearStrip(){
        panels.clear();
    }

    public Panel getPanel(int id){
        return panels.get(id);
    }

    public ArrayList<Panel> getPanels(){
        return panels;
    }

    public void setPanel(int id, Panel panel){
        panels.set(id, panel);
    }

    public int size(){
        return panels.size();
    }

    public ArrayList<Image> sendSnapshot()  //This Fills ArrayList with Snapshots of the Panels and sends to controller
    {
        ArrayList<Image> picSender = new ArrayList<>();

        for(int i=0; i<panels.size(); i++)
        {
           Panel test = getPanel(i);
           picSender.add(test.getPanelShot());
        }

        return picSender;
    }
}
