package midas.models;

import midas.annotations.Commented;

import java.util.List;

/**
 * Сущность фраз(о мидасе) героев.
 */
@Commented
public class PhraseEntity {

    /**
     * Имя героя.
     */
    private String nameOfHero;
    /**
     * Путь к иконке героя.
     */
    private String rootToImg;
    /**
     * Список фраз героя.
     */
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
