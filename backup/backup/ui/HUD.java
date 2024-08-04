package cleanCatClient.ui;

import cleanCatClient.ClientConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class HUD
{
    public Minecraft mc = Minecraft.getMinecraft();
    public void draw() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fontRenderer = mc.fontRendererObj;
        GlStateManager.translate(4,4,0);
        GlStateManager.scale(2,2,1);
        GlStateManager.translate(-4,-4,0);
        fontRenderer.drawString(ClientConstants.CLIENT_NAME, 4, 4, 0xffffff);
        GlStateManager.translate(4,4,0);
        GlStateManager.scale(0.5,0.5,1);
        GlStateManager.translate(-4,-4,0);
    }
}
