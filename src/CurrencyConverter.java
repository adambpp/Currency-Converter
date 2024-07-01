import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {

    public double rateConversion(String fromCurrency, String toCurrency, double amount) throws Exception {
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

    private CurrencyResponse CreateCurInfo() throws Exception {
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


    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();

        try {
            converter.CreateCurInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
