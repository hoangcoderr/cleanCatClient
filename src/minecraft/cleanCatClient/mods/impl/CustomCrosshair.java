package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class CustomCrosshair extends ModDraggable {
    private int thickness = 5;  // Thickness of the crosshair lines
    private int length = 8;     // Length of the crosshair lines
    private int gap = 4;
    private int color = 0xFFFFFFFF; // White color
    private boolean isDot = false;
    private boolean isPlus = true;
    private boolean isArrow = false;
    public CustomCrosshair() {
        super(ModConstants.CROSSHAIR, ModConstants.CROSSHAIR_DESC, ModCategory.RENDER);
        loadConfig();
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }

        try {
            thickness = Integer.parseInt(dataConfig[0]);
            length = Integer.parseInt(dataConfig[1]);
            gap = Integer.parseInt(dataConfig[2]);
            color = Integer.parseInt(dataConfig[3]);
            isDot = Boolean.parseBoolean(dataConfig[4]);
            isPlus = Boolean.parseBoolean(dataConfig[5]);
            isArrow = Boolean.parseBoolean(dataConfig[6]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[7];
        dataConfig[0] = String.valueOf(thickness);
        dataConfig[1] = String.valueOf(length);
        dataConfig[2] = String.valueOf(gap);
        dataConfig[3] = String.valueOf(color);
        dataConfig[4] = String.valueOf(isDot);
        dataConfig[5] = String.valueOf(isPlus);
        dataConfig[6] = String.valueOf(isArrow);
        saveDataConfig(dataConfig);
    }

    public void setColor(int color) {
        this.color = color;
        saveConfig();
    }

    public int getColor() {
        return color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        saveConfig();
    }

    public void setLength(int length) {
        this.length = length;
        saveConfig();

    }

    public void setGap(int gap) {
        this.gap = gap;
        saveConfig();

    }

    public void setDot(boolean isDot) {
        this.isDot = isDot;
        saveConfig();
    }

    public void setPlus(boolean isPlus) {
        this.isPlus = isPlus;
        saveConfig();
    }

    public void setArrow(boolean isArrow) {
        this.isArrow = isArrow;
        saveConfig();
    }

    public boolean isDot() {
        return isDot;
    }

    public boolean isPlus() {
        return isPlus;
    }

    public boolean isArrow() {
        return isArrow;
    }

    public int getThickness() {
        return thickness;
    }

    public int getLength() {
        return length;
    }

    public int getGap() {
        return gap;
    }

    @Override
    public int getWidth() {
        return -1;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public void render(ScreenPosition pos) {
        drawCrosshair(Minecraft.centerX, Minecraft.centerY, gap, length, thickness);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
    }

    public void drawCrosshair(int x, int y, int gap, int length, int thickness) {
        if (isDot) {
            drawDotCrossHair(x, y, length, color);
        }
        if (isPlus) {
            drawPlusCrossHair(x, y, gap, length, thickness, color);
        }
        if (isArrow) {
            drawArrowCrossHair(x, y, length, thickness, color);
        }
    }


    private void drawPlusCrossHair(int x, int y, int gap, int length, int thickness, int color) {
        GL11.glPushMatrix();
        GL11.glColor4f(((color >> 16) & 0xFF) / 255.0f, ((color >> 8) & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f); // Set color
        GL11.glDisable(GL11.GL_TEXTURE_2D); // Disable textures to draw pure shapes
        GL11.glLineWidth(thickness); // Set the thickness of the lines

        // Draw horizontal line
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i(x - (gap + length), y);
        GL11.glVertex2i(x - gap, y);
        GL11.glVertex2i(x + gap, y);
        GL11.glVertex2i(x + (gap + length), y);
        GL11.glEnd();

        // Draw vertical line
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i(x, y - (gap + length));
        GL11.glVertex2i(x, y - gap);
        GL11.glVertex2i(x, y + gap);
        GL11.glVertex2i(x, y + (gap + length));
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D); // Re-enable textures
        GL11.glPopMatrix();
    }

    private void drawDotCrossHair(int x, int y, int size, int color) {
        GL11.glPushMatrix();
        GL11.glColor4f(((color >> 16) & 0xFF) / 255.0f, ((color >> 8) & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f); // Set color
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPointSize(size); // Set the size of the point

        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i(x, y);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    private void drawArrowCrossHair(int x, int y, int size, int thickness, int color) {
        GL11.glPushMatrix();
        GL11.glColor4f(((color >> 16) & 0xFF) / 255.0f, ((color >> 8) & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f); // Set color
        GL11.glDisable(GL11.GL_TEXTURE_2D); // Disable textures to draw pure shapes
        GL11.glLineWidth(thickness); // Set the thickness of the lines

        GL11.glBegin(GL11.GL_LINES);

        // Draw left downward line (45 degrees to the left)
        GL11.glVertex2i(x, y); // center point
        GL11.glVertex2i(x - size, y + size); // left-down point

        // Draw right downward line (45 degrees to the right)
        GL11.glVertex2i(x, y); // center point
        GL11.glVertex2i(x + size, y + size); // right-down point

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D); // Re-enable textures
        GL11.glPopMatrix();
    }
}