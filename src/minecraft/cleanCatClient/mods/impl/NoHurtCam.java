package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;

public class NoHurtCam extends Mod {


    private static final Minecraft mc = Minecraft.getMinecraft();
    public NoHurtCam() {
        super(ModConstants.NO_HURT_CAM, ModConstants.NO_HURT_CAM_DESC);
    }

}
