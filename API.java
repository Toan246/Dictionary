import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class API {
    public static void main(String[] args) {
        String apiKey = "AIzaSyD7O-DXo4OKCN4_wsHeWQLOrnl1w6DJQVA"; // Replace with your API key
        String textToTranslate = "Hello, world!";
        String targetLanguage = "vie"; // Vietnamese

        try {
            String translatedText = translateText(apiKey, textToTranslate, targetLanguage);
            System.out.println("Original Text: " + textToTranslate);
            System.out.println("Translated Text: " + translatedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String translateText(String apiKey, String text, String targetLanguage) throws IOException {
        String apiUrl = "https://translation.googleapis.com/language/translate/v2";
        String encodedText = URLEncoder.encode(text, "UTF-8");
        String url = apiUrl + "?key=" + apiKey + "&q=" + encodedText + "&target=" + targetLanguage;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return parseTranslationResponse(response.toString());
        } else {
            // Print error stream for more details
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();

            System.out.println("Translation request failed. Error Response: " + errorResponse.toString());
            return null;
        }
    }


    private static String parseTranslationResponse(String response) {
        // Parse the JSON response to extract the translated text
        // This will depend on the structure of the API response
        // You may want to use a JSON library like Jackson or Gson for better parsing
        // For simplicity, this example assumes a simple response format
        String startMarker = "\"translatedText\":\"";
        int startIndex = response.indexOf(startMarker);
        if (startIndex != -1) {
            startIndex += startMarker.length();
            int endIndex = response.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return response.substring(startIndex, endIndex);
            }
        }
        return null;
    }
}
