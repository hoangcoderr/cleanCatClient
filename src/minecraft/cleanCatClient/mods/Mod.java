package cleanCatClient.mods;

import cleanCatClient.Client;
import cleanCatClient.event.EventManager;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.utils.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;


import java.io.*;

public class Mod {
    public String name;
    public String description;
    private boolean isEnabled = false;
    protected final Minecraft mc;
    protected final FontRenderer font;
    protected final Client client;
    protected int keyBind = Keyboard.KEY_NONE;
    private ModCategory category;

    public Mod(String name, String description, ModCategory category) {
        this.name = name;
        this.description = description;
        this.mc = Minecraft.getMinecraft();
        this.font = mc.fontRendererObj;
        this.client = Client.getInstance();
        this.isEnabled = loadModState();
        this.keyBind = loadKeyBind();
        this.category = category;
        setEnabled(isEnabled);
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
        saveKeyBind(keyBind);
    }

    public ModCategory getCategory() {
        return category;
    }

    public int getCategoryId() {
        return category.getId();
    }

    public String[] loadDataConfig() {
        File configFile = new File(getFolder(), "config.json");
        if (!configFile.exists()) {
            System.out.println("Config file does not exist. Creating a new one.");
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line = reader.readLine();
            if (line != null) {
                String[] configParts = line.split("\\|");
                return configParts;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveDataConfig(String[] data) {
        StringBuilder configData = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            configData.append(data[i]);
            if (i < data.length - 1) {
                configData.append("|");
            }
        }
        File configFile = new File(getFolder(), "config.json");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(configData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
    }

    public void saveConfig() {
    }

    private int loadKeyBind() {
        File keyBindFile = new File(getFolder(), "keybind.json");
        if (!keyBindFile.exists()) {
            saveKeyBind(Keyboard.KEY_NONE);
            return Keyboard.KEY_NONE;
        }
        Integer keyBind = FileManager.readFromJson(keyBindFile, Integer.class);
        return keyBind != null ? keyBind : Keyboard.KEY_NONE;
    }

    protected void saveKeyBind(int keyBind) {
        FileManager.writeJsonToFile(new File(getFolder(), "keybind.json"), keyBind);
    }

    private boolean loadModState() {
        File stateFile = new File(getFolder(), "state.json");
        if (!stateFile.exists()) {
            saveModState(false);
            return false;
        }
        Boolean state = FileManager.readFromJson(stateFile, Boolean.class);
        return state != null ? state : false;
    }

    protected void saveModState(boolean state) {
        FileManager.writeJsonToFile(new File(getFolder(), "state.json"), state);
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;

        if (enabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
        saveModState(enabled);
        System.out.println("Saved mod " + getFolder());
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public File getFolder() {
        final File folder = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName());
        folder.mkdirs();
        return folder;
    }

}
