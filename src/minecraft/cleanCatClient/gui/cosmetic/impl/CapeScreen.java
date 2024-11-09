package cleanCatClient.gui.cosmetic.impl;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cleanCatClient.cosmetic.CosmeticBoolean;

public class CapeScreen extends GuiScreen {
    private List<String> capeNames;
    private List<GuiButton> buttons;

    public CapeScreen() {
        capeNames = new ArrayList<>();
        buttons = new ArrayList<>();
        capeNames.add("Snow Cape");
        capeNames.add("Nornal Cape");
        capeNames.add("Thuder Cape");
        capeNames.add("Bad Wolf Cape");
        capeNames.add("Starry Sunset Cape");
        capeNames.add("Anime Girl Cape");
        capeNames.add("Jumping Frog Cape");
        capeNames.add("Waving Cape");
    }

    @Override
    public void initGui() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int padding = 10;
        int startX = (this.width - (buttonWidth * 3 + padding * 2)) / 2;
        int startY = (this.height - (buttonHeight * 5 + padding * 4)) / 2;

        for (int i = 0; i < capeNames.size(); i++) {
            int x = startX + (i % 3) * (buttonWidth + padding);
            int y = startY + (i / 3) * (buttonHeight + padding);
            ClientButton button = new ClientButton(i, x, y, buttonWidth, buttonHeight, capeNames.get(i));
            this.buttonList.add(button);
            buttons.add(button);
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        String capeName = capeNames.get(button.id);
        // Gọi hàm CosmeticBoolean.set<ten cape>
        System.out.println(capeName);
        CosmeticBoolean.set(capeName);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}