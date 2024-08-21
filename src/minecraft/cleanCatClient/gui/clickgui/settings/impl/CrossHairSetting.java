package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class CrossHairSetting extends ModSettings {
    public CrossHairSetting() {
        super(ModInstances.getCustomCrosshair());
    }

    @Override
    public void initGui() {
        super.initGui();
        // Initialize settings GUI components here
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        // Draw settings GUI components here
    }
}
