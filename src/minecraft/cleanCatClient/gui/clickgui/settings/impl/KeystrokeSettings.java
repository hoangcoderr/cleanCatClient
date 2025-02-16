package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.ComboBox;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.Keystrokes;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class KeystrokeSettings extends ModSettings {
    private final ComboBox customComboBox;
    private final ColorPicker upColorPicker;
    private final ColorPicker downColorPicker;

    public KeystrokeSettings() {
        super(ModInstances.getKeystrokes());
        this.customComboBox = new ComboBox(Minecraft.centerX - 50, Minecraft.centerY - 50, 100, 20, Arrays.asList("WASD", "WASD_MOUSE", "WASD_JUMP", "WASD_JUMP_MOUSE"));
        this.upColorPicker = new ColorPicker(Minecraft.centerX - 50, Minecraft.centerY + 30, 100, 50);
        this.downColorPicker = new ColorPicker(Minecraft.centerX - 50, Minecraft.centerY + 60, 100, 50);
    }

    @Override
    public void initGui() {
        super.initGui();
        customComboBox.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY - 50);
        upColorPicker.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY + 30);
        downColorPicker.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY + 60);
        upColorPicker.setColor(ModInstances.getKeystrokes().getUpColor().getRGB());
        downColorPicker.setColor(ModInstances.getKeystrokes().getDownColor().getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        customComboBox.drawComboBox(mc, mouseX, mouseY);
        upColorPicker.drawPicker(mc, mouseX, mouseY);
        downColorPicker.drawPicker(mc, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        customComboBox.mouseClicked(mouseX, mouseY, mouseButton);
        upColorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        downColorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        updateKeystrokesMode();
        //updateColors();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        upColorPicker.mouseReleased(mouseX, mouseY, state);
        downColorPicker.mouseReleased(mouseX, mouseY, state);
        updateKeystrokesMode();
        updateColors();
    }

    private void updateKeystrokesMode() {
        String selectedItem = customComboBox.getSelectedItem();
        switch (selectedItem) {
            case "WASD":
                ModInstances.getKeystrokes().setMode(Keystrokes.KeystrokesMode.WASD);
                break;
            case "WASD_MOUSE":
                ModInstances.getKeystrokes().setMode(Keystrokes.KeystrokesMode.WASD_MOUSE);
                break;
            case "WASD_JUMP":
                ModInstances.getKeystrokes().setMode(Keystrokes.KeystrokesMode.WASD_JUMP);
                break;
            case "WASD_JUMP_MOUSE":
                ModInstances.getKeystrokes().setMode(Keystrokes.KeystrokesMode.WASD_JUMP_MOUSE);
                break;
        }
    }

    private void updateColors() {
        int upColorValue = upColorPicker.getColor();
        int downColorValue = downColorPicker.getColor();

        // Extract the alpha component from the color values
        int upAlpha = (upColorValue >> 24) & 0xFF;
        int downAlpha = (downColorValue >> 24) & 0xFF;

        // Create new Color objects with the alpha component
        Color upColor = new Color((upColorValue & 0x00FFFFFF) | (upAlpha << 24), true);
        Color downColor = new Color((downColorValue & 0x00FFFFFF) | (downAlpha << 24), true);

        ModInstances.getKeystrokes().setUpColor(upColor);
        ModInstances.getKeystrokes().setDownColor(downColor);
    }
}