package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;

import java.awt.*;

public class BlockOverlay extends Mod {
    private float lineWidth = 7.0F;
    public BlockOverlay() {
        super(ModConstants.BLOCK_OVERLAY, ModConstants.BLOCK_OVERLAY_DESC);
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
    private static final Color DEFAULT_COLOR_OUTLINE = new Color(0,0,0);

    private Color customColorOutline = new Color(204,64,64);

    private static final Color DEFAULT_COLOR_INLINE = new Color(0,0,0);

    private Color customColorInline = new Color(0, 0, 0, 45);
    private static final float DEFAULT_LINE_WIDTH = 2F;
    public Color getColorOutline(){
        if (isEnabled()){
            return customColorOutline;
        }
        return DEFAULT_COLOR_OUTLINE;
    }


    public Color getColorInline(){
        if (isEnabled()){
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
}
