package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.components.colorpicker.ColorPicker;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class HitColorSetting extends ModSettings {
    private ColorPicker colorPicker;
    public HitColorSetting() {
        super(ModInstances.getHitColor());
        this.colorPicker = new ColorPicker(Minecraft.centerX, Minecraft.centerY, 150, 100);
        this.colorPicker.setColor(ModInstances.getHitColor().getSaveColor());
    }

    @Override
    public void initGui() {
        super.initGui();
        this.colorPicker.reloadPosition(Minecraft.centerX - 100, Minecraft.centerY - 60);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.colorPicker.drawPicker(mc, mouseX, mouseY);
        FontUtil.normal.drawStringWithShadow("Hit Color", Minecraft.centerX - 200, Minecraft.centerY - 60, -1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.colorPicker.mouseReleased(mouseX, mouseY, state);
        ModInstances.getHitColor().setColor(this.colorPicker.getColor());
    }


}
