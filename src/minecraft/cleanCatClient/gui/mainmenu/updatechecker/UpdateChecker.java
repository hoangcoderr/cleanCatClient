package cleanCatClient.gui.mainmenu.updatechecker;

import cleanCatClient.Client;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class UpdateChecker {


    private static final String WEBSITE_URL = "http://cleancat.games";


    public static ArrayList<String> updateInfos = new ArrayList<>();

    private static final String VERSION_URL_TEMPLATE = "http://api.cleancat.games/%s/lastest_version";

    public static boolean isShownUpdate = true;

    public static boolean isLastestVersion(String playerName) {

        try {
            String versionUrl = String.format(VERSION_URL_TEMPLATE, playerName);
            URL url = new URL(versionUrl);
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
            String latestVersion = jsonResponse.getString("version");
            String updateContent = jsonResponse.getString("update_content");

            // Split the update content by \n and add to updateInfos
            updateInfos.clear();
            String[] updates = updateContent.split("\n");
            Collections.addAll(updateInfos, updates);
            //isShownUpdate = false;
            return Client.CLIENT_VERSION.equals(latestVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void downloadUpdate() {
//        try {
//            // Download the file to a temporary location
//            URL url = new URL(DOWNLOAD_URL);
//            File tempFile = new File(TEMP_FILE);
//            FileUtils.copyURLToFile(url, tempFile);
//
//            // Create a script to replace the old file with the new one
//            File scriptFile = new File(UPDATE_SCRIPT);
//            try (FileWriter writer = new FileWriter(scriptFile)) {
//                writer.write("timeout /t 5\n");
//                writer.write("del \"" + System.getenv("APPDATA") + "\\.minecraft\\versions\\cleanCatClient\\cleanCatClient.jar\"\n");
//                writer.write("move \"" + TEMP_FILE + "\" \"" + System.getenv("APPDATA") + "\\.minecraft\\versions\\cleanCatClient\\cleanCatClient.jar\"\n");
//                writer.write("start javaw -jar \"" + System.getenv("APPDATA") + "\\.minecraft\\versions\\cleanCatClient\\cleanCatClient.jar\"\n");
//            }
//
//            // Run the script and exit the game
//            Runtime.getRuntime().exec("cmd /c start " + UPDATE_SCRIPT);
//            System.exit(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            // Open the download URL in the default web browser
            Desktop.getDesktop().browse(new URL(WEBSITE_URL).toURI());

            // Exit the system
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}