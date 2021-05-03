package main.model;

import javafx.scene.paint.Color;

public interface CharacterInterface {
    enum Gender{FEMALE, MALE}
    enum Orientation {LEFT, RIGHT}

    void flipImage();
    void switchGenders();
    void hairChange(Color hairColor);
    void skinChange(Color skinColor);
    void changeLipsColor(Color lipsColor);
    Color getHair();
    Color getLips();
    Gender getGender();
    Orientation getOrientation();
    String getPose();
}
