package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.Client;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.mods.impl.ChatShortcuts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatShortcutSetting extends ModSettings {
    private List<GuiTextField> textFields;
    private ChatShortcuts chatShortcuts;
    private ClientButton saveButton;
    private int scrollOffset;
    private int maxScroll;
    private ClientButton addButton;

    public ChatShortcutSetting() {
        super(ModInstances.getChatShortcuts());
        chatShortcuts = ModInstances.getChatShortcuts();
        initializeTextFields();
        initializeSaveButton();
        initializeAddButton();
        scrollOffset = 0;
        maxScroll = Math.max(0, textFields.size() / 2 * 30 - height + 100); // Calculate max scroll based on text fields
    }
    private ClientButton deleteButton;

    private void initializeDeleteButton() {
        deleteButton = new ClientButton(101, Minecraft.centerX, Minecraft.centerY + 60, 200, 20, "Delete Shortcut");
        buttonList.add(deleteButton);
    }
    private void initializeTextFields() {
        textFields = new ArrayList<>();
        int yPosition = 30; // Starting Y position for the text fields

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

    private void addNewTextFields() {
        int yPosition = 30 + (textFields.size() / 2) * 30; // Calculate Y position for new text fields

        GuiTextField keyField = new GuiTextField(0, mc.fontRendererObj, 10, yPosition, 95, 20);
        textFields.add(keyField);

        GuiTextField valueField = new GuiTextField(1, mc.fontRendererObj, 115, yPosition, 95, 20);// Set the maximum string length
        textFields.add(valueField);

        maxScroll = Math.max(0, textFields.size() / 2 * 30 - height + 100); // Recalculate max scroll
    }
    private void initializeAddButton() {
        addButton = new ClientButton(100, Minecraft.centerX, Minecraft.centerY + 30, 200, 20, "Add Shortcut");
        buttonList.add(addButton);
    }
    private void initializeSaveButton() {
        saveButton = new ClientButton(99, Minecraft.centerX, Minecraft.centerY, 200, 20, "Save");
        buttonList.add(saveButton);
    }
    private int backgroundW;
    private int backgroundH;
    @Override
    public void initGui() {
        super.initGui();
        initializeAddButton();
        initializeDeleteButton();
        for (GuiTextField textField : textFields) {
            textField.setFocused(false);
            textField.setCanLoseFocus(true);
            textField.setMaxStringLength(50);
        }
        backgroundW = Client.INSTANCE.clickGui.getWidth();
        backgroundH = Client.INSTANCE.clickGui.getHeight();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int mouseWheel = Mouse.getDWheel();
        if (mouseWheel != 0) {
            scrollOffset -= Integer.signum(mouseWheel) * 10;
            scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));
        }

        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;
        int rectX = centerW - backgroundW / 2;
        int rectY = centerH - backgroundH / 2;

        // Enable scissor test
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(rectX * sr.getScaleFactor(), (sr.getScaledHeight() - (rectY + backgroundH)) * sr.getScaleFactor(), backgroundW * sr.getScaleFactor(), backgroundH * sr.getScaleFactor());

        // Draw the text fields
        for (int i = 0; i < textFields.size(); i += 2) {
            GuiTextField keyField = textFields.get(i);
            GuiTextField valueField = textFields.get(i + 1);

            keyField.xPosition = rectX + 10; // Adjust X position for key field
            keyField.yPosition = rectY + 10 + (i / 2) * 30 - scrollOffset; // Adjust Y position for key field
            keyField.drawTextBox();

            valueField.xPosition = rectX + 115; // Adjust X position for value field
            valueField.yPosition = rectY + 10 + (i / 2) * 30 - scrollOffset; // Adjust Y position for value field
            valueField.drawTextBox();
        }

        // Disable scissor test
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        // Draw the save button
        saveButton.xPosition = rectX + (backgroundW - saveButton.width) / 2; // Center the button horizontally
        saveButton.yPosition = rectY + backgroundH - saveButton.height - 10; // Position the button at the bottom of the rectangle
        saveButton.drawButton(mc, mouseX, mouseY);

        // Draw the add button
        addButton.xPosition = rectX + (backgroundW - addButton.width) / 2; // Center the button horizontally
        addButton.yPosition = rectY + backgroundH - addButton.height - 40; // Position the button above the save button
        addButton.drawButton(mc, mouseX, mouseY);

        // Draw the delete button
        deleteButton.xPosition = rectX + (backgroundW - deleteButton.width) / 2; // Center the button horizontally
        deleteButton.yPosition = rectY + backgroundH - deleteButton.height - 70; // Position the button above the add button
        deleteButton.drawButton(mc, mouseX, mouseY);
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
            actionPerformed(saveButton); // Call action when button is pressed
        }
    }
    private void deleteLastTextFields() {
        if (textFields.size() >= 2) {
            textFields.remove(textFields.size() - 1);
            textFields.remove(textFields.size() - 1);
            maxScroll = Math.max(0, textFields.size() / 2 * 30 - height + 100); // Recalculate max scroll
        }
    }


    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == saveButton.id) {
            updateShortcuts();
        } else if (button.id == addButton.id) {
            addNewTextFields();
        } else if (button.id == deleteButton.id) {
            deleteLastTextFields();
        }
        super.actionPerformed(button);
    }

    public void updateShortcuts() {
        Map<String, String> newShortcuts = new HashMap<>();
        for (int i = 0; i < textFields.size(); i += 2) {
            String key = textFields.get(i).getText();
            String value = textFields.get(i + 1).getText();
            if (!key.isEmpty() && !value.isEmpty()) {
                newShortcuts.put(key, value);
            }
        }
        chatShortcuts.setShortcuts(newShortcuts);
        System.out.println("Shortcuts updated!");
    }
}