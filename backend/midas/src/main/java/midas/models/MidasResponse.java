package midas.models;

import midas.annotations.Commented;

import java.time.Duration;

/**
 * Сущность ответа при запросе.
 */
@Commented
public class MidasResponse {

    /**
     * Заглушка для "плохого ответа".
     */
    public static final MidasResponse BAD_MIDAS_RESPONSE = new MidasResponse();
    /**
     * Статус ответа к запросам с клиента.
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
     * Окупился ли Мидас.
     */
    private Boolean isPaidOf;
    /**
     * Время когда мидас окупил/окупил бы себя(без учета продажи).
     */
    private Long timeOfPayback; // In minutes.

    private MidasResponse() { }

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
     * Метод считающий время, когда мидас окупился/окупился бы на основе времени его покупки.
     * Метод считает БЕЗ УЧЕТА ПРОДАЖИ!
     * @param midasTime время покупки мидаса.
     * @return время когда мидас окупился.
     * @see Duration
     */
    public static Duration getTimeOfPayback(Duration midasTime, int midasPrice) {
        int priceBuffer = 0;
        Duration durationBuffer = midasTime;
        while (priceBuffer < midasPrice) {
            durationBuffer = durationBuffer.plus(Duration.ofSeconds(110));
            priceBuffer += 160;
        }
        return durationBuffer;
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
