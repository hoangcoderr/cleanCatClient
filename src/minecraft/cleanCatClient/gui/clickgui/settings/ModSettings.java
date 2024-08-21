package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.gui.button.ClientButton;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.mods.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ModSettings extends GuiScreen {
    protected Mod mod;
    private ClientButton backButton;

    public ModSettings(Mod mod) {
        this.mod = mod;
    }

    @Override
    public void initGui() {
        super.initGui();
        // Initialize settings GUI components here

        // Calculate the center of the screen
        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;

        // Add the back button
        backButton = new ClientButton(0, centerW - 50, centerH + 135, 100, 20, "Back");
        this.buttonList.add(backButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Calculate the center of the screen
        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;

        // Draw the rounded rectangle
        Gui.drawRoundedRect(centerW - 200, centerH - 125, centerW + 200, centerH + 125, 8, new Color(77, 76, 76, 218).getRGB());

        // Draw the back button
        backButton.drawButton(mc, mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == 0) {
            // Return to ClickGui
            mc.displayGuiScreen(new ClickGui());
        }
    }

}