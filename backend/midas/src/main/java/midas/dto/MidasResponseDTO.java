package midas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import midas.annotations.NoCommentsNeeded;
import midas.models.MidasResponse;
import midas.models.Status;

/**
 * @see MidasResponse
 */
@NoCommentsNeeded
@Schema(description = "Сущность ответа.")
public class MidasResponseDTO {

    @Schema(description = "Статус ответа.")
    private Status status;
    @Schema(description = "Краткое сообщение ответа, помогающее определить что нужно в случае неудачного запроса.")
    private String message;
    @Schema(description = "Сколько раз был использован мидас за матч.")
    private Integer usesCounter;
    @Schema(description = "Получено денег с помощью мидаса до конца матча.")
    private int profitUntilEnd;
    @Schema(description = "Получено денег с помощью мидаса после его продажи.")
    private int profitAfterSale;
    @Schema(description = "Окупился ли мидас.")
    @JsonProperty("paidOf")
    private boolean isPaidOf;
    @Schema(description = "Время в минутах, когда мидас окупился.")
    private Long timeOfPayback;

    @Override
    public String toString() {
        return "MidasResponseDTO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", usesCounter=" + usesCounter +
                ", profitUntilEnd=" + profitUntilEnd +
                ", profitAfterSale=" + profitAfterSale +
                ", isPaidOf=" + isPaidOf +
                ", timeOfPayback=" + timeOfPayback +
                '}';
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUsesCounter() {
        return usesCounter;
    }

    public void setUsesCounter(Integer usesCounter) {
        this.usesCounter = usesCounter;
    }

    public int getProfitUntilEnd() {
        return profitUntilEnd;
    }

    public void setProfitUntilEnd(int profitUntilEnd) {
        this.profitUntilEnd = profitUntilEnd;
    }

    public int getProfitAfterSale() {
        return profitAfterSale;
    }

    public void setProfitAfterSale(int profitAfterSale) {
        this.profitAfterSale = profitAfterSale;
    }

    public boolean isPaidOf() {
        return isPaidOf;
    }

    public void setPaidOf(boolean paidOf) {
        isPaidOf = paidOf;
    }

    public Long getTimeOfPayback() {
        return timeOfPayback;
    }

    public void setTimeOfPayback(Long timeOfPayback) {
        this.timeOfPayback = timeOfPayback;
    }

}
