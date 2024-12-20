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

public class MainMenu extends GuiScreen
{
    private SnowPartical particles = SnowPartical.create(160); // 160 is a decent amount

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        mc.getTextureManager().bindTexture(new ResourceLocation("cleanCatClient/MainMenu/main_menu.png"));
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        Gui.drawRect(0, 0, 300,height, new Color(0, 0, 0, 100).getRGB());
        GlStateManager.pushMatrix();
        GlStateManager.scale(3,3,1);
        int j = 14737632;

        this.drawCenteredString(mc.fontRendererObj, Client.CLIENT_NAME, 48, 20, j);
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
        //this.drawDefaultBackground();
        particles.render();
    }

    @Override
    public void initGui()
    {
        UpdateChecker.checkForUpdates();


        this.buttonList.add(new ClientButton(1, 25, height / 2 - 20, 250, 30, "Singleplayer"));
        this.buttonList.add(new ClientButton(2, 25, height / 2 + 15,250, 30,  "Multiplayer"));
        this.buttonList.add(new ClientButton(3, 25, height / 2 + 50,250, 30,  "Settings"));
        this.buttonList.add(new ClientButton(4, 25, height / 2 + 85, 250, 30, "Bye cleanCat"));
        this.buttonList.add(new ClientButton(5, 25, height / 2 + 120, 250, 30, "Alt Manager"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException
    {
        switch (button.id)
        {
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
        }
        super.actionPerformed(button);
    }
}
