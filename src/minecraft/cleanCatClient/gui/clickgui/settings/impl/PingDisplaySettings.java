package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.CheckBox;
import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;

public class PingDisplaySettings extends ModSettings {
    private final ColorPicker colorPicker;
    private int colorPickerX;
    private int colorPickerY;
    public PingDisplaySettings() {
        super(ModInstances.getPingDisplay());
        colorPickerX = Minecraft.centerX - 20;
        colorPickerY = Minecraft.centerY - 50;
        this.colorPicker = new ColorPicker(colorPickerX, colorPickerY, 150, 100);
        this.colorPicker.setColor(ModInstances.getCps().getColor().getRGB());
        //this.slider = new Slider(Minecraft.centerX - 75, Minecraft.centerY + 20, 150, 20, 0, 100, 50);

    }

    @Override
    public void initGui() {
        super.initGui();
        colorPickerX = Minecraft.centerX - 90;
        colorPickerY = Minecraft.centerY - 35;
        colorPicker.reloadPosition(colorPickerX, colorPickerY);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.fontRendererObj.drawString("Ping Color: ", Minecraft.centerX - 180, Minecraft.centerY - 30 , 0xFFFFFF);
        colorPicker.drawPicker(Minecraft.getMinecraft(), mouseX, mouseY);
        //slider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);

    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        //slider.mouseClicked(mouseX, mouseY, mouseButton);

    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        colorPicker.mouseReleased(mouseX, mouseY, state);
        ModInstances.getPingDisplay().setColor(new Color(colorPicker.getColor()));
        //slider.mouseReleased(mouseX, mouseY, state);

    }
}
