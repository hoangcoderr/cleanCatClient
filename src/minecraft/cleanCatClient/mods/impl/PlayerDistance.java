// src/minecraft/cleanCatClient/mods/impl/PlayerDistance.java
package cleanCatClient.mods.impl;

import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderGlobal;

public class PlayerDistance extends ModDraggable {
    public PlayerDistance() {
        super("Player Distance Mod", "Displays the distance between you and other players", ModCategory.RENDER);
        setEnabled(true);
    }

    @Override
    public void render(ScreenPosition pos) {
        Minecraft mc = Minecraft.getMinecraft();
        int yOffset = 0;
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer) {
                double distance = mc.thePlayer.getDistanceToEntity(player);
                String text = player.getName() + ": " + String.format("%.1f", distance) + " blocks";
                mc.fontRendererObj.drawStringWithShadow(text, pos.getAbsoluteX(), pos.getAbsoluteY() + yOffset, 0XFFFFFF);
                yOffset += mc.fontRendererObj.FONT_HEIGHT + 2; // Move to the next line
            }
        }
    }

    public static void drawLineToPlayer(int centerX, int centerY, EntityPlayer player) {

//        Minecraft mc = Minecraft.getMinecraft();
//        double playerX = player.posX - mc.getRenderManager().renderPosX;
//        double playerY = player.posY - mc.getRenderManager().renderPosY;
//        double playerZ = player.posZ - mc.getRenderManager().renderPosZ;
//
//        GL11.glPushMatrix();
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glLineWidth(4.0F);
//        GL11.glBegin(GL11.GL_LINES);
//        GL11.glColor4f(2.0F, 0.0F, 0.0F, 0.5F); // Red color with 50% transparency
//        GL11.glVertex2d(centerX, centerY);
//        GL11.glVertex3d(playerX, playerY, playerZ);
//        GL11.glEnd();
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glPopMatrix();
    }
    public static void entityESPBox(Entity entity, int mode) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(4.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Set color based on mode
        if (mode == 0) { // Enemy
            GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
                    Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
                    0, 0.5F);
        } else if (mode == 1) { // Friend
            GL11.glColor4d(0, 1, 0, 0.5F);

        } else if (mode == 2) { // Other
            GL11.glColor4d(0, 1, 0, 0.5F);

        } else if (mode == 3) { // Target
            GL11.glColor4d(0, 1, 0, 0.5F);

        } else if (mode == 4) { // Team
            GL11.glColor4d(0, 1, 0, 0.5F);
        }

        // Calculate bounding box coordinates
        double minX = entity.boundingBox.minX - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double minY = entity.boundingBox.minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double minZ = entity.boundingBox.minZ - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
        double maxX = entity.boundingBox.maxX - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double maxY = entity.boundingBox.maxY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double maxZ = entity.boundingBox.maxZ - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);

        // Draw bounding box
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
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