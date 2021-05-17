package main.project_enums;

import main.model.NarrativeText;

public enum NarrativeTextWrap {
    NOWRAP, WRAP;

    public static NarrativeTextWrap getWrapType(String type){
        if(type.equalsIgnoreCase("wrap")){
            return NarrativeTextWrap.WRAP;
        }
        else{
            return NarrativeTextWrap.NOWRAP;
        }
    }
}
