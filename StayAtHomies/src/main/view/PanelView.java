package main.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PanelView extends ImageView {
    private int panelId;

    public PanelView(Image image){
        super(image);
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }
}
