package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class CustomCrossHairSettings extends ModSettings {
    //private CheckBox dotCheckBox;
    private Slider gapSlider;
    private Slider thicknessSlider;
    private Slider lengthSlider;
    private ColorPicker colorPicker;

    public CustomCrossHairSettings() {
        super(ModInstances.getCustomCrosshair());
        //this.dotCheckBox = new CheckBox(Minecraft.centerX - 100, Minecraft.centerY - 45, 150, 20, "Dot", ModInstances.getCustomCrosshair().isDot());
        this.gapSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY - 20, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getGap(), 0.1f);
        this.thicknessSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY + 5, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getThickness(), 0.1f);
        this.lengthSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY + 30, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getLength(), 0.1f);
        this.colorPicker = new ColorPicker(Minecraft.centerX - 100, Minecraft.centerY + 55, 160, 100);
        colorPicker.setColor(ModInstances.getCustomCrosshair().getColor());
    }

    @Override
    public void initGui() {
        super.initGui();
        //dotCheckBox.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 45);
        gapSlider.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 20);
        thicknessSlider.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY + 5);
        lengthSlider.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY + 30);
        colorPicker.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY + 55);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        //dotCheckBox.drawCheckBox();
        gapSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        thicknessSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        lengthSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        colorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    private void updateSettings() {
        ModInstances.getCustomCrosshair().setGap((int) gapSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setThickness((int) thicknessSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setLength((int) lengthSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setColor(colorPicker.getColor());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        //dotCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        gapSlider.mouseClicked(mouseX, mouseY, mouseButton);
        thicknessSlider.mouseClicked(mouseX, mouseY, mouseButton);
        lengthSlider.mouseClicked(mouseX, mouseY, mouseButton);
        colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        updateSettings();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        //dotCheckBox.mouseReleased(mouseX, mouseY, state);
        gapSlider.mouseReleased(mouseX, mouseY, state);
        thicknessSlider.mouseReleased(mouseX, mouseY, state);
        lengthSlider.mouseReleased(mouseX, mouseY, state);
        colorPicker.mouseReleased(mouseX, mouseY, state);

    }

}