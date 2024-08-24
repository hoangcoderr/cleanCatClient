package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class FPS extends ModDraggable {

    public FPS() {
        super(ModConstants.FPS, ModConstants.FPS_DESC, ModCategory.RENDER);}
    @Override
    public int getWidth() {
       return 50;
    }
    private int color = -1;
    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        GlStateManager.pushMatrix();
        FontUtil.normal.drawString("FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY() , color);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString("FPS: 520", pos.getAbsoluteX(), pos.getAbsoluteY() , -1);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
