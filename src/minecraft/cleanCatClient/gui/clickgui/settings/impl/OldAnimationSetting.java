package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.oldanimations.OldAnimation;

public class OldAnimationSetting extends ModSettings {
    public OldAnimationSetting() {
        super(ModInstances.getOldAnimation());
    }
}
