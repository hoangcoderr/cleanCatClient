package cleanCatClient.gui.cosmetic;

import cleanCatClient.gui.cosmetic.impl.CapeScreen;
import cleanCatClient.gui.cosmetic.impl.HatScreen;
import cleanCatClient.gui.cosmetic.impl.PetScreen;
import cleanCatClient.gui.cosmetic.impl.WingScreen;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.stats.StatFileWriter;

import java.io.IOException;

public class CosmeticMainScreen extends GuiScreen {

    private boolean isDragging = false;
    private float lastMouseX;
    private float lastMouseY;
    private float rotationYaw = 180.0F;
    private final float rotationPitch = 0.0F;

    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(0, this.width / 2 - 100, this.height / 2 - 75, 200, 20, "Hats"));
        this.buttonList.add(new ClientButton(1, this.width / 2 - 100, this.height / 2 - 50, 200, 20, "Wings"));
        this.buttonList.add(new ClientButton(2, this.width / 2 - 100, this.height / 2 - 25, 200, 20, "Capes"));
        this.buttonList.add(new ClientButton(4, this.width / 2 - 100, this.height / 2, 200, 20, "Pets"));
        this.buttonList.add(new ClientButton(3, this.width / 2 - 100, this.height / 2 + 25, 200, 20, "Back"));
    }

    @Override
    protected void actionPerformed(ClientButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new HatScreen());
                break;
            case 1:
                this.mc.displayGuiScreen(new WingScreen());
                break;
            case 2:
                this.mc.displayGuiScreen(new CapeScreen());
                break;
            case 4:
                this.mc.displayGuiScreen(new PetScreen());
                break;
            case 3:
                //  this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            this.drawDefaultBackground();
            EntityPlayerSP player = mc.thePlayer;
            if (mc != null && player == null) {
                if (mc.theWorld != null && mc.getNetHandler() != null) {
                    player = new EntityPlayerSP(mc, mc.theWorld, mc.getNetHandler(), new StatFileWriter());
                } else {
                    System.err.println("World or NetHandler is null");
                    return;
                }
            }
            if (player != null) {
                renderPlayerModel(this.width - 100, this.height / 2 + 50, 100, mouseX, mouseY, player);
            }
            super.drawScreen(mouseX, mouseY, partialTicks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            isDragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0) {
            isDragging = false;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (isDragging) {
            float deltaX = mouseX - lastMouseX;
            rotationYaw += deltaX * 0.5F;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    public void renderPlayerModel(int posX, int posY, int scale, float mouseX, float mouseY, EntityPlayerSP player) {
        try {
            GlStateManager.enableColorMaterial();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) posX, (float) posY, 50.0F);
            GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            float f = player.renderYawOffset;
            float f1 = player.rotationYaw;
            float f2 = player.rotationPitch;
            float f3 = player.prevRotationYawHead;
            float f4 = player.rotationYawHead;
            GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
            player.renderYawOffset = rotationYaw;
            player.rotationYaw = rotationYaw;
            player.rotationPitch = 0.0F; // Keep the head from moving up and down
            player.rotationYawHead = player.rotationYaw;    
            player.prevRotationYawHead = player.rotationYaw;
            GlStateManager.translate(0.0F, 0.0F, 0.0F);
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            RenderPlayer renderPlayer = new RenderPlayer(rendermanager);
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            renderPlayer.doRender(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
            rendermanager.setRenderShadow(true);
            player.renderYawOffset = f;
            player.rotationYaw = f1;
            player.rotationPitch = f2;
            player.prevRotationYawHead = f3;
            player.rotationYawHead = f4;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}