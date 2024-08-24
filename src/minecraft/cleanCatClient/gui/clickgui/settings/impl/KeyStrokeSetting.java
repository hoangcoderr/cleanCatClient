package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class KeyStrokeSetting extends ModSettings {

    public KeyStrokeSetting() {
        super(ModInstances.getKeystrokes());
    }
}
