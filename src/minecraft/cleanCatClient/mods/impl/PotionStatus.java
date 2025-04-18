package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

public class PotionStatus extends ModDraggable {


    public PotionStatus() {
        super(ModConstants.POSION_STATUS, ModConstants.POSION_STATUS_DESC, ModCategory.RENDER);
    }

    @Override
    public int getWidth() {
        return 64;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }


    private final int lastColor = 0;
    private final long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL = 1000; // Update every 1000 milliseconds (1 second)



    @Override
    public void render(ScreenPosition pos) {
        Collection<PotionEffect> effects = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();
        int y = pos.getAbsoluteY();
        int effectCount = effects.size();
        int totalHeight = effectCount * 20; // Each potion effect takes 20 pixels height

        for (PotionEffect effect : effects) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            int color = RenderUtils.getRandomColor();

            String name = I18n.format(potion.getName());

            if (effect.getAmplifier() == 1) {
                name = name + " " + I18n.format("enchantment.level.2");
            } else if (effect.getAmplifier() == 2) {
                name = name + " " + I18n.format("enchantment.level.3");
            } else if (effect.getAmplifier() == 3) {
                name = name + " " + I18n.format("enchantment.level.4");
            }
            String durationString = Potion.getDurationString(effect);
            GL11.glPushMatrix();
            int i1 = potion.getStatusIconIndex();
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainer.inventoryBackground);
            Gui.drawModalRectWithCustomSizedTexture(pos.getAbsoluteX() - 20, y, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18, 256, 256);
            //FontUtil.normal.drawStringWithShadow(name, pos.getAbsoluteX(), y, color);
            //FontUtil.normal.drawStringWithShadow(durationString, pos.getAbsoluteX(), y + 10, color);
            mc.fontRendererObj.drawStringWithShadow(name, pos.getAbsoluteX(), y, color);
            mc.fontRendererObj.drawStringWithShadow(durationString, pos.getAbsoluteX(), y + 10, color);
            y += 20;
            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        int y = pos.getAbsoluteY();
        for (int i = 1; i < 4; i++) {
            GL11.glPushMatrix();
            int i1 = i;
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainer.inventoryBackground);
            Gui.drawModalRectWithCustomSizedTexture(pos.getAbsoluteX() - 20, y, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18, 256, 256);
            FontUtil.normal.drawStringWithShadow("Potion Name ", pos.getAbsoluteX(), y, 0xFFFFFF);
            FontUtil.normal.drawStringWithShadow("99:99", pos.getAbsoluteX(), y + 10, 0xFFFFFF);
            y += 20;
            GL11.glPopMatrix();
        }
    }
}