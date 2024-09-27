package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ItemPhysicsSetting extends ModSettings {
    private CheckBox cbSpin;

    public ItemPhysicsSetting() {
        super(ModInstances.getItemPhysics());
        cbSpin = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 60, 150, 20, "Spin", ModInstances.getItemPhysics().isSpin());
    }

    @Override
    public void initGui() {
        super.initGui();
        cbSpin.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 60);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        cbSpin.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        cbSpin.mouseClicked(mouseX, mouseY, mouseButton);
        ModInstances.getItemPhysics().setSpin(cbSpin.isChecked());
    }

}
