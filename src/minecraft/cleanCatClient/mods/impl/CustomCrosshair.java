package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class CustomCrosshair extends ModDraggable {
    private int thickness = 5;  // Thickness of the crosshair lines
    private int length = 8;     // Length of the crosshair lines
    private int gap = 4;
    public CustomCrosshair() {
        super(ModConstants.CROSSHAIR, ModConstants.CROSSHAIR_DESC);
        pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
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
        drawCrosshair(pos, gap, length, thickness);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        //drawCrosshair(pos);
    }

    private void drawCrosshair(ScreenPosition pos, int gap, int length, int thickness) {
        //drawArrowCrossHair(4, thickness);
        drawPlusCrossHair(3, 4, 2);
        //drawDotCrossHair(5);
    }

    private void drawPlusCrossHair(int gap, int length, int thickness){
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Set color to white
        GL11.glDisable(GL11.GL_TEXTURE_2D); // Disable textures to draw pure shapes
        GL11.glLineWidth(thickness); // Set the thickness of the lines

        // Draw horizontal line
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i(pos.getAbsoluteX() - (gap + length), pos.getAbsoluteY());
        GL11.glVertex2i(pos.getAbsoluteX() - gap, pos.getAbsoluteY());
        GL11.glVertex2i(pos.getAbsoluteX() + gap, pos.getAbsoluteY());
        GL11.glVertex2i(pos.getAbsoluteX() + (gap + length), pos.getAbsoluteY());
        GL11.glEnd();

        // Draw vertical line
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY() - (gap + length));
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY() - gap);
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY() + gap);
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY() + (gap + length));
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D); // Re-enable textures
        GL11.glPopMatrix();

    }
    private void drawDotCrossHair(int size) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Set color to white
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPointSize(size); // Set the size of the point

        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY());
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    private void drawArrowCrossHair(int size, int thickness) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Set color to white
        GL11.glDisable(GL11.GL_TEXTURE_2D); // Disable textures to draw pure shapes
        GL11.glLineWidth(thickness); // Set the thickness of the lines

        GL11.glBegin(GL11.GL_LINES);

        // Draw left downward line (45 degrees to the left)
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY()); // center point
        GL11.glVertex2i(pos.getAbsoluteX() - size, pos.getAbsoluteY() + size); // left-down point

        // Draw right downward line (45 degrees to the right)
        GL11.glVertex2i(pos.getAbsoluteX(), pos.getAbsoluteY()); // center point
        GL11.glVertex2i(pos.getAbsoluteX() + size, pos.getAbsoluteY() + size); // right-down point

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D); // Re-enable textures
        GL11.glPopMatrix();
    }
}