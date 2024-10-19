package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class ItemCounterSetting extends ModSettings {
    public ItemCounterSetting() {
        super(ModInstances.getItemCounter());
    }
}
