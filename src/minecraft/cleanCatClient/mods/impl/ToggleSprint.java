package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.MinecraftException;

public class ToggleSprint extends ModDraggable {
    public boolean flyBoost = false;
    public float flyBoostFactor = 1.0F;
    public int keyHoldTicks = 7;
    public boolean shiftToggled = false;

    public ToggleSprint() {
        super("ToggleSprint", "Sprint without holding the key", false);
    }

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
        if(mc.thePlayer == null){
            return;
        }
        //if player is moving then enable sprint else not
        if(mc.gameSettings.keyBindForward.isKeyDown()){
                mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX()+1, pos.getAbsoluteY()+1 , -1);
    }


}
