import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translate {
    public String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbyZNzsPa3FjuL4QihNLLAeEsDRixMdih_bgkkqhexnVor67v90/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        if (text.equals(response.toString())) {
            String urlStr1 = "https://script.google.com/macros/s/AKfycbyZNzsPa3FjuL4QihNLLAeEsDRixMdih_bgkkqhexnVor67v90/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langFrom +
                    "&source=" + langTo;
            URL url1 = new URL(urlStr1);
            StringBuilder response1 = new StringBuilder();
            HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
            con1.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
            String inputLine1;
            while ((inputLine1 = in1.readLine()) != null) {
                response1.append(inputLine1);
            }
            in1.close();
            return response1.toString();
        }
        return response.toString();
    }
}