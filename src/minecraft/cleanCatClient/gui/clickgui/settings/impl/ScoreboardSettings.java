package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ScoreboardSettings extends ModSettings {
    private CheckBox hideRedNumbers;
    private CheckBox hideRect;
    private Slider slider;
    public ScoreboardSettings() {
        super(ModInstances.getScoreboard());
        hideRedNumbers = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 45, 150, 20, "Hide Red Numbers", ModInstances.getScoreboard().isHideRedNumbers());
        hideRect = new CheckBox(Minecraft.centerX - 25, Minecraft.centerY - 5, 150, 20, "Hide Rect", ModInstances.getScoreboard().isHideRect());
    }

    @Override
    public void initGui() {
        super.initGui();
        hideRedNumbers.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 45);
        hideRect.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 5);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        hideRedNumbers.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        hideRect.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    private void updateSettings() {
        if (hideRedNumbers.isChecked()) {
            ModInstances.getScoreboard().setHideRedNumbers(true);
        } else {
            ModInstances.getScoreboard().setHideRedNumbers(false);
        }

        if (hideRect.isChecked()) {
            ModInstances.getScoreboard().setHideRect(true);
        } else {
            ModInstances.getScoreboard().setHideRect(false);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        hideRedNumbers.mouseClicked(mouseX, mouseY, mouseButton);
        hideRect.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }


}
