package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;

import java.awt.*;

public class GlintColor extends Mod {
    public GlintColor() {
        super(ModConstants.GLINT_COLOR, ModConstants.GLINT_COLOR_DESC);
    }

    private static final Color DEFAULT_COLOR = new Color(128, 64,204);

    private Color customColor = new Color(204,64,64);

    public Color getColor(){
        if (isEnabled()){
            return customColor;
        }
        return DEFAULT_COLOR;
    }
}
