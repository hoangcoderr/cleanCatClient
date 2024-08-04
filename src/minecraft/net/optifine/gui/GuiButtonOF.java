package net.optifine.gui;

import cleanCatClient.gui.button.ClientButton;

public class GuiButtonOF extends ClientButton
{
    public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public GuiButtonOF(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, buttonText);
    }
}
