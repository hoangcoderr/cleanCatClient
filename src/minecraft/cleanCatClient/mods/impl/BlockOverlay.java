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
    private static final Color DEFAULT_COLOR = new Color(0,0,0);

    private Color customColor = new Color(204,64,64);
    private static final float DEFAULT_LINE_WIDTH = 2F;
    public Color getColor(){
        if (isEnabled()){
            return customColor;
        }
        return DEFAULT_COLOR;
    }

    public float getLineWidth(){
        if (isEnabled()){
            return lineWidth;
        }
        return DEFAULT_LINE_WIDTH;
    }
}
