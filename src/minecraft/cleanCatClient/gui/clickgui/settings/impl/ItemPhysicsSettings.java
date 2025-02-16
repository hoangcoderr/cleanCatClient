package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ItemPhysicsSettings extends ModSettings {
    private final CheckBox cbSpin;
    private final CheckBox cbRandomAngle;
    public ItemPhysicsSettings() {
        super(ModInstances.getItemPhysics());
        cbSpin = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 60, 150, 20, "Spin", ModInstances.getItemPhysics().isSpin());
        cbRandomAngle = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 35, 150, 20, "Random Angle", ModInstances.getItemPhysics().isRandomAngle());
    }

    @Override
    public void initGui() {
        super.initGui();
        cbSpin.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 60);
        cbRandomAngle.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 35);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        cbSpin.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        cbRandomAngle.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        cbSpin.mouseClicked(mouseX, mouseY, mouseButton);
        cbRandomAngle.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }

    private void updateSettings() {
        if (cbSpin.isChecked()) {
            ModInstances.getItemPhysics().setSpin(true);
            cbRandomAngle.setChecked(false);
        } else {
            ModInstances.getItemPhysics().setSpin(false);
        }
        if (cbRandomAngle.isChecked()) {
            ModInstances.getItemPhysics().setRandomAngle(true);
            cbSpin.setChecked(false);
        } else {
            ModInstances.getItemPhysics().setRandomAngle(false);
        }
    }

}
