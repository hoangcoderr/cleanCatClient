package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.Client;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.notification.Notification;
import cleanCatClient.gui.notification.NotificationManager;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModSettings extends GuiScreen {
    protected Mod mod;
    private ClientButton backButton;
    private ClientButton exitButton;
    private int backgroundW;
    private int backgroundH;

    public ModSettings(Mod mod) {
        this.mod = mod;
    }

    @Override
    public void initGui() {
        super.initGui();
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;

        backgroundW = Client.INSTANCE.clickGui.getWidth();
        backgroundH = Client.INSTANCE.clickGui.getHeight();

        backButton = new ClientButton(0, centerW - 50, centerH + backgroundH / 2 + 10, 100, 20, "Back");
        this.buttonList.add(backButton);

        // Add Exit button
        int exitButtonX = centerW - backgroundW / 2 + backgroundW - 60;
        int exitButtonY = centerH - backgroundH / 2 + 10;
         exitButton = new ClientButton(1, exitButtonX, exitButtonY, 50, 20, "Exit");
        this.buttonList.add(exitButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;

        int rectX = centerW - backgroundW / 2;
        int rectY = centerH - backgroundH / 2;
        Gui.drawRoundedRect(rectX, rectY, centerW + backgroundW / 2, centerH + backgroundH / 2, 8, new Color(77, 76, 76, 218).getRGB());
        Gui.drawRoundedRect(rectX, rectY + 30, centerW + backgroundW / 2, centerH + backgroundH / 2, 8, new Color(55, 52, 52, 58).getRGB());

        backButton.drawButton(mc, mouseX, mouseY);
        exitButton.drawButton(mc, mouseX, mouseY);
        //todo: update exit button by creating a new button class
        int modNameX = rectX + 10;
        int modNameY = rectY + 10;
        int modNameColor = new Color(255, 255, 255, 255).getRGB();
        FontUtil.getFontRenderer(30).drawString(mod.name, modNameX, modNameY, modNameColor);
        //FontUtil.normal.drawCustomSizeString(mod.name, modNameX, modNameY, modNameColor, 30);
    }


    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(Client.INSTANCE.clickGui);
        } else if (button.id == 1) {
            mc.displayGuiScreen(null); // Close the GUI
        }
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        super.onGuiClosed();


    }

}