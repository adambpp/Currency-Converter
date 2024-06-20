import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {

    public String rateConversion(String fromCurrency, String toCurrency/*double amount*/) throws Exception {
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

            return response.toString();
        } else {
            System.out.println("'GET' Request Failed. Http Status Code: " + responseCode);
            return null;
        }
    }

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();

        try {
            System.out.println(converter.rateConversion("USD", "CAD"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
