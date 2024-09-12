package cleanCatClient.mods;

import cleanCatClient.constants.ModConstants;

public class MinimalViewBobbing extends Mod{
    public MinimalViewBobbing(){
        super(ModConstants.MINIMAL_VIEW_BOBBING, ModConstants.MINIMAL_VIEW_BOBBING_DESC, ModCategory.PLAYER);
        setEnabled(true);
    }
}
