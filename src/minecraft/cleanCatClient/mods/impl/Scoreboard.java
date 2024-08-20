package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;

public class Scoreboard extends ModDraggable {
    public Scoreboard() {
        super(ModConstants.SCOREBOARD, ModConstants.SCOREBOARD_DESC, ModCategory.RENDER);
    }

    private boolean isHideRedNumbers = true;
    private boolean isHideRect = true;

    public boolean isHideRedNumbers() {
        return isHideRedNumbers && isEnabled();
    }

    public void setHideRedNumbers(boolean isHideRedNumbers) {
        this.isHideRedNumbers = isHideRedNumbers;
    }

    public boolean isHideRect() {
        return isHideRect && isEnabled();
    }

    public void setHideRect(boolean isHideRect) {
        this.isHideRect = isHideRect;
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

    public ScreenPosition getPos(){
        return pos;
    }
}
