package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class PerspectiveSetting extends ModSettings {
    public PerspectiveSetting() {
        super(ModInstances.getPerspective());
    }
}
