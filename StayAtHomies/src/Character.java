import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class Character
{
    private enum Gender{FEMALE, MALE}
    private enum Direction{LEFT, RIGHT}

    private Color maleHair;
    private Color femaleHair;
    private Color lips;
    private Color skin;
    private Gender gender;
    private Direction direction;
    private Image image;
    private Image femaleHairMask;
    private Image maleHairMask;
    private Image lipsMask;
    private Image skinMask;

    public Color getMaleHair() {
        return maleHair;
    }

    public void setMaleHair(Color maleHair) {
        this.maleHair = maleHair;
    }

    public Color getFemaleHair() {
        return femaleHair;
    }

    public void setFemaleHair(Color femaleHair) {
        this.femaleHair = femaleHair;
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
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getFemaleHairMask() {
        return femaleHairMask;
    }

    public void setFemaleHairMask(Image femaleHairMask) {
        this.femaleHairMask = femaleHairMask;
    }

    public Image getMaleHairMask() {
        return maleHairMask;
    }

    public void setMaleHairMask(Image maleHairMask) {
        this.maleHairMask = maleHairMask;
    }

    public Image getLipsMask() {
        return lipsMask;
    }

    public void setLipsMask(Image lipsMask) {
        this.lipsMask = lipsMask;
    }

    public Image getSkinMask() {
        return skinMask;
    }

    public void setSkinMask(Image skinMask) {
        this.skinMask = skinMask;
    }
}
