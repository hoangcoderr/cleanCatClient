package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ColoSaturationSettings extends ModSettings {
    private Slider saturationSlider;
    public ColoSaturationSettings() {
        super(ModInstances.getColorSaturation());
        saturationSlider = new Slider(Minecraft.centerX, Minecraft.centerY, 100, 10, 0, 2, 1, 0.1F);
    }

    @Override
    public void initGui() {
       super.initGui();
         saturationSlider.reloadPosition(Minecraft.centerX, Minecraft.centerY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.fontRendererObj.drawString("Color Saturation: ", Minecraft.centerX - 180, Minecraft.centerY - 30 , 0xFFFFFF);
        saturationSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        saturationSlider.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        saturationSlider.mouseReleased(mouseX, mouseY, state);
       // ModInstances.getColorSaturation().reloadShader();
        float newSaturationValue = saturationSlider.getCurrentValue();

        // Cập nhật giá trị saturation trong ColorSaturation
        ModInstances.getColorSaturation().setSaturation(newSaturationValue);
    }
}
