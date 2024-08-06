package cleanCatClient.mods;

import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.mods.impl.*;
import cleanCatClient.mods.impl.oldanimations.SmoothSneaking;
import cleanCatClient.mods.impl.oldanimations.SwordAnimation;


public class ModInstances {
    private static ArmorStatus modArmorStatus;
    private static FPS modFPS;
    private static Keystrokes modKeystrokes;
    private static ToggleSprint toggleSprint;
    private static Perspective modPerspective;
    private static FullBright modFullBright;
    private static BlockOverlay modBlockOverlay;
    private static GlintColor modGlintColor;
    private static NoHurtCam modNoHurtCam;
    private static SwordAnimation swordAnimation;
    private static SmoothSneaking smoothSneaking;
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

        modFullBright = new FullBright();

        modBlockOverlay = new BlockOverlay();

        modGlintColor = new GlintColor();

        modNoHurtCam = new NoHurtCam();

        swordAnimation = new SwordAnimation();

        smoothSneaking = new SmoothSneaking();
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

    public static NoHurtCam getNoHurtCam(){
        return modNoHurtCam;
    }

    public static SwordAnimation getSwordAniamtion(){
        return swordAnimation;
    }

    public static SmoothSneaking getSmoothSneaking(){
        return smoothSneaking;
    }
}
