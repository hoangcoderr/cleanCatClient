package cleanCatClient.mods;

import cleanCatClient.Client;
import cleanCatClient.event.EventManager;
import cleanCatClient.mods.manager.ModData;
import cleanCatClient.mods.manager.ModManager;
import cleanCatClient.utils.FileManager;
import cleanCatClient.mods.manager.ModConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
    private final ModCategory category;

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
        return ModConfigManager.getModConfig(this.name);
    }

    public void saveDataConfig(String[] data) {
        ModConfigManager.saveModConfig(this.name, data);
    }
}