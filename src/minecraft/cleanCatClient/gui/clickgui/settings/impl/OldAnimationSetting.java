package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.oldanimations.OldAnimation;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class OldAnimationSetting extends ModSettings {
    // Add the CheckBox fields
    private CheckBox leftHandCheckBox;
    private CheckBox smoothSneakingCheckBox;
    private CheckBox swingAnimationCheckBox;

    public OldAnimationSetting() {
        super(ModInstances.getOldAnimation());

        // Initialize the CheckBoxes
        this.leftHandCheckBox = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 60, 150, 20, ModInstances.getLeftHand().name, ModInstances.getLeftHand().isEnabled());
        this.smoothSneakingCheckBox = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 35, 150, 20, ModInstances.getSmoothSneaking().name, ModInstances.getSmoothSneaking().isEnabled());
        this.swingAnimationCheckBox = new CheckBox(Minecraft.centerX - 150, Minecraft.centerY - 10, 150, 20, ModInstances.getSwingAnimation().name, ModInstances.getSwingAnimation().isEnabled());
    }

    @Override
    public void initGui() {
        super.initGui();

        // Reload positions for CheckBoxes
        leftHandCheckBox.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 60);
        smoothSneakingCheckBox.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 35);
        swingAnimationCheckBox.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 10);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Draw labels
      //  FontUtil.normal.drawString("LeftHand:", Minecraft.centerX - 200, Minecraft.centerY - 60, 0xFFFFFF);
       // FontUtil.normal.drawString("SmoothSneaking:", Minecraft.centerX - 200, Minecraft.centerY - 35, 0xFFFFFF);
//FontUtil.normal.drawString("SwingAnimation:", Minecraft.centerX - 200, Minecraft.centerY - 10, 0xFFFFFF);

        // Draw CheckBoxes
        leftHandCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        smoothSneakingCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        swingAnimationCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    private void updateSettings() {
        ModInstances.getLeftHand().setEnabled(leftHandCheckBox.isChecked());
        ModInstances.getSmoothSneaking().setEnabled(smoothSneakingCheckBox.isChecked());
        ModInstances.getSwingAnimation().setEnabled(swingAnimationCheckBox.isChecked());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        leftHandCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        smoothSneakingCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        swingAnimationCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }

}
