import javafx.scene.paint.Color;

public class character
{
    private enum Gender{FEMALE, MALE}
    private enum Direction{LEFT, RIGHT}

    private Color hair;
    private Color lips;
    private Color skin;
    private Gender gender;
    private Direction direction;

    public Color getHair() {
        return hair;
    }

    public void setHair(Color hair) {
        this.hair = hair;
    }

    public Color getLips() {
        return lips;
    }

    public void setLips(Color lips) {
        this.lips = lips;
    }

    public Color getSkin() {
        return skin;
    }

    public void setSkin(Color skin) {
        this.skin = skin;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
