package cleanCatClient.gui.auth;

import cleanCatClient.gui.mainmenu.MainMenu;
import cleanCatClient.gui.auth.microsoft.GuiLoginMicrosoft;
import cleanCatClient.gui.auth.mojang.GuiLoginMojang;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class AltManagerGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2, 2, 1);
        drawCenteredString(this.fontRendererObj, "Playing with: " +  mc.session.getUsername(), width / 4, 20, -1);
        GlStateManager.popMatrix();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Cancel"));
        this.buttonList.add(new ClientButton(1, width / 2 + 4 + 50, height - 48, 100, 20, "Use Cracked"));
        this.buttonList.add(new ClientButton(2, width / 2 - 50, height - 48, 100, 20, "Use Microsoft"));
        this.buttonList.add(new ClientButton(3, width / 2 - 150 - 4, height - 48, 100, 20, "Use Mojang"));
        this.buttonList.add(new ClientButton(4, width / 2 - 50, height - 24, 100, 20, "Coming Soon..."));
        this.buttonList.add(new ClientButton(5, width / 2 - 150 - 4, height - 24, 100, 20, "Coming Soon..."));
        super.initGui();
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(new MainMenu());
        }
        else  if(button.id == 1){
            mc.displayGuiScreen(new GuiLogin());
        }
        else if(button.id == 2){
            mc.displayGuiScreen(new GuiLoginMicrosoft());
        }
        else if(button.id == 3){
            mc.displayGuiScreen(new GuiLoginMojang());
        }
    }
}
