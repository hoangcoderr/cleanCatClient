package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModConstants;

public class FullBright extends Mod {
    public FullBright() {
        super(ModConstants.FULL_BRIGHT, ModConstants.FULL_BRIGHT_DESC, ModCategory.SETTINGS);
    }

    private Float previousGamma;

    @Override
    public void setEnabled(boolean enabled) {
        boolean wasEnabled = isEnabled();
        super.setEnabled(enabled);

        // Avoid double-applying when state doesn't change
        if (wasEnabled == enabled) {
            return;
        }

        if (enabled) {
            // Store current gamma and force maximum brightness
            if (mc != null && mc.gameSettings != null) {
                previousGamma = mc.gameSettings.gammaSetting;
                mc.gameSettings.gammaSetting = 100.0F;
            }
        } else {
            // Restore previous gamma or reasonable default
            if (mc != null && mc.gameSettings != null) {
                mc.gameSettings.gammaSetting = previousGamma != null ? previousGamma : 1.0F;
            }
            previousGamma = null;
        }
    }
}