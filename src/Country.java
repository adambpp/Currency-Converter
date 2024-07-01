import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("currencyId")
    private String currencyId;
    @SerializedName("currencyName")
    private String currencyName;
    @SerializedName("currencySymbol")
    private String currencySymbol;

    // Getters

    public String getCurrencyId() {
        return currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }
}

