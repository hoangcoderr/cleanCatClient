package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.gui.clickgui.settings.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModSettingsInstance {
    private static ArmorStatusSetting armorStatusSetting;
    private static BlockOverlaySettings blockOverlaySettings;
    private static BossBarSettings bossBarSettings;
    private static CustomCrossHairSettings customCrosshairSettings ;
    private static FPSSetting fpsSettings;
    private static GlintColorSetting glintColorSettings;
    private static HitColorSetting hitColorSettings;
    private static KeyStrokeSetting keystrokesSettings;
    private static LazyChunkLoadingSetting lazyChunkLoadingSettings;
    private static NoHurtCamSetting noHurtCamSettings;
    private static OldAnimationSetting oldAnimationSettings;
    private static PerspectiveSetting perspectiveSettings;
    private static PingDisplaySetting pingDisplaySettings;
    private static PoisonStatusSetting potionStatusSettings;
    private static ScoreboardSetting scoreboardSettings;
    private static ToggleSprintSetting toggleSprintSettings;
    private static TimeChangerSettings timeChangerSettings;
    private static ColorSaturationSettings colorSaturationSettings;
    private static ItemPhysicsSetting itemPhysicsSetting;
    private static CoordinateDisplaySettings coordinateDisplaySettings;
    private static List<ModSettings> allSettings = new ArrayList<>();
    public static void register(){
        armorStatusSetting = new ArmorStatusSetting();
        blockOverlaySettings = new BlockOverlaySettings();
        bossBarSettings = new BossBarSettings();
        customCrosshairSettings = new CustomCrossHairSettings();
        fpsSettings = new FPSSetting();
        glintColorSettings = new GlintColorSetting();
        hitColorSettings = new HitColorSetting();
        keystrokesSettings = new KeyStrokeSetting();
        lazyChunkLoadingSettings = new LazyChunkLoadingSetting();
        noHurtCamSettings = new NoHurtCamSetting();
        oldAnimationSettings = new OldAnimationSetting();
        perspectiveSettings = new PerspectiveSetting();
        pingDisplaySettings = new PingDisplaySetting();
        potionStatusSettings = new PoisonStatusSetting();
        scoreboardSettings = new ScoreboardSetting();
        toggleSprintSettings = new ToggleSprintSetting();
        timeChangerSettings = new TimeChangerSettings();
        colorSaturationSettings = new ColorSaturationSettings();
        itemPhysicsSetting = new ItemPhysicsSetting();
        coordinateDisplaySettings = new CoordinateDisplaySettings();
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
                scoreboardSettings, toggleSprintSettings, timeChangerSettings, colorSaturationSettings, itemPhysicsSetting, coordinateDisplaySettings
        );
    }
    public static List<ModSettings> getAllSettings(){
        return allSettings;
    }

    public static ArmorStatusSetting getArmorStatusSettings() {
        return armorStatusSetting;
    }

    public static BlockOverlaySettings getBlockOverlaySettings() {
        return blockOverlaySettings;
    }

    public static BossBarSettings getBossBarSettings() {
        return bossBarSettings;
    }

    public static CustomCrossHairSettings getCustomCrosshairSettings() {
        return customCrosshairSettings;
    }

    public static FPSSetting getFpsSettings() {
        return fpsSettings;
    }

    public static GlintColorSetting getGlintColorSettings() {
        return glintColorSettings;
    }

    public static HitColorSetting getHitColorSettings() {
        return hitColorSettings;
    }

    public static KeyStrokeSetting getKeystrokesSettings() {
        return keystrokesSettings;
    }

    public static LazyChunkLoadingSetting getLazyChunkLoadingSettings() {
        return lazyChunkLoadingSettings;
    }

    public static NoHurtCamSetting getNoHurtCamSettings() {
        return noHurtCamSettings;
    }

    public static OldAnimationSetting getOldAnimationSettings() {
        return oldAnimationSettings;
    }

    public static PerspectiveSetting getPerspectiveSettings() {
        return perspectiveSettings;
    }

    public static PingDisplaySetting getPingDisplaySettings() {
        return pingDisplaySettings;
    }

    public static PoisonStatusSetting getPotionStatusSettings() {
        return potionStatusSettings;
    }

    public static ScoreboardSetting getScoreboardSettings() {
        return scoreboardSettings;
    }

    public static ToggleSprintSetting getToggleSprintSettings() {
        return toggleSprintSettings;
    }


}
