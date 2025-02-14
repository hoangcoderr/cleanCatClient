package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.gui.clickgui.settings.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModSettingsInstance {
    private static ArmorStatusSettings armorStatusSetting;
    private static BlockOverlaySettings blockOverlaySettings;
    private static BossBarSettings bossBarSettings;
    private static CustomCrossHairSettings customCrosshairSettings;
    private static FPSSettings fpsSettings;
    private static GlintColorSettings glintColorSettings;
    private static HitColorSettings hitColorSettings;
    private static KeystrokeSettings keystrokesSettings;
    private static LazyChunkLoadingSetting lazyChunkLoadingSettings;
    private static NoHurtCamSettings noHurtCamSettings;
    private static OldAnimationSettings oldAnimationSettings;
    private static PerspectiveSettings perspectiveSettings;
    private static PingDisplaySettings pingDisplaySettings;
    private static PoisonStatusSettings potionStatusSettings;
    private static ScoreboardSettings scoreboardSettings;
    private static ToggleSprintSettings toggleSprintSettings;
    private static TimeChangerSettings timeChangerSettings;
    private static ColorSaturationSettings colorSaturationSettings;
    private static ItemPhysicsSettings itemPhysicsSetting;
    private static CoordinateDisplaySettings coordinateDisplaySettings;
    private static ChatShortcutSettings chatShortcutSetting;
    private static ItemCounterSetting itemCounterSetting;
    private static TNTTimerSettings tntTimerSettings;
    private static CustomNameSettings customNameSettings;
    private static ParticlesMultiplierSettings particlesMultiplierSettings;
    private static CPSSettings cpsSettings;
    private static List<ModSettings> allSettings = new ArrayList<>();

    public static void register() {
        cpsSettings = new CPSSettings();
        particlesMultiplierSettings = new ParticlesMultiplierSettings();
        customNameSettings = new CustomNameSettings();
        tntTimerSettings = new TNTTimerSettings();
        chatShortcutSetting = new ChatShortcutSettings();
        armorStatusSetting = new ArmorStatusSettings();
        blockOverlaySettings = new BlockOverlaySettings();
        bossBarSettings = new BossBarSettings();
        customCrosshairSettings = new CustomCrossHairSettings();
        fpsSettings = new FPSSettings();
        glintColorSettings = new GlintColorSettings();
        hitColorSettings = new HitColorSettings();
        keystrokesSettings = new KeystrokeSettings();
        lazyChunkLoadingSettings = new LazyChunkLoadingSetting();
        noHurtCamSettings = new NoHurtCamSettings();
        oldAnimationSettings = new OldAnimationSettings();
        perspectiveSettings = new PerspectiveSettings();
        pingDisplaySettings = new PingDisplaySettings();
        potionStatusSettings = new PoisonStatusSettings();
        scoreboardSettings = new ScoreboardSettings();
        toggleSprintSettings = new ToggleSprintSettings();
        timeChangerSettings = new TimeChangerSettings();
        colorSaturationSettings = new ColorSaturationSettings();
        itemPhysicsSetting = new ItemPhysicsSettings();
        coordinateDisplaySettings = new CoordinateDisplaySettings();
        itemCounterSetting = new ItemCounterSetting();
//        allSettings.add(armorStatusSetting);
//        allSettings.add(blockOverlaySettings);
//        allSettings.add(bossBarSettings);
//        allSettings.add(customCrosshairSettings);
//        allSettings.add(fpsSettings);
//        allSettings.add(glintColorSettings);
//        allSettings.add(hitColorSettings);
//        allSettings.add(keystrokesSettings);
//        allSettings.add(lazyChunkLoadingSettings);
//        allSettings.add(noHurtCamSettings);
//        allSettings.add(oldAnimationSettings);
//        allSettings.add(perspectiveSettings);
//        allSettings.add(pingDisplaySettings);
//        allSettings.add(potionStatusSettings);
//        allSettings.add(scoreboardSettings);
//        allSettings.add(toggleSprintSettings);
        allSettings = Arrays.asList(
                armorStatusSetting, blockOverlaySettings, bossBarSettings, customCrosshairSettings, fpsSettings,
                glintColorSettings, hitColorSettings, keystrokesSettings, lazyChunkLoadingSettings, noHurtCamSettings,
                oldAnimationSettings, perspectiveSettings, pingDisplaySettings, potionStatusSettings,
                scoreboardSettings, toggleSprintSettings, timeChangerSettings, colorSaturationSettings, itemPhysicsSetting, coordinateDisplaySettings, chatShortcutSetting, itemCounterSetting, tntTimerSettings, customNameSettings,
                particlesMultiplierSettings, cpsSettings
        );
    }

    public static List<ModSettings> getAllSettings() {
        return allSettings;
    }




}
