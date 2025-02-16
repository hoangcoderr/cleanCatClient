package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ArmorStatus extends ModDraggable {
    public ArmorStatus() {
        super(ModConstants.ARMOR_STATUS, ModConstants.ARMOR_STATUS_DESC, ModCategory.RENDER);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void render(ScreenPosition pos) {

        for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
            renderItemStack(pos, i, itemStack);
        }
        renderHeldItem(pos);

    }


    @Override
    public void renderDummy(ScreenPosition pos) {
        renderItemStack(pos, 3, new ItemStack(Items.diamond_helmet));
        renderItemStack(pos, 2, new ItemStack(Items.diamond_chestplate));
        renderItemStack(pos, 1, new ItemStack(Items.diamond_leggings));
        renderItemStack(pos, 0, new ItemStack(Items.diamond_boots));
        renderHeldItem(pos);

    }

    public void renderItemStack(ScreenPosition pos, int i, ItemStack is) {
        if (is == null) {
            return;
        }
        GL11.glPushMatrix();
        int yAdd = -16 * i + 48;

        if (is.getItem().isDamageable()) {
            double damage = (is.getMaxDamage() - is.getItemDamage()) / (double) is.getMaxDamage() * 100;
            //font.drawString(String.format("%.2f", damage) + "%", pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, -1);
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(is, pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd);
        GL11.glPopMatrix();
    }

    private void renderHeldItem(ScreenPosition pos) {
        ItemStack heldItem = mc.thePlayer.getHeldItem();
        if (heldItem != null) {
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(heldItem, pos.getAbsoluteX(), pos.getAbsoluteY() + 64); // Position below the armor items
            renderHeldItemCount(pos, heldItem);

            GL11.glPopMatrix();


        }
    }

    private void renderHeldItemCount(ScreenPosition pos, ItemStack heldItem) {
        String count = String.valueOf(heldItem.stackSize);
        mc.fontRendererObj.drawStringWithShadow(count, pos.getAbsoluteX() + 16, pos.getAbsoluteY() + 64, 0xFFFFFF);if (heldItem.getItem() == Item.getItemFromBlock(Blocks.wool) && heldItem.stackSize >= 3 && heldItem.stackSize <= 10) {
            String warningText = "Warning: Out of wool!";
            GL11.glPushMatrix();
            GlStateManager.scale(2.0, 2.0, 2.0); // Scale the text to be larger
            mc.fontRendererObj.drawStringWithShadow(warningText, (Minecraft.centerX - 90) / 2, Minecraft.centerY / 2, Color.RED.getRGB());
            GL11.glPopMatrix();
        }
    }

}