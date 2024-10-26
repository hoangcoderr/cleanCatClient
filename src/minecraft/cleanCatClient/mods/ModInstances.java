package cleanCatClient.mods;

import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.mods.impl.*;
import cleanCatClient.mods.impl.oldanimations.LeftHand;
import cleanCatClient.mods.impl.oldanimations.OldAnimation;
import cleanCatClient.mods.impl.oldanimations.SmoothSneaking;
import cleanCatClient.mods.impl.oldanimations.SwingAnimation;

import java.util.ArrayList;
import java.util.List;


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
    private static SwingAnimation swingAnimation;
    private static SmoothSneaking smoothSneaking;
    private static LeftHand leftHand;
    private static PotionStatus modPotionStatus;
    private static Scoreboard modScoreboard;
    private static LazyChunkLoading lazyChunkLoading;
    private static CustomCrosshair customCrosshair;
    private static DisableBlockParticles disableBlockParticles;
    private static PingDisplay pingDisplay;
    private static BossBar modBossBar;
    private static HitColor hitColor;
    private static OldAnimation oldAnimation;
    private static List<Mod> allMods = new ArrayList<>();
    private static PlayerDistance playerDistanceMod;
    private static TimeChanger timeChangerMod;
    private static ColorSaturation colorSaturation;
    private static MinimalViewBobbing minimalViewBobbing;
    private static ItemPhysics itemPhysics;
    private static CoordinateDisplay coordinateDisplay;
    private static ChatShortcuts chatShortcuts;
    private static ItemCounter itemCounter;
    private static TNTTimer tntTimer;
    public static void register(HUDManager api) {
        tntTimer = new TNTTimer();
        itemCounter = new ItemCounter();
        api.register(itemCounter);
        chatShortcuts = new ChatShortcuts();
        coordinateDisplay = new CoordinateDisplay();
        api.register(coordinateDisplay);
        itemPhysics = new ItemPhysics();
        timeChangerMod = new TimeChanger();

        minimalViewBobbing = new MinimalViewBobbing();

        colorSaturation = new ColorSaturation();
        api.register(colorSaturation);
        playerDistanceMod = new PlayerDistance();
        api.register(playerDistanceMod);

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

        swingAnimation = new SwingAnimation();

        smoothSneaking = new SmoothSneaking();

        leftHand = new LeftHand();

        modPotionStatus = new PotionStatus();
        api.register(modPotionStatus);

        modScoreboard = new Scoreboard();
        api.register(modScoreboard);

        lazyChunkLoading = new LazyChunkLoading();

        customCrosshair = new CustomCrosshair();
        api.register(customCrosshair);

        disableBlockParticles = new DisableBlockParticles();

        pingDisplay = new PingDisplay();
        api.register(pingDisplay);

        modBossBar = new BossBar();

        hitColor = new HitColor();

        oldAnimation = new OldAnimation();

        allMods.add(modArmorStatus);
        allMods.add(modBlockOverlay);
        allMods.add(modBossBar);
        allMods.add(customCrosshair);
        allMods.add(modFPS);
        allMods.add(modGlintColor);
        allMods.add(hitColor);
        allMods.add(modKeystrokes);
        allMods.add(lazyChunkLoading);
        allMods.add(modNoHurtCam);
        allMods.add(oldAnimation);
        allMods.add(modPerspective);
        allMods.add(pingDisplay);
        allMods.add(modPotionStatus);
        allMods.add(modScoreboard);
        allMods.add(toggleSprint);
        allMods.add(timeChangerMod);
        allMods.add(colorSaturation);
        allMods.add(itemPhysics);
        allMods.add(coordinateDisplay);
        allMods.add(chatShortcuts);
        allMods.add(itemCounter);
        allMods.add(tntTimer);
    }

    public static ItemCounter getItemCounter() {
        return itemCounter;
    }

    public static TNTTimer getTntTimer() {
        return tntTimer;
    }

    public static ChatShortcuts getChatShortcuts() {
        return chatShortcuts;
    }
    public static CoordinateDisplay getCoordinateDisplay() {
        return coordinateDisplay;
    }

    public static ItemPhysics getItemPhysics() {
        return itemPhysics;
    }
    public static List<Mod> getAllMods() {
        return allMods;
    }
    public static ArmorStatus getArmorStatus() {
        return modArmorStatus;
    }

    public static MinimalViewBobbing getMinimalViewBobbing() {
        return minimalViewBobbing;
    }

    public static OldAnimation getOldAnimation() {
        return oldAnimation;
    }

    public static FPS getFPS() {
        return modFPS;
    }

    public static ToggleSprint getToggleSprint() {
        return toggleSprint;
    }

    public static Keystrokes getKeystrokes() {
        return modKeystrokes;
    }

    public static Perspective getPerspective() {
        return modPerspective;
    }

    public static FullBright getFullBright() {
        return modFullBright;
    }

    public static BlockOverlay getBlockOverlay() {
        return modBlockOverlay;
    }

    public static GlintColor getGlintColor() {
        return modGlintColor;
    }

    public static NoHurtCam getNoHurtCam() {
        return modNoHurtCam;
    }

    public static SwingAnimation getSwingAnimation() {
        return swingAnimation;
    }

    public static SmoothSneaking getSmoothSneaking() {
        return smoothSneaking;
    }

    public static Scoreboard getScoreboard() {
        return modScoreboard;
    }
    public static LeftHand getLeftHand() {
        return leftHand;
    }

    public static PotionStatus getPotionStatus() {
        return modPotionStatus;
    }

    public static LazyChunkLoading getLazyChunkLoading() {
        return lazyChunkLoading;
    }

    public static CustomCrosshair getCustomCrosshair() {
        return customCrosshair;
    }

    public static DisableBlockParticles getDisableBlockParticles() {
        return disableBlockParticles;
    }

    public static PingDisplay getPingDisplay() {
        return pingDisplay;
    }

    public static BossBar getBossBar() {
        return modBossBar;
    }

    public static HitColor getHitColor() {
        return hitColor;
    }

    public static PlayerDistance getPlayerDistanceMod() {
        return playerDistanceMod;
    }

    public static TimeChanger getTimeChangerMod() {
        return timeChangerMod;
    }

    public static ColorSaturation getColorSaturation() {
        return colorSaturation;
    }
}
