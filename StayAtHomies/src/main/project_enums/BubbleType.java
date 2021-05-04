package main.project_enums;

public enum BubbleType {
    NONE, SPEECH, THOUGHT;

    public static BubbleType getBubble(String bubble){
        if(bubble.equalsIgnoreCase("speech")){
            return BubbleType.SPEECH;
        }
        else if(bubble.equalsIgnoreCase("thought")){
            return BubbleType.THOUGHT;
        }
        else{
            return BubbleType.NONE;
        }
    }
}
