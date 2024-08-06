package cleanCatClient.utils;

import com.google.gson.Gson;
import org.lwjgl.Sys;

import java.io.*;

public class FileManager {
    private static Gson gson;
    private static File ROOT_DIR;
    private static File MODS_DIR;

    static {
        FileManager.gson = new Gson();
        FileManager.ROOT_DIR = new File("cleanCatClient");
        FileManager.MODS_DIR = new File(FileManager.ROOT_DIR, "Mods");
    }

    public static void init() {
        if (!FileManager.ROOT_DIR.exists()) {
            FileManager.ROOT_DIR.mkdir();
        }
        if (!FileManager.MODS_DIR.exists()) {
            FileManager.MODS_DIR.mkdir();
        }
    }

    public static Gson getGson() {
        return FileManager.gson;
    }

    public static File getModsDirectory() {
        return FileManager.MODS_DIR;
    }

    public static boolean writeJsonToFile(File file, final Object obj) {
        try {
            System.out.println("Writing to file: " + file.getName());
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + file.getName());
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(FileManager.gson.toJson(obj).getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T extends Object> T readFromJson(final File file, final Class<T> c) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream)fileInputStream);
            BufferedReader bufferedReader = new BufferedReader((Reader)inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            return gson.fromJson(builder.toString(),c);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
