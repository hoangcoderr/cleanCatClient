// ColorPicker.java
package cleanCatClient.gui.clickgui.components.colorpicker;

import cleanCatClient.gui.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorPicker {
    private ColorSlider colorSlider;
    private ColorSlider transparencySlider;
    private ColorSquare colorSquare;

    public ColorPicker(int x, int y, int width, int height) {
        colorSquare = new ColorSquare(x + 60, y, width - 60, height);
        colorSlider = new ColorSlider(x, y, 20, height, "Color");
        transparencySlider = new ColorSlider(x + 30, y, 20, height, "Transparency");
    }

    public void drawPicker(Minecraft mc, int mouseX, int mouseY) {
        colorSlider.drawSlider(mc, mouseX, mouseY);
        transparencySlider.drawSlider(mc, mouseX, mouseY);
        colorSquare.drawSquare(mc, mouseX, mouseY, colorSlider.getColor());

        int currentColor = colorSquare.getSelectedColor();
        int transparency = (int) (transparencySlider.getSliderValue() * 255);
        int displayColor = (transparency << 24) | (currentColor & 0x00FFFFFF);

        int rectX = colorSquare.x + colorSquare.width + 10;
        int rectY = colorSquare.y;
        int rectWidth = 50;
        int rectHeight = 50;
        Gui.drawRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight, displayColor);

        FontUtil.normal.drawString(colorSlider.getColorString(), rectX + rectWidth + 10, rectY, 0xFFFFFF);
        FontUtil.normal.drawString(transparencySlider.getTransparencyString(), rectX + rectWidth + 10, rectY + 10, 0xFFFFFF);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        colorSlider.mouseClicked(mouseX, mouseY, mouseButton);
        transparencySlider.mouseClicked(mouseX, mouseY, mouseButton);
        colorSquare.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        colorSlider.mouseReleased(mouseX, mouseY, state);
        transparencySlider.mouseReleased(mouseX, mouseY, state);
    }
}