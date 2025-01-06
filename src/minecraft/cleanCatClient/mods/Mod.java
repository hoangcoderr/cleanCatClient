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
        this.category = category;

        ModData modData = ModManager.getModData(name);
        //System.out.println("ModData: " + modData.name + " " + modData.state + " " + modData.keyBind);
        if (modData != null) {
            this.isEnabled = modData.state;
            this.keyBind = modData.keyBind;
        } else {
            System.out.println("ModData is null. Creating a new one.");
            this.isEnabled = false;
            this.keyBind = Keyboard.KEY_NONE;
        }

        setEnabled(isEnabled);
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
        saveModData();
    }

    public ModCategory getCategory() {
        return category;
    }

    public int getCategoryId() {
        return category.getId();
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;

        if (enabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
        saveModData();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    private void saveModData() {
        ModData modData = new ModData(isEnabled, keyBind);
        ModManager.setModData(name, modData);
    }

    public File getFolder() {
        final File folder = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName());
        folder.mkdirs();
        return folder;
    }

    public void loadConfig(){

    }
    public void saveConfig(){

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
}