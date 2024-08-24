package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class LazyChunkLoadingSetting extends ModSettings {
    public LazyChunkLoadingSetting() {
        super(ModInstances.getLazyChunkLoading());
    }
}
