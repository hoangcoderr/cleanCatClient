package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class NoHurtCamSettings extends ModSettings {
    public NoHurtCamSettings() {
        super(ModInstances.getNoHurtCam());
    }
}
