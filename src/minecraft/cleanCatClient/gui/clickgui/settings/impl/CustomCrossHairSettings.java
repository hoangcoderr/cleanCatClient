package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class CustomCrossHairSettings extends ModSettings {
    //private CheckBox dotCheckBox;
    private final Slider gapSlider;
    private final Slider thicknessSlider;
    private final Slider lengthSlider;
    private final ColorPicker colorPicker;
    private final CheckBox dotCheckBox;
    private final CheckBox plusCheckBox;
    private final CheckBox arrowCheckBox;

    public CustomCrossHairSettings() {
        super(ModInstances.getCustomCrosshair());
        this.gapSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY - 20, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getGap(), 0.1f);
        this.lengthSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY + 5, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getLength(), 0.1f);
        this.thicknessSlider = new Slider(Minecraft.centerX - 100, Minecraft.centerY + 30, 150, 20, 0, 10, ModInstances.getCustomCrosshair().getThickness(), 0.1f);

        this.colorPicker = new ColorPicker(Minecraft.centerX - 100, Minecraft.centerY + 55, 160, 100);
        colorPicker.setColor(ModInstances.getCustomCrosshair().getColor());

        // Initialize the CheckBoxes
        this.dotCheckBox = new CheckBox(Minecraft.centerX + 60, Minecraft.centerY + 5, 150, 20, "Dot", ModInstances.getCustomCrosshair().isDot());
        this.plusCheckBox = new CheckBox(Minecraft.centerX + 60, Minecraft.centerY + 30, 150, 20, "Plus", ModInstances.getCustomCrosshair().isPlus());
        this.arrowCheckBox = new CheckBox(Minecraft.centerX + 60, Minecraft.centerY + 55, 150, 20, "Arrow", ModInstances.getCustomCrosshair().isArrow());
    }

    @Override
    public void initGui() {
        super.initGui();
        //dotCheckBox.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 45);
        gapSlider.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 64);
        lengthSlider.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 40);
        thicknessSlider.reloadPosition(Minecraft.centerX - 150, Minecraft.centerY - 15);
        colorPicker.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY + 10);
        // Reload positions for CheckBoxes
        dotCheckBox.reloadPosition(Minecraft.centerX + 60, Minecraft.centerY - 40);
        plusCheckBox.reloadPosition(Minecraft.centerX + 60, Minecraft.centerY - 15);
        arrowCheckBox.reloadPosition(Minecraft.centerX + 60, Minecraft.centerY + 10);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        // Draw labels
        FontUtil.normal.drawString("Gap:", Minecraft.centerX - 200, Minecraft.centerY - 60, 0xFFFFFF);
        FontUtil.normal.drawString("Length:", Minecraft.centerX - 200, Minecraft.centerY - 35, 0xFFFFFF);
        FontUtil.normal.drawString("Thickness:", Minecraft.centerX - 200, Minecraft.centerY - 10, 0xFFFFFF);
        FontUtil.normal.drawString("Color:", Minecraft.centerX - 200, Minecraft.centerY + 15, 0xFFFFFF);
        // Draw sliders and color picker
        gapSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        thicknessSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        lengthSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        colorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
        // Draw CheckBoxes
        dotCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        plusCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);
        arrowCheckBox.drawCheckBox(Minecraft.getMinecraft(), mouseX, mouseY);

        ModInstances.getCustomCrosshair().drawCrosshair(Minecraft.centerX, Minecraft.centerY + 100,(int)gapSlider.getCurrentValue(), (int)lengthSlider.getCurrentValue(), (int)thicknessSlider.getCurrentValue());
    }

    private void updateSettings() {
        ModInstances.getCustomCrosshair().setGap((int) gapSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setThickness((int) thicknessSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setLength((int) lengthSlider.getCurrentValue());
        ModInstances.getCustomCrosshair().setColor(colorPicker.getColor());
        // Update CheckBox settings
        ModInstances.getCustomCrosshair().setDot(dotCheckBox.isChecked());
        ModInstances.getCustomCrosshair().setPlus(plusCheckBox.isChecked());
        ModInstances.getCustomCrosshair().setArrow(arrowCheckBox.isChecked());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        //dotCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        gapSlider.mouseClicked(mouseX, mouseY, mouseButton);
        thicknessSlider.mouseClicked(mouseX, mouseY, mouseButton);
        lengthSlider.mouseClicked(mouseX, mouseY, mouseButton);
        colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        // Handle CheckBox clicks
        dotCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        plusCheckBox.mouseClicked(mouseX, mouseY, mouseButton);
        arrowCheckBox.mouseClicked(mouseX, mouseY, mouseButton);

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
        updateSettings();
    }

}