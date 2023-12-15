package src.model;

public class Word {
    public String word_target;
    public String word_explain;

    public Word(String target, String explain) {
        this.word_target = target;
        this.word_explain = explain;
    }

    public String getWordTarget() {
        return word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }
}