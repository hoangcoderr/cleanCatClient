package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class PoisonStatusSetting extends ModSettings {
    public PoisonStatusSetting() {
        super(ModInstances.getPotionStatus());
    }
}