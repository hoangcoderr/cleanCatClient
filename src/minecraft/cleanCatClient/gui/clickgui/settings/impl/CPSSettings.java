package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class CPSSettings extends ModSettings {
    public CPSSettings() {
        super(ModInstances.getCps());
    }
}
