package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class CustomNameSettings extends ModSettings {
    public CustomNameSettings() {
        super(ModInstances.getCustomName());
    }
    private GuiTextField customNameField;
    private ClientButton setNameButton;
    @Override
    public void initGui() {
        super.initGui();
        this.customNameField = new GuiTextField(0, mc.fontRendererObj, this.width / 2 - 100, this.height / 2 - 50, 200, 20);
        this.customNameField.setText(ModInstances.getCustomName().getCustomName());
        this.setNameButton = new ClientButton(1, this.width / 2 - 100, this.height / 2, "Set Custom Name");
        this.buttonList.add(this.setNameButton);
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        super.actionPerformed(button);
        if (button.id == 1) {
            String customName = this.customNameField.getText();
            ModInstances.getCustomName().setCustomName(customName);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.customNameField.drawTextBox();
        setNameButton.drawButton(mc, mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.customNameField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.customNameField.mouseClicked(mouseX, mouseY, mouseButton);
    }



}
