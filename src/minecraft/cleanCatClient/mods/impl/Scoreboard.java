package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;

public class Scoreboard extends ModDraggable {
    public Scoreboard() {
        super(ModConstants.SCOREBOARD, ModConstants.SCOREBOARD_DESC, ModCategory.RENDER);
        loadConfig();
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
            pos = ScreenPosition.fromRelativePosition(0.915, 0.43); // Set position to the middle right of the screen
            savePositionToFile();
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
    public int getWidth() {
        return 100; // Example width
    }

    @Override
    public int getHeight() {
        return 100; // Example height
    }

    @Override
    public void render(ScreenPosition pos) {

    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        super.renderDummy(pos);
    }

    public ScreenPosition getPos() {
        return pos;
    }
}
