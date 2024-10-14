package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class PerspectiveSetting extends ModSettings {
    private CheckBox smooth;
    public PerspectiveSetting() {
        super(ModInstances.getPerspective());
        this.smooth = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 50, 200, 20, "Smooth", ModInstances.getPerspective().isSmooth());
    }

    @Override
    public void initGui(){
        super.initGui();
        smooth.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 50);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        smooth.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    private void updateSettings() {
        if (smooth.isChecked()) {
            ModInstances.getPerspective().setSmooth(true);
        } else {
            ModInstances.getPerspective().setSmooth(false);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        smooth.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }
}
