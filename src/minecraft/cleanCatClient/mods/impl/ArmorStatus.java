package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.clickgui.settings.impl.ArmorStatusSetting;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ArmorStatus extends ModDraggable {
    public ArmorStatus() {
        super(ModConstants.ARMOR_STATUS, ModConstants.ARMOR_STATUS_DESC, ModCategory.RENDER);}
    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public void render(ScreenPosition pos) {

        for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++){
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

    public void renderItemStack(ScreenPosition pos,int i, ItemStack is){
        if (is == null){
            return;
        }
        GL11.glPushMatrix();
        int yAdd = -16 * i + 48;

        if (is.getItem().isDamageable()){
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
        mc.fontRendererObj.drawStringWithShadow(count, pos.getAbsoluteX() + 16, pos.getAbsoluteY() + 64, 0xFFFFFF);
    }
}
