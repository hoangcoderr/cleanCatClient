package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class BlockOverlaySettings extends ModSettings {
    public BlockOverlaySettings() {
        super(ModInstances.getBlockOverlay());
    }
}
