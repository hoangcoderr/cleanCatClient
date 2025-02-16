package cleanCatClient.gui.clickgui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.util.List;

public class ComboBox {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final List<String> items;
    private String selectedItem;
    private boolean isOpen;

    public ComboBox(int x, int y, int width, int height, List<String> items) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.items = items;
        this.selectedItem = items.get(0);
        this.isOpen = false;
    }

    public void drawComboBox(Minecraft mc, int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
        mc.fontRendererObj.drawString(selectedItem, x + 5, y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), Color.WHITE.getRGB());

        if (isOpen) {
            for (int i = 0; i < items.size(); i++) {
                int itemY = y + height + (i * height);
                int color = isMouseOverOption(mouseX, mouseY, itemY) ? Color.LIGHT_GRAY.getRGB() : Color.DARK_GRAY.getRGB();
                Gui.drawRect(x, itemY, x + width, itemY + height, color);
                mc.fontRendererObj.drawString(items.get(i), x + 5, itemY + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), Color.WHITE.getRGB());
            }
        }
    }

    public void reloadPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isMouseOver(mouseX, mouseY)) {
                toggleOpen();
            } else if (isOpen) {
                for (int i = 0; i < items.size(); i++) {
                    int itemY = y + height + (i * height);
                    if (isMouseOverOption(mouseX, mouseY, itemY)) {
                        selectItem(i);
                        break;
                    }
                }
                isOpen = false;
            }
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private boolean isMouseOverOption(int mouseX, int mouseY, int itemY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= itemY && mouseY <= itemY + height;
    }

    private void toggleOpen() {
        isOpen = !isOpen;
    }

    private void selectItem(int index) {
        selectedItem = items.get(index);
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }
}