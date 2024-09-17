package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class FullBright extends Mod {
    public FullBright() {
        super(ModConstants.FULL_BRIGHT, ModConstants.FULL_BRIGHT_DESC, ModCategory.SETTINGS);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            mc.gameSettings.gammaSetting = 1;
        }
        else {
            mc.gameSettings.gammaSetting = 100;
        }
    }
}