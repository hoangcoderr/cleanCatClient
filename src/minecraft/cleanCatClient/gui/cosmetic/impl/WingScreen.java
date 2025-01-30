package cleanCatClient.gui.cosmetic.impl;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cleanCatClient.cosmetic.CosmeticBoolean;
import cleanCatClient.cosmetic.Cosmetic;

public class WingScreen extends GuiScreen {
    private List<Cosmetic> wings;
    private List<GuiButton> buttons;

    public WingScreen() {
        wings = new ArrayList<>();
        buttons = new ArrayList<>();
        wings.add(new Cosmetic(3, 7, "Dragon Wing", false));
        wings.add(new Cosmetic(3, 8, "Satan Wing", false));
        wings.add(new Cosmetic(3, 9, "Dragon Baby Wing", false));
        wings.add(new Cosmetic(3, 12, "Dragon Obsidian Wing", false));
    }

    @Override
    public void initGui() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int padding = 10;
        int startX = (this.width - (buttonWidth * 3 + padding * 2)) / 2;
        int startY = (this.height - (buttonHeight * 5 + padding * 4)) / 2;

        for (int i = 0; i < wings.size(); i++) {
            int x = startX + (i % 3) * (buttonWidth + padding);
            int y = startY + (i / 3) * (buttonHeight + padding);
            ClientButton button = new ClientButton(i, x, y, buttonWidth, buttonHeight, wings.get(i).getName());
            this.buttonList.add(button);
            buttons.add(button);
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        Cosmetic wing = wings.get(button.id);
        System.out.println(wing.getName());
        CosmeticBoolean.set(wing.getType(), wing.getId(), !wing.isEnabled());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}