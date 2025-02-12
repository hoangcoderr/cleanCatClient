package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class PoisonStatusSettings extends ModSettings {
    public PoisonStatusSettings() {
        super(ModInstances.getPotionStatus());
    }
}
