package cleanCatClient.gui.clickgui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class CheckBox {
    private int x, y, width, height;
    private String label;
    private boolean checked;
    private Color checkedColor;
    private Color uncheckedColor;

    public CheckBox(int x, int y, int width, int height, String label, boolean checked) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.checked = checked;
        this.checkedColor = new Color(0, 255, 0); // Green
        this.uncheckedColor = new Color(255, 0, 0); // Red
    }

    public void drawCheckBox(Minecraft mc, int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY)){
            // doi chuot sang ban tay
        }
        Gui.drawRoundedRect(x, y, x + width, y + height,15, checked ? checkedColor.getRGB() : uncheckedColor.getRGB());
        mc.fontRendererObj.drawString(label, x + width + 5, y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFFFF);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isMouseOver(mouseX, mouseY)) {
            checked = !checked;
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
}