package cleanCatClient.mods;

import cleanCatClient.event.EventManager;
import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.gui.hud.IRenderer;
import cleanCatClient.mods.impl.*;
import com.sun.media.sound.ModelPerformer;
import net.minecraft.client.Minecraft;


public class ModInstances {
    private static ArmorStatus modArmorStatus;
    private static FPS modFPS;
    private static Keystrokes modKeystrokes;
    private static ToggleSprint toggleSprint;
    private static Perspective modPerspective;
    private static FullBright modFullBright;
    private static BlockOverlay modBlockOverlay;
    private static GlintColor modGlintColor;
    public static void register(HUDManager api){

        modArmorStatus = new ArmorStatus();
        api.register(modArmorStatus);

        modFPS = new FPS();
        api.register(modFPS);

        modKeystrokes = new Keystrokes();
        api.register(modKeystrokes);

        toggleSprint = new ToggleSprint();
        api.register(toggleSprint);

        modPerspective = new Perspective();

        modFullBright = new FullBright(false);

        modBlockOverlay = new BlockOverlay(false);

        modGlintColor = new GlintColor(false);
    }

    public static ArmorStatus getArmorStatus(){
        return modArmorStatus;
    }

    public static FPS getFPS(){
        return modFPS;
    }

    public static ToggleSprint getToggleSprint(){
        return toggleSprint;
    }

    public static Keystrokes getKeystrokes(){
        return modKeystrokes;
    }

    public static Perspective getPerspective(){
        return modPerspective;
    }

    public static FullBright getFullBright(){
        return modFullBright;
    }

    public static BlockOverlay getBlockOverlay(){
        return modBlockOverlay;
    }

    public static GlintColor getGlintColor(){
        return modGlintColor;
    }
}
