package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;
import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.ModCategory;

public class BossBar extends Mod {
    public BossBar() {
        super(ModConstants.BOSS_BAR, ModConstants.BOSS_BAR_DESC, ModCategory.SETTINGS);
    }
}
