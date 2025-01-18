package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.Sys;

public class FullBright extends Mod {
    public FullBright() {
        super(ModConstants.FULL_BRIGHT, ModConstants.FULL_BRIGHT_DESC, ModCategory.SETTINGS);
    }
    private float oldBrightness = 1000;


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            mc.gameSettings.gammaSetting = 1000;
            mc.gameSettings.saveOptions();

            this.oldBrightness = mc.gameSettings.saturation;
        } else {
            mc.gameSettings.saturation = this.oldBrightness;


        }
    }
}