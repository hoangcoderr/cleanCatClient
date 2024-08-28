package cleanCatClient.gui.clickgui.components.colorpicker;

import cleanCatClient.gui.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ColorPicker {
    private ColorSlider colorSlider;
    private ColorSlider transparencySlider;
    private ColorSquare colorSquare;
    private boolean isPickerVisible = false;

    // Define rectangle's position and dimensions as class variables

    public ColorSquare getColorSquare() {
        return colorSquare;
    }

    public ColorSlider getColorSlider() {
        return colorSlider;
    }

    public void setPickerVisible(boolean pickerVisible) {
        isPickerVisible = pickerVisible;
    }

    public boolean isPickerVisible() {
        return isPickerVisible;
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
        int squareSize = 20; // Define the size of the small square
        Gui.drawRoundedRect(colorSlider.x - squareSize - 5, colorSlider.y, colorSlider.x - 5, colorSlider.y + squareSize, 15, getColor());
        if (isPickerVisible) {
            colorSlider.drawSlider(mc, mouseX, mouseY);
            transparencySlider.drawSlider(mc, mouseX, mouseY);
            colorSquare.drawSquare(mc, mouseX, mouseY, colorSlider.getColor());

            FontUtil.normal.drawString(colorSlider.getColorString(), colorSlider.x + colorSlider.width + 10, colorSlider.y, 0xFFFFFF);
            FontUtil.normal.drawString(transparencySlider.getTransparencyString(), colorSlider.x + colorSlider.width + 10, colorSlider.y + 10, 0xFFFFFF);
        }
    }

    public void setColor(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (color >> 24) & 0xFF;

        colorSquare.setSelectedColor((r << 16) | (g << 8) | b);
        transparencySlider.setSliderValue(a / 255.0f);

        float[] hsbValues = Color.RGBtoHSB(r, g, b, null);
        colorSlider.setSliderValue(hsbValues[0]);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int squareSize = 20; // Define the size of the small square
        if (mouseX >= colorSlider.x - squareSize - 5 && mouseX <= colorSlider.x - 5 && mouseY >= colorSlider.y && mouseY <= colorSlider.y + squareSize) {
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
            colorSquare.mouseReleased(mouseX, mouseY, state);
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