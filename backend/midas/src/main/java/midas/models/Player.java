package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

import java.util.List;
import java.util.Map;

@NoCommentsNeeded
public class Player {

    @JsonProperty("personaname")
    private String personaName;
    @JsonProperty("item_uses")
    private Map<String, Integer> itemUses;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("purchase_log")
    private List<PurchaseLog> purchaseLogs;

    public List<PurchaseLog> getPurchaseLogs() {
        return purchaseLogs;
    }

    public void setPurchaseLogs(List<PurchaseLog> purchaseLogs) {
        this.purchaseLogs = purchaseLogs;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public Map<String, Integer> getItemUses() {
        return itemUses;
    }

    public void setItemUses(Map<String, Integer> itemUses) {
        this.itemUses = itemUses;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "\nPlayer{" +
                "\n personaName=" + personaName +
                ",\n itemUses=" + itemUses +
                ",\n accountId=" + accountId +
                ",\n purchaseLogs=" + purchaseLogs +
                "\n}";
    }
}
