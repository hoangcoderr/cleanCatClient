package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.world.MinecraftException;

public class ToggleSprint extends ModDraggable {
    public boolean flyBoost = false;
    public float flyBoostFactor = 1.0F;
    public int keyHoldTicks = 7;
    public boolean shiftToggled = false;

    public ToggleSprint() {
        super(ModConstants.TOGGLE_SPRINT, ModConstants.TOGGLE_SPRINT_DESC, ModCategory.PLAYER);}

    public int getWidth()
    {
        return (int)FontUtil.normal.getStringWidth("[Sprinting (Key Toggled)]");
    }

    public int getHeight()
    {
        return mc.fontRendererObj.FONT_HEIGHT;
    }


    @Override
    public void render(ScreenPosition pos) {
        //RenderUtils.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), getWidth(), getHeight());
        FontUtil.normal.drawStringWithShadow(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX(), pos.getAbsoluteY() , -1);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawStringWithShadow(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX()+1, pos.getAbsoluteY()+1 , -1);
    }


}
