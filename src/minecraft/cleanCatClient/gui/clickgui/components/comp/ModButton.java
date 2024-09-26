package cleanCatClient.gui.clickgui.components.comp;

import java.awt.*;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModButton {

    public int x, y, w, h;
    public int originalY;
    public Mod mod;
    public int id;
    public ModSettings modSettings;

    public ModButton(int x, int y, int w, int h, Mod mod, int id, ModSettings modSettings) {
        this.x = x - 85;
        this.y = y - 80;
        this.originalY = this.y;
        this.w = w;
        this.h = h;
        this.mod = mod;
        this.id = id;
        this.modSettings = modSettings;
    }

    public void render(int mouseX, int mouseY) {
        // Determine the color based on hover state
        Color borderColor = isMouseOver(mouseX, mouseY) ? new Color(255, 255, 255, 150) : new Color(255, 255, 255, 50);
        Color innerBorderColor = isMouseOver(mouseX, mouseY) ? new Color(77, 76, 76, 150) : new Color(77, 76, 76, 97);

        // Draw the light white border (outer border)
        Gui.drawRoundedRect(x - 2, y - 2, x + w + 2, y + h + 2, 10, borderColor.getRGB());

        // Draw the dark border (inner border)
        Gui.drawRoundedRect(x, y, x + w, y + h, 8, innerBorderColor.getRGB());

        // Adjust the width of the rounded rectangle to be nearly the same as the width of the ModButton
        int rectWidth = w - 10; // Adjust this value as needed
        Gui.drawRoundedRect(x + 5, (y + h) - 18, x + 5 + rectWidth, (y + h) - 7, 8, getColor());

        // Calculate the width and height of the mod name text
        double textWidth = FontUtil.normal.getStringWidth(mod.name);
        int textHeight = 12;

        // Calculate the position to center the text
        double textX = x + (w - textWidth) / 2;
        int textY = y + (h - textHeight) / 2 - 30;

        // Draw the mod name text with a smaller font size
        FontUtil.normal.drawString(mod.name, textX, textY, new Color(243, 236, 236, 255).getRGB());
    }

    private int getColor() {
        if (mod.isEnabled()) {
            return new Color(131, 255, 92, 255).getRGB();
        } else {
            return new Color(255, 64, 59, 255).getRGB();
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public void onClick(int mouseX, int mouseY, int button) {
        int rectX1 = x + 5;
        int rectY1 = (y + h) - 18;
        int rectX2 = x + 5 + (w - 10);
        int rectY2 = (y + h) - 7;

        if (button == 0) {
            if (mouseX >= rectX1 && mouseX <= rectX2 && mouseY >= rectY1 && mouseY <= rectY2) {
                if (mod.isEnabled()) {
                    mod.setEnabled(false);
                } else {
                    mod.setEnabled(true);
                }
            } else if (isMouseOver(mouseX, mouseY)) {
                Minecraft.getMinecraft().displayGuiScreen(modSettings);
            }
        }
    }
}