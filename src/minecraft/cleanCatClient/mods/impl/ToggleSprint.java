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
        super(ModConstants.TOGGLE_SPRINT, ModConstants.TOGGLE_SPRINT_DESC);}

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
        if (isEnabled() && mc.thePlayer != null && !mc.thePlayer.isSprinting()
            && mc.thePlayer.moveForward > 0.0F && !mc.thePlayer.isUsingItem()
            && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking()
            && !mc.thePlayer.isPotionActive(Potion.blindness)) {
            mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX()+1, pos.getAbsoluteY()+1 , -1);
    }


}
