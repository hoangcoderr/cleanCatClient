package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;

public class ToggleSprint extends ModDraggable {
    public boolean flyBoost = false;
    public float flyBoostFactor = 1.0F;
    public int keyHoldTicks = 7;
    public boolean shiftToggled = false;
    private boolean isShowText = true;
    private int color = -1;
    public ToggleSprint() {
        super(ModConstants.TOGGLE_SPRINT, ModConstants.TOGGLE_SPRINT_DESC, ModCategory.PLAYER);
        loadConfig();
    }

    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.color = Integer.parseInt(dataConfig[0]);
            this.isShowText = Boolean.parseBoolean(dataConfig[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.color = -1;
        }
    }

    public void saveConfig() {
        String[] dataConfig = new String[2];
        dataConfig[0] = String.valueOf(this.color);
        dataConfig[1] = String.valueOf(this.isShowText);
        saveDataConfig(dataConfig);
    }

    public void setColor(int color) {
        this.color = color;
        saveConfig();
    }

    public int getColor() {
        return color;
    }

    public boolean isShowText() {
        return isShowText;
    }

    public void setShowText(boolean showText) {
        isShowText = showText;
        saveConfig();
    }


    public int getWidth() {
        return (int) FontUtil.normal.getStringWidth("[Sprinting (Key Toggled)]");
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void render(ScreenPosition pos) {
        if (isShowText) {
            //FontUtil.normal.drawStringWithShadow(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX(), pos.getAbsoluteY(), color);
           FontUtil.normal.drawStringWithShadow(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX(), pos.getAbsoluteY(), color);
            //mc.fontRendererObj.drawStringWithShadow("aX = " + pos.getAbsoluteX() + " aY = " + pos.getAbsoluteY() + " rX = " + pos.getRelativeX() + " rY = " + pos.getRelativeY(), pos.getAbsoluteX(), pos.getAbsoluteY(), color);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawStringWithShadow(ModConstants.TOGGLE_SPRINT_ENABLED, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color);
    }


}
