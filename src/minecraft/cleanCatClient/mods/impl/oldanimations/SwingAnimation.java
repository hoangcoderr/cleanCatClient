package cleanCatClient.mods.impl.oldanimations;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.clickgui.settings.impl.OldAnimationSetting;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;

public class SwingAnimation extends Mod {
    public SwingAnimation() {
        super(ModConstants.SWING_ANIMATION, ModConstants.SWING_ANIMATION_DESC, ModCategory.PLAYER);
    }

    private final Minecraft mc = Minecraft.getMinecraft();
    public float prevSwingProgress;
    public float swingProgress;
    private int swingProgressInt;
    private boolean isSwingInProgress;

    public float getSwingProgress(float partialTickTime) {
        float currentProgress = this.swingProgress - this.prevSwingProgress;

        if (!isSwingInProgress) {
            return mc.thePlayer.getSwingProgress(partialTickTime);
        }

        if (currentProgress < 0.0F) {
            ++currentProgress;
        }

        return this.prevSwingProgress + currentProgress * partialTickTime;
    }

    private int getArmSwingAnimationEnd(EntityPlayerSP player) {
        return player.isPotionActive(Potion.digSpeed) ? 5 - player.getActivePotionEffect(Potion.digSpeed).getAmplifier() :
                (player.isPotionActive(Potion.digSlowdown) ? 8 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 : 6);
    }

    private void updateSwingProgress() {
        final EntityPlayerSP player = mc.thePlayer;
        if (player == null) {
            return;
        }

        prevSwingProgress = swingProgress;

        int max = getArmSwingAnimationEnd(player);

        if (mc.gameSettings.keyBindAttack.isKeyDown() &&
                mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (!this.isSwingInProgress || this.swingProgressInt >= max >> 1 || this.swingProgressInt < 0) {
                isSwingInProgress = true;
                swingProgressInt = -1;
            }
        }

        if (this.isSwingInProgress) {
            ++this.swingProgressInt;

            if (this.swingProgressInt >= max) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        } else {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float) this.swingProgressInt / (float) max;
    }

    @EventTarget
    public void onRender(ClientTickEvent event) {
        if (ModInstances.getOldAnimation().isEnabled())
            updateSwingProgress();
    }

}
