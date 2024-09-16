// src/minecraft/cleanCatClient/mods/impl/PlayerDistance.java
package cleanCatClient.mods.impl;

import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.Render2D;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.util.glu.GLU;
import net.minecraft.util.Vec3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class PlayerDistance extends ModDraggable {
    public PlayerDistance() {
        super("Player Distance Mod", "Displays the distance between you and other players", ModCategory.RENDER);
        setEnabled(true);
    }
    private String getRelativePosition(EntityPlayer mainPlayer, EntityPlayer otherPlayer) {
        double angle = Math.toDegrees(Math.atan2(otherPlayer.posZ - mainPlayer.posZ, otherPlayer.posX - mainPlayer.posX)) - mainPlayer.rotationYaw;
        angle = (angle + 360) % 360;

        if (angle >= 45 && angle < 135) {
            return "trước mặt";
        } else if (angle >= 135 && angle < 225) {
            return "bên phải";
        } else if (angle >= 225 && angle < 315) {
            return "sau lưng";
        } else {
            return "bên trái";
        }
    }
    @EventTarget
    public void drawLine(Render2D event){
        for (EntityLivingBase entitylivingbase : this.mc.theWorld.playerEntities) {
            if (entitylivingbase instanceof EntityPlayer) {
                if (entitylivingbase != mc.thePlayer)
                    PlayerDistance.drawTracerLine(entitylivingbase);
            }
        }
    }
    public void render(ScreenPosition pos) {
        Minecraft mc = Minecraft.getMinecraft();
        int yOffset = 0;
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer) {
                double distance = mc.thePlayer.getDistanceToEntity(player);
                String relativePosition = getRelativePosition(mc.thePlayer, player);
                String text = player.getDisplayName().getFormattedText() + ": " + String.format("%.1f", distance) + " blocks (" + relativePosition + ")";
                mc.fontRendererObj.drawStringWithShadow(text, pos.getAbsoluteX(), pos.getAbsoluteY() + yOffset, 0XFFFFFF);
                yOffset += mc.fontRendererObj.FONT_HEIGHT + 2; // Move to the next line
            }
        }
    }

    public static void drawTracerLine(EntityLivingBase entity) {
        double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks) - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks) - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks) - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        RenderUtils.drawTracerLine(xPos, yPos, zPos, 1, 0, 0, 1, 2);
    }

    public static void entityESPBox(Entity entity, int mode) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS); // Save all OpenGL attributes
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(4.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(0, 1, 0, 0.3);

        // Calculate bounding box coordinates
        double minX = entity.boundingBox.minX - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double minY = entity.boundingBox.minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double minZ = entity.boundingBox.minZ - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
        double maxX = entity.boundingBox.maxX - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double maxY = entity.boundingBox.maxY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double maxZ = entity.boundingBox.maxZ - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);

        // Draw bounding box
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));

        GL11.glPopMatrix();
        GL11.glPopAttrib(); // Restore all OpenGL attributes
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRendererObj.drawStringWithShadow("Player Distance Mod", pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFFFFFF);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 20;
    }
}