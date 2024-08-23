package cleanCatClient.gui.clickgui.comp;

import java.awt.*;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.clickgui.settings.impl.CustomCrossHairSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import cleanCatClient.gui.clickgui.components.CheckBox;

public class ModButton {

    public int x, y, w, h;
    public int originalY; // Thêm thuộc tính này
    public Mod mod;
    public int id;
    public ModSettings modSettings;

    public ModButton(int x, int y, int w, int h, Mod mod, int id) {
        this.x = x - 85;
        this.y = y - 80;
        this.originalY = this.y;
        this.w = w;
        this.h = h;
        this.mod = mod;
        this.id = id;
    }

    public void render() {
        Gui.drawRoundedRect(x, y, x + w, y + h, 8, new Color(224, 214, 214, 255).getRGB());

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
        FontUtil.normal.drawString(mod.name, textX, textY, new Color(0, 0, 0, 255).getRGB());
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
                Minecraft.getMinecraft().displayGuiScreen(new CustomCrossHairSettings());
            }
        }
    }
}
