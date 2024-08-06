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
        File posFile = new File(getFolder(), "pos.json");
        if (!posFile.exists()) {
            ScreenPosition defaultPos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            savePositionToFile();
            return defaultPos;
        }
        ScreenPosition loadedPos = FileManager.readFromJson(posFile, ScreenPosition.class);
        return loadedPos != null ? loadedPos : ScreenPosition.fromRelativePosition(0.5, 0.5);
    }

}
