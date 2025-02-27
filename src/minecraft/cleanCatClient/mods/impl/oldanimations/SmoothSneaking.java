package cleanCatClient.mods.impl.oldanimations;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SmoothSneaking extends Mod {
    public SmoothSneaking() {
        super(ModConstants.SMOOTH_SNEAKING, ModConstants.SMOOTH_SNEAKING_DESC, ModCategory.PLAYER);
        setEnabled(true);
    }

    private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;
    private final Minecraft mc = Minecraft.getMinecraft();
    private float eyeHeight;
    private float lastEyeHeight;

    public float getEyeHeight(float partialTicks) {
        if (!this.isEnabled() || !ModInstances.getOldAnimation().isEnabled()) {
            return this.mc.getRenderViewEntity().getEyeHeight();
        }
        return lastEyeHeight + (eyeHeight - lastEyeHeight) * partialTicks;
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (!ModInstances.getOldAnimation().isEnabled()) {
            return;
        }
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