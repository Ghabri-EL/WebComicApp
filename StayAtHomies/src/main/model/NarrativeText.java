package main.model;

import main.project_enums.NarrativeTextWrap;

public class NarrativeText {
    String narrativeText;
    NarrativeTextWrap narrativeTextWrap;

    public NarrativeText(){
        this.narrativeTextWrap = NarrativeTextWrap.NOWRAP;
    }
    public NarrativeText(String narrativeText){
        this.narrativeText = narrativeText;
        this.narrativeTextWrap = NarrativeTextWrap.NOWRAP;
    }

    public NarrativeText(NarrativeText narrativeText){
        this.narrativeText = narrativeText.narrativeText;
        this.narrativeTextWrap = narrativeText.narrativeTextWrap;
    }

    public String getNarrativeText() {
        return narrativeText;
    }

    public void setNarrativeText(String narrativeText) {
        if(narrativeText != null){
            narrativeText = (narrativeText.length() <= 300 ? narrativeText : narrativeText.substring(0 , 300));
        }
        this.narrativeText = narrativeText;
    }

    public NarrativeTextWrap getNarrativeTextWrap() {
        return narrativeTextWrap;
    }

    public void setNarrativeTextWrap(NarrativeTextWrap narrativeTextWrap) {
        this.narrativeTextWrap = narrativeTextWrap;
    }

    @Override
    public String toString() {
        return "NarrativeText{" +
                "narrativeText='" + narrativeText + '\'' +
                ", narrativeTextWrap=" + narrativeTextWrap +
                '}';
    }
}
