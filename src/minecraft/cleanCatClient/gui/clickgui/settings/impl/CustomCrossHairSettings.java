package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class CustomCrossHairSettings extends ModSettings {
    private CheckBox customCrosshairCheckBox;
    public static ColorPicker colorPicker;

    public CustomCrossHairSettings() {
        super(ModInstances.getCustomCrosshair());
    }

    @Override
    public void initGui() {
        super.initGui();
        // Initialize settings GUI components here
        int centerW = width / 2;
        int centerH = height / 2;
        colorPicker = new ColorPicker(centerW - 75, centerH + 20, 150, 100);
        customCrosshairCheckBox = new CheckBox(centerW - 75, centerH - 10, 150, 20, "Enable Custom Crosshair", false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        // Draw settings GUI components here
        colorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
        customCrosshairCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        customCrosshairCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        colorPicker.mouseReleased(mouseX, mouseY, state);
    }

}