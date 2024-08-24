package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class PingDisplaySetting extends ModSettings {
    public PingDisplaySetting() {
        super(ModInstances.getPingDisplay());
    }
}
