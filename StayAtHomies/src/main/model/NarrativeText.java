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

    public NarrativeText(String narrativeText, NarrativeTextWrap narrativeTextLine){
        this.narrativeText = narrativeText;
        this.narrativeTextWrap = narrativeTextLine;
    }

    public NarrativeText(NarrativeText narrativeText){
        this.narrativeText = narrativeText.narrativeText;
        this.narrativeTextWrap = narrativeText.narrativeTextWrap;
    }

    public String getNarrativeText() {
        return narrativeText;
    }

    public void setNarrativeText(String narrativeText) {
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
