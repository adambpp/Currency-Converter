import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CurrencyResponse {
    @SerializedName("results")
    private Map<String, Country> results;

    public Map<String, Country> getResults() {
        return results;
    }
}
