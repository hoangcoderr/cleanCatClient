package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

import java.awt.*;

public class BlockOverlay extends Mod {
    private float lineWidth = 7.0F;
    private boolean outline = false;
    private boolean fill = false;

    public BlockOverlay() {
        super(ModConstants.BLOCK_OVERLAY, ModConstants.BLOCK_OVERLAY_DESC, ModCategory.RENDER);
        loadConfig();
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        saveConfig();
    }

    private static final int DEFAULT_COLOR_OUTLINE = new Color(0, 0, 0).getRGB();

    private int customColorOutline = new Color(204, 64, 64).getRGB();

    private static final int DEFAULT_COLOR_INLINE = new Color(0, 0, 0, 0).getRGB();

    private int customColorInline = new Color(184, 49, 49, 110).getRGB();
    private static final float DEFAULT_LINE_WIDTH = 2F;

    public int getColorOutline() {
        if (isEnabled() && outline) {
            return customColorOutline;
        }
        return DEFAULT_COLOR_OUTLINE;
    }

    public void setCustomColorOutline(int customColorOutline) {
        this.customColorOutline = customColorOutline;
        saveConfig();
    }

    public void setCustomColorInline(int customColorInline) {
        this.customColorInline = customColorInline;
        saveConfig();

    }

    public int getColorInline() {
        if (isEnabled() && fill) {
            return customColorInline;
        }
        return DEFAULT_COLOR_INLINE;
    }

    public float getLineWidth() {
        if (isEnabled()) {
            return lineWidth;
        }
        return DEFAULT_LINE_WIDTH;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
        saveConfig();
    }

    public void setFill(boolean fill) {
        this.fill = fill;
        saveConfig();
    }

    public boolean isOutline() {
        return outline;
    }

    public boolean isFill() {
        return fill;
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }

        try {
            this.customColorOutline = Integer.parseInt(dataConfig[0]);
            this.customColorInline = Integer.parseInt(dataConfig[1]);
            this.lineWidth = Float.parseFloat(dataConfig[2]);
            this.outline = Boolean.parseBoolean(dataConfig[3]);
            this.fill = Boolean.parseBoolean(dataConfig[4]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.customColorOutline = new Color(0, 0, 0).getRGB();
            this.customColorInline = new Color(0, 0, 0, 47).getRGB();
            this.lineWidth = 2F;
            this.outline = true;
            this.fill = true;
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
            saveConfig();

        }
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[5];
        dataConfig[0] = String.valueOf(this.customColorOutline);
        dataConfig[1] = String.valueOf(this.customColorInline);
        dataConfig[2] = String.valueOf(this.lineWidth);
        dataConfig[3] = String.valueOf(this.outline);
        dataConfig[4] = String.valueOf(this.fill);
        saveDataConfig(dataConfig);
    }
}
