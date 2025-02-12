package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ToggleSprintSettings extends ModSettings {
    private CheckBox showTextCheckBox;
    private ColorPicker colorPicker;
    public ToggleSprintSettings() {
        super(ModInstances.getToggleSprint());
        showTextCheckBox = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 40, 150, 20, "Show Text", ModInstances.getToggleSprint().isShowText());
        colorPicker = new ColorPicker(Minecraft.centerX - 100, Minecraft.centerY , 160, 100);
        colorPicker.setColor(ModInstances.getToggleSprint().getColor());
    }

    @Override
    public void initGui() {
        super.initGui();
        showTextCheckBox.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY -40);
        colorPicker.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        showTextCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        colorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        showTextCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        ModInstances.getToggleSprint().setShowText(showTextCheckBox.isChecked());
        colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        colorPicker.mouseReleased(mouseX, mouseY, state);
        ModInstances.getToggleSprint().setColor(colorPicker.getColor());
    }


}
