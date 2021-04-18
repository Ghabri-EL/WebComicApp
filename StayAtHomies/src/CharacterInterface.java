import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface CharacterInterface {
    Color DEFAULT_FEMALE_HAIR_COLOR = Color.web("0xf0ff00ff");
    Color DEFAULT_MALE_HAIR_COLOR = Color.web("0xf9ff00ff");
    Color DEFAULT_SKIN_COLOR = Color.web("0xffe8d8ff");
    Color DEFAULT_LIPS_COLOR = Color.web("0xff0000ff");
    Color DEFAULT_RIBBON_COLOR = Color.web("0xecb4b5ff");
    enum Gender{FEMALE, MALE}
    enum Direction{LEFT, RIGHT}

    public void flipImage();
    public void switchGenders();
    public void hairChange(Color hairColor);
    public void skinChange(Color skinColor);
}
