package cleanCatClient.module;

import cleanCatClient.events.Event;
import net.minecraft.client.Minecraft;

public class Module {
    public String name;
    public boolean toggled;
    public int keyCode;
    public Catelogy catelogy;
    public String description;
    public Minecraft mc = Minecraft.getMinecraft();
    public Module(String name, int keyCode, String description,Catelogy catelogy) {
        this.name = name;
        this.keyCode = keyCode;
        this.catelogy = catelogy;
        this.description = description;
    }

    public boolean isEnable() {
        return this.toggled;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void onEvent(Event e) {
    }
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public enum Catelogy {
        PVP("PvP"), MOVEMENT("Movement"), PLAYER("Player"), RENDER("Render"), MISC("Misc");
        public String name;
        Catelogy(String name){
            this.name = name;
        }
    }

}
