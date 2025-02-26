package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

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
        // Existing outer borders remain the same
        Color borderColor = isMouseOver(mouseX, mouseY) ? new Color(255, 255, 255, 150) : new Color(255, 255, 255, 50);
        Color innerBorderColor = isMouseOver(mouseX, mouseY) ? new Color(77, 76, 76, 150) : new Color(77, 76, 76, 97);

        Gui.drawRoundedRect(x - 2, y - 2, x + w + 2, y + h + 2, 10, borderColor.getRGB());
        Gui.drawRoundedRect(x, y, x + w, y + h, 8, innerBorderColor.getRGB());

        int rectWidth = w - 10;
        int toggleX = x + 5;
        int toggleY = (y + h) - 18;
        int toggleHeight = 11;

        Color baseColor = mod.isEnabled() ? new Color(131, 255, 92) : new Color(255, 64, 59);

        // Border color matching base color
        Color toggleBorderColor = new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                100
        );

        // Draw border
        Gui.drawRoundedRect(toggleX - 1, toggleY - 1, toggleX + rectWidth + 1, toggleY + toggleHeight + 1, 3, toggleBorderColor.getRGB());

        boolean isHoveringToggle = mouseX >= toggleX && mouseX <= toggleX + rectWidth &&
                mouseY >= toggleY && mouseY <= toggleY + toggleHeight;

        Color toggleColor = new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                isHoveringToggle ? 200 : 255
        );

        // Draw toggle button background
        Gui.drawRoundedRect(toggleX, toggleY, toggleX + rectWidth, toggleY + toggleHeight, 2, toggleColor.getRGB());

        // Add ON/OFF text - always visible
        String toggleText = mod.isEnabled() ? "ON" : "OFF";
        double textWidth = FontUtil.normal.getStringWidth(toggleText);

        // Center the text
        double toggleTextX = toggleX + (rectWidth - textWidth) / 2;
        double toggleTextY = toggleY + (toggleHeight - 8) / 2;

        // Text is always visible with full opacity, only slightly dimmed on hover
        int textOpacity = isHoveringToggle ? 230 : 255;
        Color textColor = new Color(255, 255, 255, textOpacity);
        FontUtil.normal.drawStringWithShadow(toggleText, toggleTextX, (int)toggleTextY, getColor() + 100 );

        // Rest of the existing render code
            textWidth = FontUtil.normal.getStringWidth(mod.name);
        int textHeight = 12;
        double textX = x + (w - textWidth) / 2;
        int textY = y + (h - textHeight) / 2 - 30;

        FontUtil.normal.drawString(mod.name, textX, textY, new Color(243, 236, 236, 255).getRGB());

        if (isMouseOver(mouseX, mouseY)) {
            drawDescription(mouseX, mouseY, mod.description);
        }
    }

    private void drawDescription(int mouseX, int mouseY, String description) {
        int descriptionWidth = (int)FontUtil.normal.getStringWidth(description);
        int descriptionHeight = 12; // Adjust as needed

        int descriptionX = mouseX + 10; // Offset to the right of the mouse cursor
        int descriptionY = mouseY - descriptionHeight - 5; // Offset above the mouse cursor

        // Draw background rectangle for the description
        Gui.drawRect(descriptionX - 2, descriptionY - 2, descriptionX + descriptionWidth + 2, descriptionY + descriptionHeight + 2, new Color(0, 0, 0, 150).getRGB());

        // Draw the description text
        FontUtil.normal.drawString(description, descriptionX, descriptionY, new Color(255, 255, 255, 255).getRGB());
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
                mod.setEnabled(!mod.isEnabled());
            } else if (isMouseOver(mouseX, mouseY)) {
                Minecraft.getMinecraft().displayGuiScreen(modSettings);
            }
        }
    }
}