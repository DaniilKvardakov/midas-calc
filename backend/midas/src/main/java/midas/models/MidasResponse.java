package midas.models;

import midas.annotations.Commented;
import org.springframework.context.annotation.PropertySource;

/**
 * Сущность ответа при запросе.
 */
@Commented
@PropertySource("classpath:secret/midas.properties")
public class MidasResponse {

    /**
     * Заглушка для "плохого ответа".
     */
    public static final MidasResponse BAD_MIDAS_RESPONSE = new MidasResponse();
    /**
     * Статус ответа к запросы с клиента.
     */
    private Status status;
    /**
     * Краткое сообщение к ответу запроса с клиента.
     */
    private String message;
    /**
     * Сколько раз игрок использовал мидас.
     */
    private Integer usesCounter;
    /**
     * Получено денег до конца матча.
     */
    private long profitUntilEnd;
    /**
     * Получено денег до продажи мидаса.
     */
    private long profitAfterSale;
    /**
     * Окупился ли мидас.
     */
    private Boolean isPaidOf;
    /**
     * Время когда мидас окупил себя.
     */
    private Long timeOfPayback; // In minutes.

    private MidasResponse() {
    }

    public MidasResponse(long profitUntilEnd, long profitAfterSale, Long timeOfPayback) {
        this.profitUntilEnd = profitUntilEnd;
        this.profitAfterSale = profitAfterSale;
        this.timeOfPayback = timeOfPayback;
    }

    public MidasResponse(Integer usesCounter, long profitUntilEnd, long profitAfterSale, Long timeOfPayback, Boolean isPaidOf) {
        this.usesCounter = usesCounter;
        this.profitUntilEnd = profitUntilEnd;
        this.profitAfterSale = profitAfterSale;
        this.isPaidOf = isPaidOf;
        this.timeOfPayback = timeOfPayback;
    }

    @Override
    public String toString() {
        return "\nMidasResponse{" +
                " \nprofitUntilEnd=" + profitUntilEnd +
                ", \nprofitAfterSale=" + profitAfterSale +
                ", \nisPaidOf=" + isPaidOf +
                ", \ntimeOfPayback=" + timeOfPayback +
                "\n}";
    }

    /**
     * Метод формирующий краткое сообщение к ответу.
     *
     * @param status статус ответа.
     * @return сущность ответа при запросе.
     * @see Status
     */
    public MidasResponse statusAndMessage(Status status) {
        this.status = status;
        this.message = status.getMessage();
        return this;
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

    public long getProfitUntilEnd() {
        return profitUntilEnd;
    }

    public void setProfitUntilEnd(long profitUntilEnd) {
        this.profitUntilEnd = profitUntilEnd;
    }

    public long getProfitAfterSale() {
        return profitAfterSale;
    }

    public void setProfitAfterSale(long profitAfterSale) {
        this.profitAfterSale = profitAfterSale;
    }

    public Boolean getPaidOf() {
        return isPaidOf;
    }

    public void setPaidOf(Boolean paidOf) {
        isPaidOf = paidOf;
    }

    public Long getTimeOfPayback() {
        return timeOfPayback;
    }

    public void setTimeOfPayback(Long timeOfPayback) {
        this.timeOfPayback = timeOfPayback;
    }

}
