package cleanCatClient.mods.impl.oldanimations;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventManager;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SmoothSneaking extends Mod {
    public SmoothSneaking() {
        super(ModConstants.SMOOTH_SNEAKING, ModConstants.SMOOTH_SNEAKING_DESC);
        setEnabled(true);
        if (this.isEnabled()) {
            EventManager.unregister(this);
            EventManager.register(this);
        }
    } private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;
    private Minecraft mc = Minecraft.getMinecraft();
    private float eyeHeight;
    private float lastEyeHeight;
    public float getEyeHeight(float partialTicks) {
        if (!this.isEnabled()){
           return this.mc.getRenderViewEntity().getEyeHeight();
        }
        return lastEyeHeight + (eyeHeight - lastEyeHeight) * partialTicks;
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
            lastEyeHeight = eyeHeight;
            final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) {
                eyeHeight = START_HEIGHT;
                return;
            }

            if (player.isSneaking()) {
                eyeHeight = END_HEIGHT;
            } else if (eyeHeight < START_HEIGHT) {
                float delta = START_HEIGHT - eyeHeight;
                delta *= 0.4;
                eyeHeight = START_HEIGHT - delta;
            }
        }

}