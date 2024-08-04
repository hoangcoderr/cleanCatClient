package cleanCatClient.mods;

import cleanCatClient.utils.FileManager;
import cleanCatClient.gui.hud.IRenderer;
import cleanCatClient.gui.hud.ScreenPosition;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {

    protected ScreenPosition pos;

    public ModDraggable(String name, String description, boolean isEnabled) {
        super(name, description);
        pos = loadPositionFromFile();
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
    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (font.FONT_HEIGHT + 3) * lineNum;
    }

    private void savePositionToFile() {
        FileManager.writeJsonToFile(new File(this.getFolder(), "pos.json"), this.pos);
    }
    private ScreenPosition loadPositionFromFile() {
        ScreenPosition loaded = FileManager.<ScreenPosition>readFromJson(new File(this.getFolder(), "pos.json"), ScreenPosition.class);
        if (loaded == null) {
            loaded = ScreenPosition.fromRelativePosition(0.5, 0.5);
            this.pos = loaded;
            this.savePositionToFile();
        }
        return loaded;
    }

}
