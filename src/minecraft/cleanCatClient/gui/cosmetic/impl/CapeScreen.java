package cleanCatClient.gui.cosmetic.impl;

import cleanCatClient.cosmetic.Cosmetic;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cleanCatClient.cosmetic.CosmeticBoolean;

public class CapeScreen extends GuiScreen {
    private List<Cosmetic> capes;
    private List<GuiButton> buttons;

    public CapeScreen() {
        capes = new ArrayList<>();
        buttons = new ArrayList<>();
        capes.add(new Cosmetic(1, 1, "Snow Cape", false));
        capes.add(new Cosmetic(1, 2, "Normal Cape", false));
        capes.add(new Cosmetic(1, 3, "Thunder Cape", false));
        capes.add(new Cosmetic(1, 4, "Bad Wolf Cape", false));
        capes.add(new Cosmetic(1, 5, "Starry Sunset Cape", false));
        capes.add(new Cosmetic(1, 10, "Anime Girl Cape", false));
        capes.add(new Cosmetic(1, 11, "Jumping Frog Cape", false));
        capes.add(new Cosmetic(1, 14, "Waving Cape", false));
        capes.add(new Cosmetic(1, 15, "Chillin Boy", false));
    }

    @Override
    public void initGui() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int padding = 10;
        int startX = (this.width - (buttonWidth * 3 + padding * 2)) / 2;
        int startY = (this.height - (buttonHeight * 5 + padding * 4)) / 2;

        for (int i = 0; i < capes.size(); i++) {
            int x = startX + (i % 3) * (buttonWidth + padding);
            int y = startY + (i / 3) * (buttonHeight + padding);
            ClientButton button = new ClientButton(i, x, y, buttonWidth, buttonHeight, capes.get(i).getName());
            this.buttonList.add(button);
            buttons.add(button);
        }
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        Cosmetic cape = capes.get(button.id);
        System.out.println(cape.getName());
        CosmeticBoolean.set(cape.getType(), cape.getId(), !cape.isEnabled());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}