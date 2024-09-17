package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class MinimalViewBobbing extends Mod {
    public MinimalViewBobbing(){
        super(ModConstants.MINIMAL_VIEW_BOBBING, ModConstants.MINIMAL_VIEW_BOBBING_DESC, ModCategory.SETTINGS);
        setEnabled(true);
    }
}
