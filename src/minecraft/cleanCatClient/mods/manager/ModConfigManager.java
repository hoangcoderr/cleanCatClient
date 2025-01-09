package cleanCatClient.mods.manager;

import cleanCatClient.utils.FileManager;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ModConfigManager {
    private static final File MOD_CONFIGS_FILE = new File(FileManager.getModsDirectory(), "mod_configs.json");
    private static Map<String, String[]> modsConfigs = new HashMap<>();

    public static void loadAllConfigs() {
        Type type = new TypeToken<Map<String, String[]>>() {}.getType();
        modsConfigs = FileManager.readAFromJson(MOD_CONFIGS_FILE, type);
        if (modsConfigs == null) {
            modsConfigs = new HashMap<>();
            System.out.println("Mods config file does not exist. Creating a new one.");
        }
    }

    public static void saveAllConfigs() {
        FileManager.writeJsonToFile(MOD_CONFIGS_FILE, modsConfigs);
    }

    public static String[] getModConfig(String modName) {
        return modsConfigs.get(modName);
    }

    public static void saveModConfig(String modName, String[] data) {
        modsConfigs.put(modName, data);
        saveAllConfigs();
    }
}