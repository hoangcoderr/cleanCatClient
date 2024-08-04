package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;

public class FullBright extends Mod {
    public FullBright(boolean isEnabled) {
        super("Full bright", "Make game full bright");
        setEnabled(isEnabled);
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