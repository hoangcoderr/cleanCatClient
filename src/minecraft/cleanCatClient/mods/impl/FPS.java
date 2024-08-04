package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.renderer.GlStateManager;

public class FPS extends ModDraggable {

    public FPS() {
        super(ModConstants.FPS, ModConstants.FPS_DESC, false);}
    @Override
    public int getWidth() {
       return 50;
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        GlStateManager.pushMatrix();
        FontUtil.normal.drawString("FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY() , -1);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString("FPS: 520", pos.getAbsoluteX(), pos.getAbsoluteY() , -1);
    }


}
