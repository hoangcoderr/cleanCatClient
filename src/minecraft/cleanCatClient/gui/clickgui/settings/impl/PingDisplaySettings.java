package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class PingDisplaySettings extends ModSettings {
    public PingDisplaySettings() {
        super(ModInstances.getPingDisplay());
    }
}
