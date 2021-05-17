package main.view;

import javafx.scene.image.Image;

public interface ViewDefaultValues {
    double SCENE_WIDTH = 1300;
    double SCENE_HEIGHT = 850;
    double WORKING_PANE_WIDTH = 610;
    double WORKING_PANE_HEIGHT = 600;
    double BUBBLE_WIDTH = 290;
    double BUBBLE_HEIGHT = 220;
    double CHARACTER_VIEW_SIZE = 300;
    double COMIC_STRIP_PANE_HEIGHT = 160;
    double PANEL_SIZE = COMIC_STRIP_PANE_HEIGHT - 10;
    double LEFT_BUTTONS_PANEL_WIDTH = 200;
    Image THOUGHT_BUBBLE_IMAGE = new Image("/resources/thoughtBubble.png");
    Image SPEECH_BUBBLE_IMAGE = new Image("/resources/speechBubble.png");
    String APP_THEME_COLOR = "#003f63";
    String APP_THEME_COLOR_SCENE = "#04263b";
    String BORDER_COLOR_LIGHT = "rgba(240, 240, 240, 0.2)";
    String BORDER_COLOR_DARK = "rgba(0, 0, 0, 0.5)";
}
