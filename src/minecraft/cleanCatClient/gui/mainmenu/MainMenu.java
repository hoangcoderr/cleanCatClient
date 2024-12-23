package cleanCatClient.gui.mainmenu;

import cleanCatClient.Client;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.gui.mainmenu.particles.SnowPartical;
import cleanCatClient.gui.mainmenu.updatechecker.UpdateChecker;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends GuiScreen {
    private SnowPartical particles = SnowPartical.create(160); // 160 is a decent amount
    private boolean showUpdateMenu = false;

    private ClientButton yesButton;
    private ClientButton noButton;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("cleanCatClient/MainMenu/main_menu.png"));
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        Gui.drawRect(0, 0, 300, height, new Color(0, 0, 0, 100).getRGB());
        GlStateManager.pushMatrix();
        GlStateManager.scale(3, 3, 1);
        int j = 14737632;

        this.drawCenteredString(mc.fontRendererObj, Client.CLIENT_NAME, 48, 20, j);
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
        particles.render();

        if (showUpdateMenu) {
            drawDefaultBackground();
            drawCenteredString(mc.fontRendererObj, "New version released!", width / 2, height / 2 - 30, Color.WHITE.getRGB());
            drawCenteredString(mc.fontRendererObj, "<<<<===Changelogs===>>>>", width / 2, height / 2 - 10, Color.WHITE.getRGB());

            int yOffset = height / 2 + 10;
            for (String update : UpdateChecker.updateInfos) {
                drawCenteredString(mc.fontRendererObj, update, width / 2, yOffset, Color.WHITE.getRGB());
                yOffset += 10; // Adjust the spacing between lines as needed
            }

            yesButton.drawButton(mc, mouseX, mouseY);
            noButton.drawButton(mc, mouseX, mouseY);
        }
    }
    @Override
    public void initGui() {
        int yOff = height / 2 + 10 + 10 * UpdateChecker.updateInfos.size();
        this.buttonList.add(new ClientButton(1, 25, height / 2 - 20, 250, 30, "Singleplayer"));
        this.buttonList.add(new ClientButton(2, 25, height / 2 + 15, 250, 30, "Multiplayer"));
        this.buttonList.add(new ClientButton(3, 25, height / 2 + 50, 250, 30, "Settings"));
        this.buttonList.add(new ClientButton(4, 25, height / 2 + 85, 250, 30, "Bye cleanCat"));
        this.buttonList.add(new ClientButton(5, 25, height / 2 + 120, 250, 30, "Alt Manager"));

        if (!UpdateChecker.isLastestVersion()) {
            showUpdateMenu = true;
            System.out.println("New version available, showing update menu.");
            yesButton = new ClientButton(6, width / 2 - 50, yOff, 40, 20, "Yes");
            noButton = new ClientButton(7, width / 2 + 10, yOff, 40, 20, "No");
            this.buttonList.add(yesButton);
            this.buttonList.add(noButton);
        } else {
            System.out.println("No new version available.");
        }

        super.initGui();
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 4:
                mc.shutdown();
                break;
            case 5:
                mc.displayGuiScreen(new cleanCatClient.gui.auth.AltManagerGui());
                break;
            case 6:
                // Logic to download the update
                new Thread(() -> {
                    try {
                        UpdateChecker.downloadUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case 7:
                showUpdateMenu = false;
                buttonList.remove(yesButton);
                buttonList.remove(noButton);
                break;
        }
        super.actionPerformed(button);
    }
}