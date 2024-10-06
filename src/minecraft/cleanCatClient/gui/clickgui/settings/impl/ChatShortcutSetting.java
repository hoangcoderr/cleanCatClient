package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.Client;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.ChatShortcuts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatShortcutSetting extends ModSettings {
    private List<GuiTextField> textFields;
    private ChatShortcuts chatShortcuts;
    private ClientButton saveButton;

    public ChatShortcutSetting() {
        super(ModInstances.getChatShortcuts());
        chatShortcuts = ModInstances.getChatShortcuts();
        initializeTextFields();
        initializeSaveButton();
    }

    private void initializeTextFields() {
        textFields = new ArrayList<>();
        int yPosition = 10; // Starting Y position for the text fields

        for (Map.Entry<String, String> entry : chatShortcuts.getShortcuts().entrySet()) {
            GuiTextField keyField = new GuiTextField(0, mc.fontRendererObj, 10, yPosition, 95, 20);
            keyField.setText(entry.getKey());
            textFields.add(keyField);

            GuiTextField valueField = new GuiTextField(1, mc.fontRendererObj, 115, yPosition, 95, 20);
            valueField.setText(entry.getValue());
            textFields.add(valueField);

            yPosition += 30; // Increment Y position for the next pair of text fields
        }
    }

    private void initializeSaveButton() {
        saveButton = new ClientButton(99, Minecraft.centerX, Minecraft.centerY, 200, 20, "Save");
        buttonList.add(saveButton);
    }

    @Override
    public void initGui() {
        super.initGui();
        for (GuiTextField textField : textFields) {
            textField.setFocused(false);
            textField.setCanLoseFocus(true);
            textField.setMaxStringLength(20);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (GuiTextField textField : textFields) {
            textField.drawTextBox();
        }
        saveButton.drawButton(mc, mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (GuiTextField textField : textFields) {
            textField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiTextField textField : textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (saveButton.mousePressed(mc, mouseX, mouseY)) {
            actionPerformed(saveButton); // Gọi hành động khi nút được nhấn
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == saveButton.id) {
            updateShortcuts();
        }
    }

    public void updateShortcuts() {
        Map<String, String> shortcuts = chatShortcuts.getShortcuts();
        shortcuts.clear();
        for (int i = 0; i < textFields.size(); i += 2) {
            String key = textFields.get(i).getText();
            String value = textFields.get(i + 1).getText();
            if (!key.isEmpty() && !value.isEmpty()) {
                shortcuts.put(key, value);
            }
        }
        System.out.println("Shortcuts updated!");
    }
}