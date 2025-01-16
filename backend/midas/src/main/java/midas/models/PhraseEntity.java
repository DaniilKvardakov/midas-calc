package midas.models;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import midas.annotations.Commented;

import java.util.List;

/**
 * Сущность фраз(о мидасе) героев.
 */
@Commented
@Schema
public class PhraseEntity {

    /**
     * Имя героя.
     */
    @Schema(description = "Имя героя")
    private String nameOfHero;
    /**
     * Путь к иконке героя.
     */
    @Schema(description = "Путь к файлу с иконкой.")
    private String rootToImg;
    /**
     * Список фраз героя.
     */
    @ArraySchema(
            schema = @Schema(description = "Фраза героя."),
            arraySchema = @Schema(description = "Список фраз героя.")
    )
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
