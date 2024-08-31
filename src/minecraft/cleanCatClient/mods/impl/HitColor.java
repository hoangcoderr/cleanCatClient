package cleanCatClient.mods.impl;

import cleanCatClient.mods.Mod;
import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.ModCategory;

import java.awt.*;

public class HitColor extends Mod {
    public HitColor() {
        super(ModConstants.HIT_COLOR, ModConstants.HIT_COLOR_DESC, ModCategory.PLAYER);
        loadConfig();
    }

    private float colorRed = 3F;
    private float colorGreen = 0F;
    private float colorBlue = 0F;
    private float colorAqua = 1F;

    private int saveColor = 0xFF0000FF;

    @Override
    public void loadConfig() {
        String[] data = loadDataConfig();
        if (data == null) return;
        saveColor = Integer.parseInt(data[0]);
        colorRed = (saveColor >> 16 & 255) / 255.0F;
        colorGreen = (saveColor >> 8 & 255) / 255.0F;
        colorBlue = (saveColor & 255) / 255.0F;
        colorAqua = (saveColor >> 24 & 255) / 255.0F;
    }

    @Override
    public void saveConfig() {
        saveColor = new Color(colorRed, colorGreen, colorBlue, colorAqua).getRGB();
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.saveColor);
        saveDataConfig(dataConfig);
    }
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

    public void setColor(int color){
        this.saveColor = color;
        this.colorRed = (color >> 16 & 255) / 255.0F;
        this.colorGreen = (color >> 8 & 255) / 255.0F;
        this.colorBlue = (color & 255) / 255.0F;
        this.colorAqua = (color >> 24 & 255) / 255.0F;
        saveConfig();
    }

    public int getSaveColor() {
        return saveColor;
    }
}
