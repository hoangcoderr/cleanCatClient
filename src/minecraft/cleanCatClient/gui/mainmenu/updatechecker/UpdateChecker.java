package cleanCatClient.gui.mainmenu.updatechecker;

import cleanCatClient.Client;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private static final String VERSION_URL = "http://47.236.89.236:25566/lastest_version";

    public static boolean isLastestVersion() {
        try {
            URL url = new URL(VERSION_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String latestVersion = jsonResponse.getString("message");

            if (!Client.CLIENT_VERSION.equals(latestVersion)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void downloadUpdate() {
        // Implement the logic to download and apply the update
        System.out.println("Downloading and applying the update...");
        // You can use libraries like Apache Commons IO to handle the download and extraction of the update
    }
}