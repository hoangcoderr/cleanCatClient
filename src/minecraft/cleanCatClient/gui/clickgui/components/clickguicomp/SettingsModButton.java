package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class SettingsModButton {

    public int x, y, w, h;
    public int originalY;
    public Mod mod;

    public SettingsModButton(int x, int y, int w, int h, Mod mod) {
        this.x = x;
        this.y = y;
        this.originalY = this.y;
        this.w = w;
        this.h = h;
        this.mod = mod;
    }

    public void render(int mouseX, int mouseY) {
        // Determine the color based on hover state
        Color borderColor = isMouseOver(mouseX, mouseY) ? new Color(255, 255, 255, 150) : new Color(255, 255, 255, 50);
        Color innerBorderColor = isMouseOver(mouseX, mouseY) ? new Color(77, 76, 76, 150) : new Color(77, 76, 76, 97);

        // Draw the light white border (outer border)
        Gui.drawRoundedRect(x - 2, y - 2, x + w + 2, y + h + 2, 10, borderColor.getRGB());

        // Draw the dark border (inner border)
        Gui.drawRoundedRect(x, y, x + w, y + h, 8, innerBorderColor.getRGB());

        // Draw the toggle switch on the right
        int toggleX = x + w - 35; // Adjusted position to accommodate larger size
        int toggleY = y + (h / 2) - 7; // Adjusted position to accommodate larger size
        int toggleWidth = 30; // Increased width
        int toggleHeight = 14; // Height remains the same
        Color toggleColor = mod.isEnabled() ? new Color(92, 255, 100, 255) : new Color(255, 64, 59, 255);
        Gui.drawRoundedRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 7, toggleColor.getRGB());

        // Draw the "on" or "off" text in the middle of the toggle switch
        String toggleText = mod.isEnabled() ? "ON" : "OFF";
        int color = mod.isEnabled() ? new Color(92, 255, 100, 255).getRGB() : new Color(255, 64, 59, 255).getRGB();
        int textWidthToggle =(int) FontUtil.normal.getStringWidth(toggleText);
        int textXToggle = toggleX + (toggleWidth - textWidthToggle) / 2;
        int textYToggle = toggleY + (toggleHeight - 8) / 2; // 8 is the approximate height of the text
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(toggleText, textXToggle, textYToggle, color);

        // Calculate the width and height of the mod name text
        double textWidth = FontUtil.normal.getStringWidth(mod.name);
        int textHeight = 12;

        // Calculate the position to align the text to the left
        double textX = x + 5; // Adjusted to add some padding from the left edge
        int textY = y + (h - textHeight) / 2;

        // Draw the mod name text
        FontUtil.normal.drawString(mod.name, textX, textY, new Color(243, 236, 236, 255).getRGB());
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public void onClick(int mouseX, int mouseY, int button) {
        int toggleX1 = x + w - 35; // Adjusted position to accommodate larger size
        int toggleY1 = y + (h / 2) - 7; // Adjusted position to accommodate larger size
        int toggleX2 = toggleX1 + 30; // Increased width
        int toggleY2 = toggleY1 + 14; // Height remains the same

        if (button == 0) {
            if (mouseX >= toggleX1 && mouseX <= toggleX2 && mouseY >= toggleY1 && mouseY <= toggleY2) {
                mod.setEnabled(!mod.isEnabled());
            }
        }
    }
}