package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.Client;
import cleanCatClient.gui.button.ClientButton;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

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
        Gui.drawRoundedRect(rectX, rectY + 30, centerW + backgroundW / 2, centerH + backgroundH / 2, 8, new Color(255, 255, 255, 195).getRGB());

        backButton.drawButton(mc, mouseX, mouseY);
        exitButton.drawButton(mc, mouseX, mouseY);
        //todo: update exit button by creating a new button class
        int modNameX = rectX + 10;
        int modNameY = rectY + 10;
        int modNameColor = new Color(255, 255, 255, 255).getRGB();
        float scale = 2.0f;

        GL11.glPushMatrix();
        GL11.glTranslatef(modNameX, modNameY, 0);
        GL11.glScalef(scale, scale, 1.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f((modNameColor >> 16 & 255) / 255.0f, (modNameColor >> 8 & 255) / 255.0f, (modNameColor & 255) / 255.0f, (modNameColor >> 24 & 255) / 255.0f);

        FontUtil.normal.drawString(mod.name, 0, 0, modNameColor);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }


    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(Client.INSTANCE.clickGui);
        } else if (button.id == 1) {
            mc.displayGuiScreen(null); // Close the GUI
        }
    }

}