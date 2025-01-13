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

public class KeyStrokeSetting extends ModSettings {
    private ComboBox customComboBox;
    private Keystrokes keystrokes;
    private ColorPicker upColorPicker;
    private ColorPicker downColorPicker;

    public KeyStrokeSetting() {
        super(ModInstances.getKeystrokes());
        this.keystrokes = ModInstances.getKeystrokes();
        this.customComboBox = new ComboBox(Minecraft.centerX - 50, Minecraft.centerY - 50, 100, 20, Arrays.asList("WASD", "WASD_MOUSE", "WASD_JUMP", "WASD_JUMP_MOUSE"));
        this.upColorPicker = new ColorPicker(Minecraft.centerX - 50, Minecraft.centerY + 30, 100, 20);
        this.downColorPicker = new ColorPicker(Minecraft.centerX - 50, Minecraft.centerY + 60, 100, 20);
    }

    @Override
    public void initGui() {
        super.initGui();
        customComboBox.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY - 50);
        upColorPicker.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY + 30);
        downColorPicker.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY + 60);
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
        updateColors();
    }

    private void updateKeystrokesMode() {
        String selectedItem = customComboBox.getSelectedItem();
        switch (selectedItem) {
            case "WASD":
                keystrokes.setMode(Keystrokes.KeystrokesMode.WASD);
                break;
            case "WASD_MOUSE":
                keystrokes.setMode(Keystrokes.KeystrokesMode.WASD_MOUSE);
                break;
            case "WASD_JUMP":
                keystrokes.setMode(Keystrokes.KeystrokesMode.WASD_JUMP);
                break;
            case "WASD_JUMP_MOUSE":
                keystrokes.setMode(Keystrokes.KeystrokesMode.WASD_JUMP_MOUSE);
                break;
        }
    }

    private void updateColors() {
        keystrokes.setUpColor(new Color(upColorPicker.getColor()));
        keystrokes.setDownColor(new Color(downColorPicker.getColor()));
    }
}