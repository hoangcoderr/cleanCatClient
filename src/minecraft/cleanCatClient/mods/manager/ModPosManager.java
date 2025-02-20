// Sửa đổi lớp ModPosManager
package cleanCatClient.mods.manager;

import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.utils.FileManager;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ModPosManager implements IConfigManager<ScreenPosition> {
    private static final File POSITIONS_FILE = new File(FileManager.getModsDirectory(), "positions.json");
    private static Map<String, ScreenPosition> positions = new HashMap<>();

    @Override
    public void loadAll() {
        Type type = new TypeToken<Map<String, ScreenPosition>>() {}.getType();
        positions = FileManager.readAFromJson(POSITIONS_FILE, type);
        if (positions == null) {
            positions = new HashMap<>();
            System.out.println("Positions file does not exist. Creating a new one.");
        }
    }

    @Override
    public void saveAll() {
        FileManager.writeJsonToFile(POSITIONS_FILE, positions);
    }

    @Override
    public ScreenPosition getConfig(String modName) {
        return positions.get(modName);
    }

    @Override
    public void setConfig(String modName, ScreenPosition pos) {
        positions.put(modName, pos);
        saveAll();
    }
}