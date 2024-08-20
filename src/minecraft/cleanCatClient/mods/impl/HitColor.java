package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;
import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.ModCategory;

import java.awt.*;

public class HitColor extends Mod {
    public HitColor() {
        super(ModConstants.HIT_COLOR, ModConstants.HIT_COLOR_DESC, ModCategory.PLAYER);
    }
    private float colorRed = 3F;
    private float colorGreen = 0F;
    private float colorBlue = 0F;
    private float colorAqua = 1F;

    public float getColorRed() {
        return colorRed;
    }

    public float getColorGreen() {
        return colorGreen;
    }

    public float getColorBlue() {
        return colorBlue;
    }

    public float getColorAqua() {
        return colorAqua;
    }
}
