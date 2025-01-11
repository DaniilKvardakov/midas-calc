package midas.dto;

import midas.annotations.NoCommentsNeeded;
import midas.models.MidasResponse;
import midas.models.Status;

/**
 * @see MidasResponse
 */
@NoCommentsNeeded
public class MidasResponseDTO {

    private Status status;
    private String message;

    private Integer usesCounter;
    private int profitUntilEnd;
    private int profitAfterSale;
    private boolean isPaidOf;
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
