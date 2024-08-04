package net.optifine.gui;

import java.io.IOException;
import java.util.List;

import cleanCatClient.gui.button.ClientButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF extends GuiScreen
{
    protected void actionPerformedRightClick(ClientButton button) throws IOException
    {
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1)
        {
            ClientButton guibutton = getSelectedButton(mouseX, mouseY, this.buttonList);

            if (guibutton != null && guibutton.enabled)
            {
                guibutton.playPressSound(this.mc.getSoundHandler());
                this.actionPerformedRightClick(guibutton);
            }
        }
    }

    public static ClientButton getSelectedButton(int x, int y, List<ClientButton> listButtons)
    {
        for (int i = 0; i < listButtons.size(); ++i)
        {
            ClientButton guibutton = (ClientButton)listButtons.get(i);

            if (guibutton.visible)
            {
                int j = GuiVideoSettings.getButtonWidth(guibutton);
                int k = GuiVideoSettings.getButtonHeight(guibutton);

                if (x >= guibutton.xPosition && y >= guibutton.yPosition && x < guibutton.xPosition + j && y < guibutton.yPosition + k)
                {
                    return guibutton;
                }
            }
        }

        return null;
    }
}
