package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.event.impl.Render2D;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class ParticlesMultiplier extends Mod {
    public ParticlesMultiplier() {
        super(ModConstants.PARTICLES_MULTIPLIER, ModConstants.PARTICLES_MULTIPLIER_DESC, ModCategory.RENDER);
    }
    private float multiplier = 16;
    private float savedMultiplier = 16;

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            savedMultiplier = multiplier;
            multiplier = 16;

        }
        else {
            multiplier = savedMultiplier;
        }
    }
//    @EventTarget
//    public void onClientTick(Render2D event) {
//        if (this.isEnabled()) {
//            Minecraft mc = Minecraft.getMinecraft();
//            if (mc.theWorld != null) {
//                for (int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
//                    if (mc.theWorld.loadedEntityList.get(i) instanceof EntityFX) {
//                        EntityFX particle = (EntityFX) mc.theWorld.loadedEntityList.get(i);
//                        for (int j = 0; j < multiplier - 1; j++) {
//                            mc.effectRenderer.addEffect(particle);
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }

}
