package cleanCatClient.gui.clickgui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeyBindSetting {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final String label;
    private int keyBind;
    private boolean listening = false;

    public KeyBindSetting(int x, int y, int width, int height, String label, int keyBind) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.keyBind = keyBind;
    }

    public void reloadPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawKeyBindSetting(Minecraft mc, int mouseX, int mouseY) {
        String displayText = label + ": " + (listening ? "Listening..." : Keyboard.getKeyName(keyBind));
        int textWidth = mc.fontRendererObj.getStringWidth(displayText);
        int textHeight = mc.fontRendererObj.FONT_HEIGHT;

        // Draw a faint black rectangle around the text
        int rectPadding = 2;
        int rectX = x - rectPadding;
        int rectY = y - rectPadding;
        int rectWidth = textWidth + rectPadding * 2;
        int rectHeight = textHeight + rectPadding * 2;
        int rectColor = 0x80000000; // Faint black color with some transparency

        drawRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight, rectColor);

        // Draw the text
        mc.fontRendererObj.drawStringWithShadow(displayText, x, y, 0xFFFFFF);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            listening = !listening;
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (listening) {
            keyBind = keyCode;
            listening = false;
        }
    }

    public int getKeyBind() {
        return keyBind;
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        Gui.drawRect(left, top, right, bottom, color);
    }
}