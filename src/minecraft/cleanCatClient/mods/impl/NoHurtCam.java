package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;

public class NoHurtCam extends Mod {


    private static final Minecraft mc = Minecraft.getMinecraft();
    public NoHurtCam() {
        super(ModConstants.NO_HURT_CAM, ModConstants.NO_HURT_CAM_DESC, ModCategory.PLAYER);
    }

}
