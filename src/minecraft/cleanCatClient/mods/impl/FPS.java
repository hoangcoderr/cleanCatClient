package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class FPS extends ModDraggable {
    private int color = -1;
    public FPS() {
        super(ModConstants.FPS, ModConstants.FPS_DESC, ModCategory.RENDER);
        loadConfig();
    }
    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.color = Integer.parseInt(dataConfig[0]);
            setColor(this.color); // Apply the color and transparency to the sliders and square
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.color = -1; // Default color if parsing fails
        }
    }
    @Override
    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.color);
        saveDataConfig(dataConfig);
    }

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
        FontUtil.normal.drawString("FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), color);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString("FPS: 520", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    public void setColor(int color) {
        this.color = color;
        saveConfig();
    }

    public int getColor() {
        return color;
    }

}
