package cleanCatClient.mods;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.utils.FileManager;
import cleanCatClient.gui.hud.IRenderer;
import cleanCatClient.gui.hud.ScreenPosition;
import net.minecraft.client.gui.ScaledResolution;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {

    protected ScreenPosition pos;
    protected int width = 100;
    protected int height = 100;

    public ModDraggable(String name, String description, ModCategory category) {
        super(name, description, category);
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

    public void setScreenPosition(ScreenPosition pos) {
        this.pos = pos;
    }

    public ScreenPosition getScreenPosition() {
        return this.pos;
    }
    private int getLineOffset(int lineNum) {
        return (font.FONT_HEIGHT + 3) * lineNum;
    }

    protected void savePositionToFile() {
        FileManager.writeJsonToFile(new File(this.getFolder(), "pos.json"), this.pos);
        FileManager.writeJsonToFile(new File(this.getFolder(), "size.json"), new int[]{this.width, this.height});
    }


    private ScreenPosition loadPositionFromFile() {
        File posFile = new File(getFolder(), "pos.json");
        if (!posFile.exists()) {
            ScreenPosition defaultPos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            savePositionToFile();
            return defaultPos;
        }
        ScreenPosition loadedPos = FileManager.readFromJson(posFile, ScreenPosition.class);
        if (loadedPos == null) {
            loadedPos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        File sizeFile = new File(getFolder(), "size.json");
        if (sizeFile.exists()) {
            int[] size = FileManager.readFromJson(sizeFile, int[].class);
            if (size != null && size.length == 2) {
                this.width = size[0];
                this.height = size[1];
            }
        }

        return loadedPos;
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
