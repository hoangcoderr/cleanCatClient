// ColorSquare.java
package cleanCatClient.gui.clickgui.components.colorpicker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorSquare {
    public int x, y, width, height;
    private int selectedColor;
    private int baseColor;
    private boolean dragging;

    public ColorSquare(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selectedColor = 0xFFFFFFFF; // Default white color
        this.dragging = false;
    }

    public void drawSquare(Minecraft mc, int mouseX, int mouseY, int baseColor) {
        this.baseColor = baseColor;
        // Draw color gradient square with increased white and black
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float ratioX = (float) i / width;
                float ratioY = (float) j / height;
                int color = blendColors(baseColor, 0xFFFFFFFF, ratioX); // Use white
                color = blendColors(color, 0xFF000000, ratioY); // Use black
                Gui.drawRect(x + i, y + j, x + i + 1, y + j + 1, color);
            }
        }
        if (dragging) {
            updateColor(mouseX, mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            dragging = true;
            updateColor(mouseX, mouseY);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    private void updateColor(int mouseX, int mouseY) {
        int localX = mouseX - x;
        int localY = mouseY - y;
        float ratioX = (float) localX / width;
        float ratioY = (float) localY / height;
        selectedColor = blendColors(baseColor, 0xFFFFFFFF, ratioX); // Use white
        selectedColor = blendColors(selectedColor, 0xFF000000, ratioY); // Use black
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int color) {
        this.selectedColor = color;
    }

    private int blendColors(int color1, int color2, float ratio) {
        int r = (int)(((color1 >> 16) & 0xFF) * (1 - ratio) + ((color2 >> 16) & 0xFF) * ratio);
        int g = (int)(((color1 >> 8) & 0xFF) * (1 - ratio) + ((color2 >> 8) & 0xFF) * ratio);
        int b = (int)((color1 & 0xFF) * (1 - ratio) + (color2 & 0xFF) * ratio);
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}