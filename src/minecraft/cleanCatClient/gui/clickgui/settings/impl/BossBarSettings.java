package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class BossBarSettings extends ModSettings {
    public BossBarSettings(){
        super(ModInstances.getBossBar());
    }
}
