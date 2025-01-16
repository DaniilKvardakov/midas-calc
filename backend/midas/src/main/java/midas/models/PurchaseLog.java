package midas.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.Commented;


/**
 * Сущность журнала покупок из open dota api,
 * показывающая время покупки того или иного предмета.
 */
@Commented
public class PurchaseLog {
    /**
     * Время покупки предмета
     */
    @JsonProperty("time")
    private int time;
    /**
     * Название предмета
     */
    @JsonProperty("key")
    private String itemName;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "time=" + time +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
