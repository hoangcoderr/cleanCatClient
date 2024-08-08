package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.world.MinecraftException;

public class ToggleSprint extends ModDraggable {
    public boolean flyBoost = false;
    public float flyBoostFactor = 1.0F;
    public int keyHoldTicks = 7;
    public boolean shiftToggled = false;

    public ToggleSprint() {
        super(ModConstants.TOGGLE_SPRINT, ModConstants.TOGGLE_SPRINT_DESC, false);}

    public int getWidth()
    {
        return mc.fontRendererObj.getStringWidth("[Sprinting (Key Toggled)]");
    }

    public int getHeight()
    {
        return mc.fontRendererObj.FONT_HEIGHT;
    }


    @Override
    public void render(ScreenPosition pos) {
        FontUtil.normal.drawString(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX(), pos.getAbsoluteY() , -1);
    }

    @EventTarget
    public void onEvent(ClientTickEvent c){
        if (isEnabled() && mc.thePlayer != null && !mc.thePlayer.isSneaking() &&
                (mc.thePlayer.motionX != 0.0D || mc.thePlayer.motionZ != 0.0D)
                 &&!mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isPotionActive(Potion.moveSlowdown) && !mc.thePlayer.isPotionActive(Potion.confusion) && !mc.gameSettings.keyBindBack.isKeyDown()) {
            mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX()+1, pos.getAbsoluteY()+1 , -1);
    }


}
