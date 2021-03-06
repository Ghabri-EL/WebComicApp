package main.model;

import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class Character implements CharacterInterface, DefaultColors
{
    private Color hair;
    private Color lips;
    private Color skin;
    private Gender gender;
    private Orientation orientation;
    private Image characterImage;
    private Image femaleHairMask;
    private Image maleHairMask;
    private Image lipsMask;
    private Image skinMask;
    private Image bodyOutlineMask;
    private String pose;

    public Character(Image characterImage, String pose){
        this.skin = DEFAULT_SKIN_COLOR;
        this.gender = Gender.FEMALE;
        this.hair = DEFAULT_FEMALE_HAIR_COLOR;
        this.lips = DEFAULT_LIPS_COLOR;
        this.orientation = Orientation.RIGHT;
        this.pose = pose;
        setCharacterImageAndMasks(characterImage);
    }

    //creates a clone of the given character
    public Character(Character baseCharacter){
        this.hair = baseCharacter.hair;
        this.lips = baseCharacter.lips;
        this.skin = baseCharacter.skin;
        this.gender = baseCharacter.gender;
        this.orientation = baseCharacter.orientation;
        this.characterImage = baseCharacter.characterImage;
        this.femaleHairMask = baseCharacter.femaleHairMask;
        this.maleHairMask = baseCharacter.maleHairMask;
        this.lipsMask = baseCharacter.lipsMask;
        this.skinMask = baseCharacter.skinMask;
        this.bodyOutlineMask = baseCharacter.bodyOutlineMask;
        this.pose = baseCharacter.pose;
    }

    public Color getHair() {
        return hair;
    }

    public Color getLips() {
        return lips;
    }

    public Color getSkin() {
        return skin;
    }

    public Gender getGender() {
        return gender;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Image getCharacterImage() {
        return characterImage;
    }

    private void setCharacterImageAndMasks(Image image) {
        this.characterImage = image;
        setMasks();
    }

    public String getPose() {
        return pose;
    }

    public boolean isFemale(){
        return Gender.FEMALE == gender;
    }

    public void flipImage(){
        characterImage = flip(characterImage);
        femaleHairMask = flip(femaleHairMask);
        maleHairMask = flip(maleHairMask);
        lipsMask = flip(lipsMask);
        skinMask = flip(skinMask);
        bodyOutlineMask = flip(bodyOutlineMask);

        if(orientation == Orientation.RIGHT){
            orientation = Orientation.LEFT;
        }
        else{
            orientation = Orientation.RIGHT;
        }
    }

    private Image flip(Image image)
    {
        int width = (int) image.getWidth();
        int height = (int)image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        WritableImage flippedImage = new WritableImage(width,  height);
        PixelWriter pixelWriter = flippedImage.getPixelWriter();

        // x y axis of the image
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixelWriter.setArgb(x, y, pixelReader.getArgb((width - 1) - x, y));
            }
        }
        return flippedImage;
    }

    public void switchGenders(){
        int width = (int)characterImage.getWidth();
        int height = (int)characterImage.getHeight();

        PixelReader pixelReaderImage = characterImage.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        PixelReader pixelReaderFemHairMask = femaleHairMask.getPixelReader();
        PixelReader pixelReaderMaleHairMask = maleHairMask.getPixelReader();
        PixelReader pixelReaderLipsMask = lipsMask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color femHairPixel = pixelReaderFemHairMask.getColor(x,y);
                Color maleHairPixel = pixelReaderMaleHairMask.getColor(x,y);
                Color lipsPixel = pixelReaderLipsMask.getColor(x,y);

                if(gender == Gender.FEMALE){
                    if(!femHairPixel.equals(Color.TRANSPARENT) && maleHairPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, Color.WHITE);
                    }

                    if(!lipsPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, DEFAULT_MALE_LIPS_COLOR);
                    }
                }
                else{
                    if(!femHairPixel.equals(Color.TRANSPARENT)){
                        if(femHairPixel.equals(DEFAULT_RIBBON_COLOR)){
                            pixelWriterImage.setColor(x, y, DEFAULT_RIBBON_COLOR);
                        }
                        else{
                            pixelWriterImage.setColor(x, y, hair);
                        }
                    }

                    if(!lipsPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, lips);
                    }
                }
            }
        }
        characterImage = correctBodyOutline(newImage);

        if(gender == Gender.FEMALE){
            gender = Gender.MALE;
        }
        else{
            gender = Gender.FEMALE;
        }
    }
    public void hairChange(Color hairColor){
        int width = (int)characterImage.getWidth();
        int height = (int)characterImage.getHeight();

        PixelReader pixelReaderImage = characterImage.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        Image mask = (gender == Gender.FEMALE) ? femaleHairMask : maleHairMask;

        PixelReader pixelReaderMask = mask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReaderMask.getColor(x,y);

                if(!pixel.equals(Color.TRANSPARENT) && !pixel.equals(DEFAULT_RIBBON_COLOR)){
                    pixelWriterImage.setColor(x, y, hairColor);
                }
            }
        }
        hair = hairColor;
        characterImage = correctBodyOutline(newImage);
    }

    public void skinChange(Color skinColor){
        int width = (int)characterImage.getWidth();
        int height = (int)characterImage.getHeight();

        PixelReader pixelReaderImage = characterImage.getPixelReader();
        WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

        PixelReader pixelReaderSkinMask = skinMask.getPixelReader();
        PixelWriter pixelWriterImage = newImage.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReaderSkinMask.getColor(x,y);

                if(!pixel.equals(Color.TRANSPARENT)){
                    pixelWriterImage.setColor(x, y, skinColor);
                }
            }
        }
        skin = skinColor;
        characterImage = correctBodyOutline(newImage);
    }

    public void changeLipsColor(Color lipsColor){
        if(gender == Gender.FEMALE){

            int width = (int)characterImage.getWidth();
            int height = (int)characterImage.getHeight();

            PixelReader pixelReaderImage = characterImage.getPixelReader();
            WritableImage newImage = new WritableImage(pixelReaderImage, width, height);

            PixelReader pixelReaderLipsMask = lipsMask.getPixelReader();
            PixelWriter pixelWriterImage = newImage.getPixelWriter();

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    Color lipsPixel = pixelReaderLipsMask.getColor(x, y);

                    if(!lipsPixel.equals(Color.TRANSPARENT)){
                        pixelWriterImage.setColor(x, y, lipsColor);
                    }
                }
            }
            lips = lipsColor;
            characterImage = correctBodyOutline(newImage);
        }
    }

    private void setMasks(){
        int width = (int)characterImage.getWidth();
        int height = (int)characterImage.getHeight();

        PixelReader pixelReader = characterImage.getPixelReader();
        WritableImage femaleHairMask = new WritableImage(width,  height);   //female hair mask
        PixelWriter pixelWriterFHM = femaleHairMask.getPixelWriter();

        WritableImage maleHairMask = new WritableImage(width, height);     //male hair mask
        PixelWriter pixelWriterMHM = maleHairMask.getPixelWriter();

        WritableImage lipsMask = new WritableImage(width,  height);         //lip mask
        PixelWriter pixelWriterLM = lipsMask.getPixelWriter();

        WritableImage bodyMask = new WritableImage(width,  height);         //skin mask
        PixelWriter pixelWriterBM = bodyMask.getPixelWriter();

        WritableImage bodyOutlineMask = new WritableImage(width,  height);         //skin mask
        PixelWriter pixelWriterBOM = bodyOutlineMask.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReader.getColor(x, y);
                if(pixel.equals(DEFAULT_FEMALE_HAIR_COLOR) || pixel.equals(DEFAULT_RIBBON_COLOR) || pixel.equals(DEFAULT_MALE_HAIR_COLOR)){
                    pixelWriterFHM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterFHM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_MALE_HAIR_COLOR)){
                    pixelWriterMHM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterMHM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_LIPS_COLOR)){
                    pixelWriterLM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterLM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_SKIN_COLOR)){
                    pixelWriterBM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterBM.setColor(x, y, Color.TRANSPARENT);
                }

                if(pixel.equals(DEFAULT_BODY_OUTLINE_COLOR)){
                    pixelWriterBOM.setColor(x, y, pixel);
                }
                else{
                    pixelWriterBOM.setColor(x, y, Color.TRANSPARENT);
                }
            }
        }

        //the int parameters are used to optimize the anti aliasing correction
        this.maleHairMask = correctMaskEdges(maleHairMask, 5);
        this.femaleHairMask = correctMaskEdges(femaleHairMask, 5);
        //combined these two masks again as there were a lot of antialiasing artefacts in the female hair color
        // due to the difference in color and antialiasing
        this.femaleHairMask = combineMasks(this.femaleHairMask, this.maleHairMask);
        this.lipsMask = correctMaskEdges(lipsMask, 4);
        this.skinMask = correctMaskEdges(bodyMask, 2);
        //in some images, originally the skin to overlaps the hair color, hence subtracted the
        //hair color pixels from the skin mask
        this.skinMask = subtractMasks(this.skinMask, this.maleHairMask);
        this.bodyOutlineMask = correctMaskEdges(bodyOutlineMask, 1);
    }

    //nPixels is the number of pixels by which the edge of the mask will be extended
    private Image correctMaskEdges(WritableImage mask, int nPixels){
        if(nPixels < 0){
            return mask;
        }

        int width = (int)mask.getWidth();
        int height = (int)mask.getHeight();

        PixelReader pixelReaderMask = mask.getPixelReader();
        WritableImage newMask = new WritableImage(pixelReaderMask, width, height);
        PixelWriter pixelWriterMask = newMask.getPixelWriter();

        if(nPixels > 5){
            nPixels = 5;
        }

        int retain = 3;
        //the for loops start with N pixels to width & height - N pixels to avoid out of bounds
        //this approach ignores up to 5 pixels on each of the image's sides and applies the effect
        // to the rest of the pixels based on conditionals
        for(int y = nPixels; y < height - nPixels; y++){
            for(int x = nPixels; x < width - nPixels; x++){
                Color pixel = pixelReaderMask.getColor(x, y);
                if(!pixel.equals(Color.TRANSPARENT)){

                    //this for loop iterates to find an edge pixel and add n pixels to the edge
                    //the x-3-i condition is to retain any transparent lines which are outlines
                    // of body characteristics like the chin or an arm, retains at least 3 pixels
                    for(int i = 1; i < nPixels; i++){

                        if( !(x-retain-i < 0 || x+retain+i >= width || y-retain-i < 0 || y+retain+i >= height)){
                            if(pixelReaderMask.getColor(x-i, y).equals(Color.TRANSPARENT) && pixelReaderMask.getColor(x-retain-i, y).equals(Color.TRANSPARENT)){
                                pixelWriterMask.setColor(x-i, y, pixel);
                            }
                            else if(pixelReaderMask.getColor(x+i, y).equals(Color.TRANSPARENT) && pixelReaderMask.getColor(x+retain+i, y).equals(Color.TRANSPARENT)){
                                pixelWriterMask.setColor(x+i, y, pixel);
                            }
                            else if(pixelReaderMask.getColor(x, y-i).equals(Color.TRANSPARENT) && pixelReaderMask.getColor(x, y-retain-i).equals(Color.TRANSPARENT)){
                                pixelWriterMask.setColor(x, y-i, pixel);
                            }
                            else if(pixelReaderMask.getColor(x, y+i).equals(Color.TRANSPARENT) && pixelReaderMask.getColor(x, y+retain+i).equals(Color.TRANSPARENT)){
                                pixelWriterMask.setColor(x, y+i, pixel);
                            }
                        }
                    }
                }
            }
        }
        return newMask;
    }

    // applies visible pixels in mask2 over mask1 and returns the modified mask1(merging 2 masks)
    private Image combineMasks(Image mask1, Image mask2){
        int width = (int)mask1.getWidth();
        int height = (int)mask1.getHeight();

        PixelReader pixelReaderMask1 = mask1.getPixelReader();
        PixelReader pixelReaderMask2 = mask2.getPixelReader();
        WritableImage newMask = new WritableImage(pixelReaderMask1, width, height);
        PixelWriter pixelWriterMask = newMask.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixelMask2 = pixelReaderMask2.getColor(x, y);

                if(!pixelMask2.equals(Color.TRANSPARENT)){
                    pixelWriterMask.setColor(x, y, pixelMask2);
                }
            }
        }
        return newMask;
    }

    // take 2 masks, subtracts mask 2 from mask 1 and return modified mask 1
    private Image subtractMasks(Image mask1, Image mask2){
        int width = (int)mask1.getWidth();
        int height = (int)mask1.getHeight();

        PixelReader pixelReaderMask1 = mask1.getPixelReader();
        PixelReader pixelReaderMask2 = mask2.getPixelReader();
        WritableImage newMask = new WritableImage(pixelReaderMask1, width, height);
        PixelWriter pixelWriterMask = newMask.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixelMask2 = pixelReaderMask2.getColor(x, y);

                if(!pixelMask2.equals(Color.TRANSPARENT)){
                    pixelWriterMask.setColor(x, y, Color.TRANSPARENT);
                }
            }
        }
        return newMask;
    }

    //body outline and limbs were eroded in certain places due to the
    //correctMaskEdges method trying to correct anti aliasing artefacts by extending the edges
    private Image correctBodyOutline(WritableImage image){
        int width = (int)characterImage.getWidth();
        int height = (int)characterImage.getHeight();

        PixelReader pixelReaderOutlineMask = bodyOutlineMask.getPixelReader();
        PixelWriter pixelWriterImage = image.getPixelWriter();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Color pixel = pixelReaderOutlineMask.getColor(x, y);

                if(!pixel.equals(Color.TRANSPARENT)){
                    pixelWriterImage.setColor(x, y, pixel);
                }
            }
        }
        return image;
    }
}

