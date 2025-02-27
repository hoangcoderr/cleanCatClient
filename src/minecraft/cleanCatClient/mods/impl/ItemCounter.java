package cleanCatClient.mods.impl;

import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemCounter extends ModDraggable {
    private List<ItemStack> itemsToCount;

    public ItemCounter() {
        super(ModConstants.ITEM_COUNTER, ModConstants.ITEM_COUNTER_DESC, ModCategory.RENDER);
        itemsToCount = new ArrayList<>();
        loadConfig();
    }

    public void initItemsToCount() {
        //iron ingot
        ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot);
        addItemToCount(ironIngot);

        //diamond
        ItemStack diamond = new ItemStack(net.minecraft.init.Items.diamond);
        addItemToCount(diamond);

        //gold ingot
        ItemStack goldIngot = new ItemStack(net.minecraft.init.Items.gold_ingot);
        addItemToCount(goldIngot);
    }


    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            itemsToCount.clear();
            for (String itemName : dataConfig) {
                net.minecraft.item.Item item = net.minecraft.item.Item.getByNameOrId(itemName);
                ItemStack itemStack = new ItemStack(item);
                if (itemStack != null) {
                    addItemToCount(itemStack);
                }
            }
        } catch (Exception e) {
            ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot);
            addItemToCount(ironIngot);

            //diamond
            ItemStack diamond = new ItemStack(net.minecraft.init.Items.diamond);
            addItemToCount(diamond);

            //gold ingot
            ItemStack goldIngot = new ItemStack(net.minecraft.init.Items.gold_ingot);
            addItemToCount(goldIngot);
            e.printStackTrace();
        }
    }
    @Override
    public void saveConfig() {
        List<String> dataConfig = new ArrayList<>();
        for (ItemStack item : itemsToCount) {
            dataConfig.add(net.minecraft.item.Item.getIdFromItem(item.getItem()) + "");
        }
        saveDataConfig(dataConfig.toArray(new String[0]));
    }

    public void addItemToCount(ItemStack item) {
        itemsToCount.add(item);
        saveConfig();
    }

    public List<ItemStack> getItemsToCount() {
        return itemsToCount;
    }

    public void setItemsToCount(List<ItemStack> itemsToCount) {
        this.itemsToCount = itemsToCount;
        saveConfig();
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 20;
    }


    @Override
    public void render(ScreenPosition pos) {
        Minecraft mc = Minecraft.getMinecraft();
        int x = pos.getAbsoluteX();
        int y = pos.getAbsoluteY();

        for (ItemStack itemToCount : itemsToCount) {
            int totalCount = 0;

            // Count the total number of the current item in the player's inventory
            for (ItemStack itemStack : mc.thePlayer.inventory.mainInventory) {
                if (itemStack != null && itemStack.getItem() == itemToCount.getItem()) {
                    totalCount += itemStack.stackSize;
                }
            }

            // Render the item icon
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemToCount, x, y);

            // Render the item count below the icon
            mc.fontRendererObj.drawStringWithShadow(String.valueOf(totalCount), x, y + 20, 0xFFFFFF);

            // Move to the next position
            x += 20;
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        super.renderDummy(pos);
    }
}
