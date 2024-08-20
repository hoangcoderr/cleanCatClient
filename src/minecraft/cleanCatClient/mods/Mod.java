package cleanCatClient.mods;

import cleanCatClient.Client;
import cleanCatClient.event.EventManager;
import cleanCatClient.utils.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

import java.io.File;

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


    public ModCategory getCategory() {
        return category;
    }

    public int getCategoryId() {
        return category.getId();
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
            EventManager.register(this);}
        else {
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
