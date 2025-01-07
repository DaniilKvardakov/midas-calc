package midas.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:midas.properties")
public class MidasResponse {

    public static final MidasResponse BAD_MIDAS_RESPONSE = new MidasResponse();
    private Status status;
    private String message;


    @Value("${midas.price}")
    private static int midasPrice;
    private int profitUntilEnd;
    private int profitAfterSale;
    private boolean isPaidOf;
    private long timeOfPayback; // In minutes.

    private  MidasResponse() { }

    public MidasResponse(int profitUntilEnd, int profitAfterSale, long timeOfPayback) {
        this.profitUntilEnd = profitUntilEnd;
        this.profitAfterSale = profitAfterSale == 0 ? profitUntilEnd : profitAfterSale;
        this.isPaidOf = profitAfterSale >= midasPrice;
        this.timeOfPayback = timeOfPayback;
    }

    public MidasResponse status(Status status) {
        this.status = status;
        return this;
    }

    public MidasResponse message(String message) {
        this.message = message;
        return this;
    }

    public MidasResponse statusAndMessage(Status status){
        this.status = status;
        this.message = status.getMessage();
        return this;
    }

    public static int getMidasPrice() {
        return midasPrice;
    }

    public static void setMidasPrice(int midasPrice) {
        MidasResponse.midasPrice = midasPrice;
    }

    public long getTimeOfPayback() {
        return timeOfPayback;
    }

    public void setTimeOfPayback(long timeOfPayback) {
        this.timeOfPayback = timeOfPayback;
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

    @Override
    public String toString() {
        return "MidasResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", midasPrice=" + midasPrice +
                ", profitUntilEnd=" + profitUntilEnd +
                ", profitAfterSale=" + profitAfterSale +
                ", isPaidOf=" + isPaidOf +
                '}';
    }
}
