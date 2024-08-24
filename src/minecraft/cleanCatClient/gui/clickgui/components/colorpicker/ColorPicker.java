package cleanCatClient.gui.clickgui.components.colorpicker;

import cleanCatClient.gui.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorPicker {
    private ColorSlider colorSlider;
    private ColorSlider transparencySlider;
    private ColorSquare colorSquare;
    private boolean isPickerVisible = false;

    public ColorSquare getColorSquare() {
        return colorSquare;
    }

    public ColorSlider getColorSlider() {
        return colorSlider;
    }

    public ColorPicker(int x, int y, int width, int height) {
        colorSquare = new ColorSquare(x + 60, y, width - 60, height);
        colorSlider = new ColorSlider(x, y, 20, height, "Color");
        transparencySlider = new ColorSlider(x + 30, y, 20, height, "Transparency");
    }

    public int getColor() {
        int currentColor = colorSquare.getSelectedColor();
        int transparency = (int) (transparencySlider.getSliderValue() * 255);
        return (transparency << 24) | (currentColor & 0x00FFFFFF);
    }

    public void drawPicker(Minecraft mc, int mouseX, int mouseY) {
        int rectX = colorSquare.x - 150; // Move the rectangle to the left of the color square
        int rectY = colorSquare.y;
        int rectWidth = 50;
        int rectHeight = 50;
        Gui.drawRoundedRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight, 15, getColor());

        if (isPickerVisible) {
            colorSlider.drawSlider(mc, mouseX, mouseY);
            transparencySlider.drawSlider(mc, mouseX, mouseY);
            colorSquare.drawSquare(mc, mouseX, mouseY, colorSlider.getColor());

            FontUtil.normal.drawString(colorSlider.getColorString(), rectX + rectWidth + 10, rectY, 0xFFFFFF);
            FontUtil.normal.drawString(transparencySlider.getTransparencyString(), rectX + rectWidth + 10, rectY + 10, 0xFFFFFF);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int rectX = colorSquare.x - 150; // Move the rectangle to the left of the color square
        int rectY = colorSquare.y;
        int rectWidth = 50;
        int rectHeight = 50;

        if (mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight) {
            isPickerVisible = !isPickerVisible;
        }

        if (isPickerVisible) {
            colorSlider.mouseClicked(mouseX, mouseY, mouseButton);
            transparencySlider.mouseClicked(mouseX, mouseY, mouseButton);
            colorSquare.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (isPickerVisible) {
            colorSlider.mouseReleased(mouseX, mouseY, state);
            transparencySlider.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void setXColorSquare(int x) {
        colorSquare.x = x;
    }

    public void setYColorSquare(int y) {
        colorSquare.y = y;
    }

    public void setXColorSlider(int x) {
        colorSlider.x = x;
    }

    public void setYColorSlider(int y) {
        colorSlider.y = y;
    }

    public void setXTransparencySlider(int x) {
        transparencySlider.x = x;
    }

    public void setYTransparencySlider(int y) {
        transparencySlider.y = y;
    }

    public void reloadPosition(int x, int y) {
        setXColorSquare(x + 60);
        setYColorSquare(y);
        setXColorSlider(x);
        setYColorSlider(y);
        setXTransparencySlider(x + 30);
        setYTransparencySlider(y);
    }
}