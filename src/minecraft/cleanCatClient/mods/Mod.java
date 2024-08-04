package cleanCatClient.mods;

import cleanCatClient.Client;
import cleanCatClient.event.EventManager;
import cleanCatClient.utils.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.io.File;

public class Mod {
    public String name;
    public String description;
    private boolean isEnabled = false;
    protected final Minecraft mc;
    protected final FontRenderer font;
    protected final Client client;

    public Mod(String name, String description) {
        this.name = name;
        this.description = description;
        this.mc = Minecraft.getMinecraft();
        this.font = mc.fontRendererObj;
        this.client = Client.getInstance();
        this.isEnabled = loadModState();
        setEnabled(isEnabled);
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

    private void saveModState(boolean state) {
        FileManager.writeJsonToFile(new File(getFolder(), "state.json"), state);
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;

        if (enabled) {
            EventManager.register(this);}
        else {
            EventManager.unregister(this);
        }
        saveModState(enabled);
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
