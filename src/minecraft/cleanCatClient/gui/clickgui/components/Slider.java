package cleanCatClient.gui.clickgui.components;

import cleanCatClient.gui.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Slider {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final float minValue;
    private final float maxValue;
    private float currentValue;
    private final float step;
    private boolean dragging;

    public Slider(int x, int y, int width, int height, float minValue, float maxValue, float currentValue, float step) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.step = step;
        this.dragging = false;
    }

    public void drawSlider(Minecraft mc, int mouseX, int mouseY) {
        int sliderPos = x + (int) ((currentValue - minValue) / (maxValue - minValue) * width);
        Gui.drawRoundedRect(x, y, x + width, y + height, 15,0xFFAAAAAA); // Background
        Gui.drawRect(sliderPos - 2, y, sliderPos + 2, y + height,0xFF555555); // Slider knob

        if (dragging) {
            updateValue(mouseX);
        }

        // Draw the current value to the right of the slider
        String valueText = String.format("%.2f", currentValue);
        int textX = x + width + 5; // 5 pixels to the right of the slider
        int textY = y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2); // Center vertically
        FontUtil.normal.drawString(valueText, textX, textY, 0xFFFFFFFF); // White color
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isMouseOver(mouseX, mouseY)) {
            dragging = true;
            updateValue(mouseX);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            dragging = false;
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void updateValue(int mouseX) {
        float newValue = minValue + (mouseX - x) / (float) width * (maxValue - minValue);
        newValue = Math.round(newValue / step) * step;
        currentValue = Math.max(minValue, Math.min(maxValue, newValue));
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = Math.max(minValue, Math.min(maxValue, currentValue));
    }

    public void reloadPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}