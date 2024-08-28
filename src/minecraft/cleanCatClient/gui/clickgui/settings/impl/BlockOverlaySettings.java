package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.IOException;

public class BlockOverlaySettings extends ModSettings {
    private CheckBox checkBoxIsOutline;
    private CheckBox checkBoxIsInline;
    private ColorPicker outlineColorPicker;
    private ColorPicker inlineColorPicker;
    private Slider slider;
    public BlockOverlaySettings() {
        super(ModInstances.getBlockOverlay());
        this.checkBoxIsOutline = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 45, 150, 20, "Enable Outline", ModInstances.getBlockOverlay().isOutline());
        this.checkBoxIsInline = new CheckBox(Minecraft.centerX - 25, Minecraft.centerY - 5, 150, 20, "Enable Fill", ModInstances.getBlockOverlay().isFill());
        this.outlineColorPicker = new ColorPicker(Minecraft.centerX - 45, Minecraft.centerY - 45, 160, 100);
        this.outlineColorPicker.setColor(ModInstances.getBlockOverlay().getColorOutline());
        this.inlineColorPicker = new ColorPicker(Minecraft.centerX - 25, Minecraft.centerY + 40, 160, 100);
        this.inlineColorPicker.setColor(ModInstances.getBlockOverlay().getColorInline());
        this.slider = new Slider(Minecraft.centerX - 75, Minecraft.centerY + 20, 150, 20, 1, 10, ModInstances.getBlockOverlay().getLineWidth(), 0.1f);
    }

    @Override
    public void initGui() {
        super.initGui();
        checkBoxIsOutline.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 45);
        checkBoxIsInline.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 20);
        outlineColorPicker.reloadPosition(Minecraft.centerX + 150, Minecraft.centerY - 45);
        inlineColorPicker.reloadPosition(Minecraft.centerX + 150, Minecraft.centerY - 20);
        slider.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY + 20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        checkBoxIsOutline.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        checkBoxIsInline.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        outlineColorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
        inlineColorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
        slider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        FontUtil.normal.drawString("Outline Width", Minecraft.centerX - 100, Minecraft.centerY + 10,0xFFFFFFFF);
    }

    private void updateSettings() {
        if (checkBoxIsOutline.isChecked()) {
            ModInstances.getBlockOverlay().setOutline(true);
        } else {
            ModInstances.getBlockOverlay().setOutline(false);
        }

        if (checkBoxIsInline.isChecked()) {
            ModInstances.getBlockOverlay().setFill(true);
        } else {
            ModInstances.getBlockOverlay().setFill(false);
        }

    }

    private void updateSettingsAtRealeased() {
        ModInstances.getBlockOverlay().setCustomColorOutline(outlineColorPicker.getColor());
        ModInstances.getBlockOverlay().setCustomColorInline(inlineColorPicker.getColor());
        ModInstances.getBlockOverlay().setLineWidth(slider.getCurrentValue());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        checkBoxIsOutline.mouseClicked(mouseX, mouseY, mouseButton);
        checkBoxIsInline.mouseClicked(mouseX, mouseY, mouseButton);
        outlineColorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        inlineColorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        slider.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        outlineColorPicker.mouseReleased(mouseX, mouseY, state);
        inlineColorPicker.mouseReleased(mouseX, mouseY, state);
        slider.mouseReleased(mouseX, mouseY, state);
        updateSettingsAtRealeased();
    }
}
