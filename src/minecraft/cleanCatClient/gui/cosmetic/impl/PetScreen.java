package cleanCatClient.gui.cosmetic.impl;

import cleanCatClient.cosmetic.Cosmetic;
import cleanCatClient.cosmetic.CosmeticBoolean;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetScreen extends GuiScreen {
    private final List<Cosmetic> pets;
    private final List<GuiButton> buttons;

    public PetScreen() {
        pets = new ArrayList<>();
        buttons = new ArrayList<>();
        Map<Integer, Cosmetic> allCosmetics = CosmeticBoolean.getAllCosmetics();
        for (Cosmetic cosmetic : allCosmetics.values()) {
            if (cosmetic.getType() == 4) {
                pets.add(cosmetic);
            }
        }
    }

    @Override
    public void initGui() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int padding = 10;
        int startX = (this.width - (buttonWidth * 3 + padding * 2)) / 2;
        int startY = (this.height - (buttonHeight * 5 + padding * 4)) / 2;

        for (int i = 0; i < pets.size(); i++) {
            int x = startX + (i % 3) * (buttonWidth + padding);
            int y = startY + (i / 3) * (buttonHeight + padding);
            ClientButton button = new ClientButton(i, x, y, buttonWidth, buttonHeight, pets.get(i).getName());
            this.buttonList.add(button);
            buttons.add(button);
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        Cosmetic pet = pets.get(button.id);
        System.out.println(pet.getName());
        CosmeticBoolean.set(pet.getType(), pet.getId(), !pet.isEnabled());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Pets", this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
