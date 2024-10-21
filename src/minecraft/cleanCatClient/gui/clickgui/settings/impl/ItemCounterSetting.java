package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.ItemCounter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

public class ItemCounterSetting extends ModSettings {
    public ItemCounterSetting() {
        super(ModInstances.getItemCounter());
    }
    private GuiTextField itemTextField;
    private ClientButton addButton;
    private ClientButton saveButton;
    private ClientButton okButton;
    private List<ItemStack> itemsToCount;

    @Override
    public void initGui() {
        super.initGui();
        int id = 0;
        int y = 20;

        // Initialize text field for item input
        itemTextField = new GuiTextField(id++, fontRendererObj, width / 2 - 100, y, 200, 20);
        y += 30;

        // Initialize buttons
        addButton = new ClientButton(id++, width / 2 - 100, y, 98, 20, "Thêm");
        okButton = new ClientButton(id++, width / 2 + 2, y, 98, 20, "OK");
        y += 30;
        saveButton = new ClientButton(id++, width / 2 - 100, y, 200, 20, "Lưu");
        itemsToCount = ModInstances.getItemCounter().getItemsToCount();
        buttonList.add(addButton);
        buttonList.add(okButton);
        buttonList.add(saveButton);
    }



    private ItemStack getItemByName(String itemName) {
        ResourceLocation itemResource = new ResourceLocation(itemName);
        net.minecraft.item.Item item = net.minecraft.item.Item.getByNameOrId(itemResource.toString());
        if (item == null) {
            System.out.println("Item not found: " + itemName); // Ghi nhật ký lỗi
            return null;
        }
        return new ItemStack(item);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        itemTextField.drawTextBox();
        int y = height / 2 - 50;
        int centerX = width / 2;

        for (int i = 0; i < itemsToCount.size(); i++) {
            ItemStack item = itemsToCount.get(i);

            // Render the item icon
            mc.getRenderItem().renderItemAndEffectIntoGUI(item, centerX - 110, y);

            // Calculate the X position to center the text
            int itemNameWidth = fontRendererObj.getStringWidth(item.getDisplayName());
            int itemX = centerX - itemNameWidth / 2;
            fontRendererObj.drawString(item.getDisplayName(), itemX, y + 5, 0xFFFFFF);

            // Add delete button
            ClientButton deleteButton = new ClientButton(100 + i, centerX + 110, y, 20, 20, "X");
            buttonList.add(deleteButton);

            y += 30;
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        if (button == addButton) {
            itemTextField.setVisible(true);
            okButton.visible = true;
        } else if (button == okButton) {
            String itemName = itemTextField.getText();
            ItemStack newItem = getItemByName(itemName);
            if (newItem != null) {
                ModInstances.getItemCounter().addItemToCount(newItem);
                itemsToCount = ModInstances.getItemCounter().getItemsToCount();
            }
            itemTextField.setVisible(false);
            okButton.visible = false;
        } else if (button == saveButton) {
            // Save logic here
        } else if (button.id >= 100) {
            // Handle delete button
            int index = button.id - 100;
            if (index < itemsToCount.size()) {
                itemsToCount.remove(index);
                ModInstances.getItemCounter().setItemsToCount(itemsToCount);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        itemTextField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        itemTextField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
