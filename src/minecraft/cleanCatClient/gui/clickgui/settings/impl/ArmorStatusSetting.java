package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModInstances;

public class ArmorStatusSetting extends ModSettings {
    public ArmorStatusSetting(){
        super(ModInstances.getArmorStatus());
    }
}
