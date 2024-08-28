// ColorSlider.java
package cleanCatClient.gui.clickgui.components.colorpicker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorSlider {
    public int x, y, width, height;
    private float sliderValue;
    private boolean dragging;
    private String label;

    public ColorSlider(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.sliderValue = 0.0f;
        this.dragging = false;
    }

    public void drawSlider(Minecraft mc, int mouseX, int mouseY) {
        if (dragging) {
            sliderValue = (float)(y + height - mouseY) / height;
            sliderValue = Math.max(0.0f, Math.min(1.0f, sliderValue));
        }

        int sliderPos = (int)(sliderValue * height);
        Gui.drawRoundedRect(x, y, x + width, y + height, 15,0xFFAAAAAA);
        Gui.drawRect(x, y + height - sliderPos - 1, x + width, y + height - sliderPos + 1, 0xFF000000);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            dragging = true;
            sliderValue = (float)(y + height - mouseY) / height;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }

    public float getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(float value) {
        this.sliderValue = Math.max(0.0f, Math.min(1.0f, value));
    }

    public int getColor() {
        int r = 0, g = 0, b = 0;
        float value = sliderValue * 6;
        int segment = (int) value;
        float remainder = value - segment;

        switch (segment) {
            case 0: r = 255; g = (int) (255 * remainder); break;
            case 1: r = (int) (255 * (1 - remainder)); g = 255; break;
            case 2: g = 255; b = (int) (255 * remainder); break;
            case 3: g = (int) (255 * (1 - remainder)); b = 255; break;
            case 4: r = (int) (255 * remainder); b = 255; break;
            case 5: r = 255; b = (int) (255 * (1 - remainder)); break;
        }

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    public String getColorString() {
        int color = getColor();
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        return String.format("RGB: %d, %d, %d", r, g, b);
    }

    public String getTransparencyString() {
        int transparency = (int) (sliderValue * 255);
        return String.format("Transparency: %d", transparency);
    }
}