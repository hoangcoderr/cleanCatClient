package cleanCatClient.gui.clickgui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

public class CheckBox {
    private int x, y, width, height;
    private String label;
    private boolean checked;
    private Color checkedColor;
    private Color uncheckedColor;
    private boolean isAnimating;
    private float animationProgress;

    public CheckBox(int x, int y, int width, int height, String label, boolean checked) {
        this.x = x;
        this.y = y;
        this.width = width / 3; // Reduce width
        this.height = height / 2; // Reduce height
        this.label = label;
        this.checked = checked;
        this.checkedColor = new Color(0, 255, 0); // Green
        this.uncheckedColor = new Color(255, 0, 0); // Red
        this.isAnimating = false;
        this.animationProgress = checked ? 1.0f : 0.0f;
    }

    public void drawCheckBox(Minecraft mc, int mouseX, int mouseY) {
        if (isAnimating) {
            updateAnimation();
        }

        int drawX = x + (int) (animationProgress * (width / 2));
        Color currentColor = new Color(
                (int) (uncheckedColor.getRed() * (1 - animationProgress) + checkedColor.getRed() * animationProgress),
                (int) (uncheckedColor.getGreen() * (1 - animationProgress) + checkedColor.getGreen() * animationProgress),
                (int) (uncheckedColor.getBlue() * (1 - animationProgress) + checkedColor.getBlue() * animationProgress)
        );

        Gui.drawRoundedRect(x, y, x + width, y + height, 10, currentColor.getRGB());
        Gui.drawRoundedRect(drawX, y, drawX + width / 2, y + height, 10, currentColor.darker().getRGB());
        mc.fontRendererObj.drawString(label, x - mc.fontRendererObj.getStringWidth(label) - 5, y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFFFF);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isMouseOver(mouseX, mouseY)) {
            checked = !checked;
            isAnimating = true;
        }
    }

    private void updateAnimation() {
        if (checked) {
            animationProgress += 0.02f; // Slow down animation
            if (animationProgress >= 1.0f) {
                animationProgress = 1.0f;
                isAnimating = false;
            }
        } else {
            animationProgress -= 0.02f; // Slow down animation
            if (animationProgress <= 0.0f) {
                animationProgress = 0.0f;
                isAnimating = false;
            }
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void reloadPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}