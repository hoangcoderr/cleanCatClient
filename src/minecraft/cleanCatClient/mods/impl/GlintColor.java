package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

import java.awt.*;

public class GlintColor extends Mod {
    public GlintColor() {
        super(ModConstants.GLINT_COLOR, ModConstants.GLINT_COLOR_DESC, ModCategory.PLAYER);
        loadConfig();
    }

    private static final Color DEFAULT_COLOR = new Color(128, 64,204);

    private Color customColor = new Color(204,64,64);

    public Color getColor(){
        if (isEnabled()){
            return customColor;
        }
        return DEFAULT_COLOR;
    }

    public void setColor(int color) {
        this.customColor = new Color(color);
        saveConfig();
    }

    @Override
    public void loadConfig() {
        String[] data = loadDataConfig();
        if (data == null) return;
        customColor = new Color(Integer.parseInt(data[0]));
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(customColor.getRGB());
        saveDataConfig(dataConfig);
    }
}
