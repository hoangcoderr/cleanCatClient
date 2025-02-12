package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class ArmorStatusSettings extends ModSettings {
    public ArmorStatusSettings(){
        super(ModInstances.getArmorStatus());
    }
}
