import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class CurrencyConverter {

    private final CurrencyResponse currencyResponse;
    private final ArrayList<String> currencies = new ArrayList<>();

    public CurrencyConverter() {
        this.currencyResponse = CreateCurInfo();
        createCurList();
    }

    public double rateConversion(String fromCurrency, String toCurrency, double amount) {
        try {
            if (toCurrency != null && toCurrency.equals(fromCurrency)) {
                throw new IllegalStateException();
            }

            URL obj = new URL(String.format("https://free.currconv.com/api/v7/convert?q=%s_%s&compact=ultra&apiKey=%s",
                    fromCurrency, toCurrency, APIKey.API_KEY));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Setting the request method to GET
            con.setRequestMethod("GET");

            // Setting User-Agent property
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("'GET' request is sent to URL : " + obj + "\nResponse Code: " + responseCode);

            if (responseCode == 200) { // HTTP OK
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

                double rate = jsonResponse.get(String.format("%s_%s", fromCurrency, toCurrency)).getAsDouble();

                return amount * rate;

            } else {
                System.out.println("'GET' Request Failed. Http Status Code: " + responseCode);
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    private CurrencyResponse CreateCurInfo() {
        try {
            URL obj = new URL(String.format("https://free.currconv.com/api/v7/countries?apiKey=%s", APIKey.API_KEY));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Setting the request method to GET
            con.setRequestMethod("GET");

            // Setting User-Agent property
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("'GET' request is sent to URL : " + obj + "\nResponse Code: " + responseCode);

            if (responseCode == 200) { // HTTP OK
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response using Gson
                Gson gson = new Gson();
                return gson.fromJson(response.toString(), CurrencyResponse.class);
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createCurList() {
        for (Map.Entry<String, Country> entry : currencyResponse.getResults().entrySet()) {
            Country country = entry.getValue();
            currencies.add(country.getCurrencyName());
        }
    }

    public ArrayList<String> getCurrencies() {
        return currencies;
    }

    public String getCurrencyNameByCurrencyId(String currencyId) {
        for (Map.Entry<String, Country> entry : currencyResponse.getResults().entrySet()) {
            Country country = entry.getValue();
            if (country.getCurrencyId().equals(currencyId)) {
                return country.getCurrencyName();
            }
        }
        return null; // Not found
    }

    public String getCurrencyIdByName(String currencyName) {
        for (Map.Entry<String, Country> entry : currencyResponse.getResults().entrySet()) {
            Country country = entry.getValue();
            if (country.getCurrencyName().equals(currencyName)) {
                return country.getCurrencyId();
            }
        }
        return null; // Not found
    }

    public String getCurrencySymbolByCurrencyId(String currencyId) {
        for (Map.Entry<String, Country> entry : currencyResponse.getResults().entrySet()) {
            Country country = entry.getValue();
            if (country.getCurrencyId().equals(currencyId)) {
                return country.getCurrencySymbol();
            }
        }
        return null; // Not found
    }


    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();


    }
}
