package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;

public class BlockOverlay extends Mod {
    private float lineWidth = 2.0F;
    public BlockOverlay(boolean isEnabled) {
        super("BlockOverlay", "Edit block overlay");
        setEnabled(isEnabled);
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
           lineWidth = 7.0F;
        }
        else {
           lineWidth = 2.0F;
        }
    }
}
