package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

import java.awt.*;

public class BlockOverlay extends Mod {
    private float lineWidth = 7.0F;
    private boolean outline = true;
    private boolean fill = true;
    public BlockOverlay() {
        super(ModConstants.BLOCK_OVERLAY, ModConstants.BLOCK_OVERLAY_DESC, ModCategory.RENDER);
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
    private static final int DEFAULT_COLOR_OUTLINE = new Color(0,0,0).getRGB();

    private int customColorOutline = new Color(204,64,64).getRGB();

    private static final int DEFAULT_COLOR_INLINE = new Color(0,0,0, 0).getRGB();

    private int customColorInline = new Color(184, 49, 49, 110).getRGB();
    private static final float DEFAULT_LINE_WIDTH = 2F;
    public int getColorOutline(){
        if (isEnabled() && outline){
            return customColorOutline;
        }
        return DEFAULT_COLOR_OUTLINE;
    }

    public void setCustomColorOutline(int customColorOutline) {
        this.customColorOutline = customColorOutline;
    }

    public void setCustomColorInline(int customColorInline) {
        this.customColorInline = customColorInline;
    }
    public int getColorInline(){
        if (isEnabled() && fill){
            return customColorInline;
        }
        return DEFAULT_COLOR_INLINE;
    }
    public float getLineWidth(){
        if (isEnabled()){
            return lineWidth;
        }
        return DEFAULT_LINE_WIDTH;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isOutline() {
        return outline;
    }

    public boolean isFill() {
        return fill;
    }
}
