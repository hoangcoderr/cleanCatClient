// Sửa đổi lớp ModManager
package cleanCatClient.mods.manager;

import cleanCatClient.utils.FileManager;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ModManager implements IConfigManager<ModData> {
    private static final File MODS_CONFIG_FILE = new File(FileManager.getModsDirectory(), "mods_config.json");
    private static Map<String, ModData> modsData = new HashMap<>();

    @Override
    public void loadAll() {
        Type type = new TypeToken<Map<String, ModData>>() {}.getType();
        modsData = FileManager.readAFromJson(MODS_CONFIG_FILE, type);
        if (modsData == null) {
            modsData = new HashMap<>();
            System.out.println("Mods config file does not exist. Creating a new one.");
        } else {
            System.out.println("Mods config file loaded.");
            for (Map.Entry<String, ModData> entry : modsData.entrySet()) {
                System.out.println("Mod: " + entry.getKey() + ", Data: " + entry.getValue());
            }
        }
    }

    @Override
    public void saveAll() {
        FileManager.writeJsonToFile(MODS_CONFIG_FILE, modsData);
    }

    @Override
    public ModData getConfig(String modName) {
        System.out.println("Getting mod data for: " + modName);
        return modsData.get(modName);
    }

    @Override
    public void setConfig(String modName, ModData modData) {
        modsData.put(modName, modData);
        saveAll();
    }
}