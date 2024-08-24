// ColorSquare.java
package cleanCatClient.gui.clickgui.components.colorpicker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorSquare {
    public int x, y, width, height;
    private int selectedColor;
    private int baseColor;

    public ColorSquare(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selectedColor = 0xFFFFFFFF; // Default white color
    }

    public void drawSquare(Minecraft mc, int mouseX, int mouseY, int baseColor) {
        this.baseColor = baseColor;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = blendColors(baseColor, 0xFFFFFFFF, (float)i / width);
                color = blendColors(color, 0xFF000000, (float)j / height);
                Gui.drawRect(x + i, y + j, x + i + 1, y + j + 1, color);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            int localX = mouseX - x;
            int localY = mouseY - y;
            selectedColor = blendColors(baseColor, 0xFFFFFFFF, (float)localX / width);
            selectedColor = blendColors(selectedColor, 0xFF000000, (float)localY / height);
        }
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