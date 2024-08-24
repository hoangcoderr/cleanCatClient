package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class GlintColorSetting extends ModSettings {
    public GlintColorSetting() {
        super(ModInstances.getGlintColor());
    }
}
