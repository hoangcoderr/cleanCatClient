package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class NoHurtCamSetting extends ModSettings {
    public NoHurtCamSetting() {
        super(ModInstances.getNoHurtCam());
    }
}
