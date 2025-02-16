package cleanCatClient.mods.impl;


import cleanCatClient.mods.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;

public class CoordinateDisplay extends ModDraggable {
    public CoordinateDisplay() {
        super(ModConstants.COORDINATES, ModConstants.COORDINATES_DESC, ModCategory.RENDER);
        loadConfig();
    }
    private int color = -1;

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.color = Integer.parseInt(dataConfig[0]);
            setColor(this.color);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.color = -1;
        }
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
        saveConfig();
    }

    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.color);
        saveDataConfig(dataConfig);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    private String getXYZ() {
        return String.format("X: %.2f Y: %.2f Z: %.2f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    @Override
    public void render(ScreenPosition pos) {
        //FontUtil.normal.drawString(getXYZ(), pos.getAbsoluteX(), pos.getAbsoluteY(), this.color);
        mc.fontRendererObj.drawStringWithShadow(getXYZ(), pos.getAbsoluteX(), pos.getAbsoluteY(), this.color);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        super.renderDummy(pos);
        FontUtil.normal.drawString("X: 100 Y: 100 Z: 100", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
