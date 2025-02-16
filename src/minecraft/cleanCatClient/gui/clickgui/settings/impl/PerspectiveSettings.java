

package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.KeyBindSetting;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class PerspectiveSettings extends ModSettings {
    private final CheckBox smooth;
    private final KeyBindSetting keyBindSetting;

    public PerspectiveSettings() {
        super(ModInstances.getPerspective());
        this.smooth = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 50, 200, 20, "Smooth", ModInstances.getPerspective().isSmooth());
        this.keyBindSetting = new KeyBindSetting(Minecraft.centerX - 100, Minecraft.centerY - 20, 200, 20, "Key Bind", ModInstances.getPerspective().getKeyBind());
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        keyBindSetting.keyTyped(typedChar, keyCode);
        ModInstances.getPerspective().setKeyBind(keyBindSetting.getKeyBind());

    }
    @Override
    public void initGui(){
        super.initGui();
        smooth.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 50);
        keyBindSetting.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        smooth.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        keyBindSetting.drawKeyBindSetting(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    private void updateSettings() {
        ModInstances.getPerspective().setSmooth(smooth.isChecked());
        ModInstances.getPerspective().setKeyBind(keyBindSetting.getKeyBind());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        smooth.mouseClicked(mouseX, mouseY, mouseButton);
        keyBindSetting.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }
}