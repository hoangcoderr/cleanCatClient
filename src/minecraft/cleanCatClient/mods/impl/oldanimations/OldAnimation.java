package cleanCatClient.mods.impl.oldanimations;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.clickgui.settings.impl.OldAnimationSetting;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class OldAnimation extends Mod {
    public OldAnimation() {
        super(ModConstants.OLD_ANIMATIONS,ModConstants.OLD_ANIMATIONS_DESC, ModCategory.PLAYER);
    }
}
