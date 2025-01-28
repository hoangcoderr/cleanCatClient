package cleanCatClient.mods;

import cleanCatClient.gui.hud.IRenderer;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.manager.ModPosManager;

public abstract class ModDraggable extends Mod implements IRenderer {

    protected ScreenPosition pos;
    protected int width = 100;
    protected int height = 100;

    public ModDraggable(String name, String description, ModCategory category) {
        super(name, description, category);
        pos = ModPosManager.getPosition(name);
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            savePositionToFile();
        }
    }

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
        savePositionToFile();
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }


    private int getLineOffset(int lineNum) {
        return (font.FONT_HEIGHT + 3) * lineNum;
    }

    protected void savePositionToFile() {
        ModPosManager.setPosition(this.name, this.pos);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}