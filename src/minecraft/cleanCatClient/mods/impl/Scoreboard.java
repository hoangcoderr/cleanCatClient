package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;

public class Scoreboard extends ModDraggable {
    public Scoreboard() {
        super(ModConstants.SCOREBOARD, ModConstants.SCOREBOARD_DESC, ModCategory.RENDER);
        loadConfig();
        setHeight(0); // Initialize height
        setWidth(0);  // Initialize width
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            isHideRect = false;
            isHideRedNumbers = false;
            return;
        }
        try {
            setHideRedNumbers(Boolean.parseBoolean(dataConfig[0]));
            setHideRect(Boolean.parseBoolean(dataConfig[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfig() {
        saveDataConfig(new String[]{
                String.valueOf(isHideRedNumbers()),
                String.valueOf(isHideRect())
        });
    }

    private boolean isHideRedNumbers = true;
    private boolean isHideRect = true;

    public boolean isHideRedNumbers() {
        return isHideRedNumbers && isEnabled();
    }

    public void setHideRedNumbers(boolean isHideRedNumbers) {
        this.isHideRedNumbers = isHideRedNumbers;
        this.saveConfig();
    }

    public boolean isHideRect() {
        return isHideRect && isEnabled();
    }

    public void setHideRect(boolean isHideRect) {
        this.isHideRect = isHideRect;
        this.saveConfig();
    }

    @Override
    public void render(ScreenPosition pos) {
        // Render logic
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        super.renderDummy(pos);
    }

    public ScreenPosition getPos() {
        return pos;
    }
}