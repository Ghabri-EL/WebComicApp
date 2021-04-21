import javafx.scene.paint.Color;

public interface CharacterInterface {
    Color DEFAULT_FEMALE_HAIR_COLOR = Color.web("0xf0ff00ff");
    Color DEFAULT_MALE_HAIR_COLOR = Color.web("0xf9ff00ff");
    Color DEFAULT_SKIN_COLOR = Color.web("0xffe8d8ff");
    Color DEFAULT_LIPS_COLOR = Color.web("0xff0000ff");
    Color DEFAULT_RIBBON_COLOR = Color.web("0xecb4b5ff");
    Color DEFAULT_BODY_OUTLINE_COLOR = Color.web("0x000000");
    enum Gender{FEMALE, MALE}
    enum Direction{LEFT, RIGHT}

    void flipImage();
    void switchGenders();
    void hairChange(Color hairColor);
    void skinChange(Color skinColor);
}
