package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.ComboBox;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.Keystrokes;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.Arrays;

public class KeyStrokeSetting extends ModSettings {
    private ComboBox customComboBox;
    private Keystrokes keystrokes;

    public KeyStrokeSetting() {
        super(ModInstances.getKeystrokes());
        this.keystrokes = ModInstances.getKeystrokes();
        this.customComboBox = new ComboBox(Minecraft.centerX - 50, Minecraft.centerY - 50, 100, 20, Arrays.asList("WASD", "WASD_MOUSE", "WASD_JUMP", "WASD_JUMP_MOUSE"));
    }

    @Override
    public void initGui() {
        super.initGui();
        customComboBox.reloadPosition(Minecraft.centerX - 50, Minecraft.centerY - 50);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        customComboBox.drawComboBox(mc, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        customComboBox.mouseClicked(mouseX, mouseY, mouseButton);
        updateKeystrokesMode();
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
}