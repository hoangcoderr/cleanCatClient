package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.Slider;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ParticlesMultiplierSettings extends ModSettings {
    private Slider multiplierSlider;

    public ParticlesMultiplierSettings() {
        super(ModInstances.getParticlesMultiplier());
    }

    @Override
    public void initGui() {
        super.initGui();
        int centerX = width / 2;
        int centerY = height / 2;
        multiplierSlider = new Slider(centerX - 100, centerY - 10, 200, 20, 1.0f, 500.0f, ModInstances.getParticlesMultiplier().getMultiplier(), 0.1f);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        multiplierSlider.drawSlider(Minecraft.getMinecraft(), mouseX, mouseY);
        ModInstances.getParticlesMultiplier().setMultiplier(multiplierSlider.getCurrentValue());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        multiplierSlider.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        multiplierSlider.mouseReleased(mouseX, mouseY, state);
    }
}