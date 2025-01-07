package midas.model;

import java.util.List;

public class PhraseEntity {

    private String nameOfHero;
    private String rootToImg;
    private List<String> phrases;

    public PhraseEntity(String nameOfHero, String rootToImg, List<String> phrases) {
        this.nameOfHero = nameOfHero;
        this.rootToImg = rootToImg;
        this.phrases = phrases;
    }

    public String getNameOfHero() {
        return nameOfHero;
    }

    public void setNameOfHero(String nameOfHero) {
        this.nameOfHero = nameOfHero;
    }

    public String getRootToImg() {
        return rootToImg;
    }

    public void setRootToImg(String rootToImg) {
        this.rootToImg = rootToImg;
    }

    public List<String> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<String> phrases) {
        this.phrases = phrases;
    }

    @Override
    public String toString() {
        return "PhraseEntity{" +
                "nameOfHero='" + nameOfHero + '\'' +
                ", rootToImg='" + rootToImg + '\'' +
                ", phrases=" + phrases +
                '}';
    }
}
