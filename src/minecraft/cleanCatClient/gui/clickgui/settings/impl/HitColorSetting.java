package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class HitColorSetting extends ModSettings {
    public HitColorSetting() {
        super(ModInstances.getHitColor());
    }
}
