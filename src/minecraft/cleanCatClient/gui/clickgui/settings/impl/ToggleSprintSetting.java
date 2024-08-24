package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class ToggleSprintSetting extends ModSettings {
    public ToggleSprintSetting() {
        super(ModInstances.getToggleSprint());
    }
}
