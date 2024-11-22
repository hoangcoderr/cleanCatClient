// src/minecraft/cleanCatClient/mods/impl/PlayerDistance.java
package cleanCatClient.mods.impl;

import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.event.impl.KeyEvent;
import cleanCatClient.event.impl.Render2D;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.utils.RenderUtils;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.Vec3;


public class PlayerDistance extends ModDraggable {
    private int vboId;
    private int vaoId;
    public PlayerDistance() {
        super("Player Distance Mod", "Displays the distance between you and other players", ModCategory.RENDER);
        setEnabled(true);

    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
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
    public void drawLine(Render2D event) {
        if (!ModInstances.getToggleSprint().isShowText())
            for (EntityLivingBase entitylivingbase : this.mc.theWorld.playerEntities) {
                if (entitylivingbase instanceof EntityPlayer) {
                    if (entitylivingbase != mc.thePlayer) {
                        //PlayerDistance.drawTracerLine(entitylivingbase);
                        PlayerDistance.entityESPBox(entitylivingbase);

                    }

                }
            }
        //renderBedESP();
    }

    private void checkForFireballs() {
        Minecraft mc = Minecraft.getMinecraft();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityFireball) {
                double distance = mc.thePlayer.getDistanceToEntity(entity);
                if (distance < 80.0) { // Set the warning distance to 10 units
                    displayWarning();
                    break;
                }
            }
        }
    }

    private void displayWarning() {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GlStateManager.scale(3.0, 3.0, 3.0); // Scale the text to be 3 times larger
        mc.fontRendererObj.drawStringWithShadow("Warning: Fireball Incoming!", (Minecraft.centerX - 90)/ 3, (Minecraft.centerY + 50) / 3, 0xFF0000);
        GL11.glPopMatrix();
    }

    public void render(ScreenPosition pos) {
        if (!ModInstances.getToggleSprint().isShowText()) {
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
            checkForFireballs();
        }


    }

    public static void blockESPBox(BlockPos blockPos) {
        Minecraft mc = Minecraft.getMinecraft();
        double x = blockPos.getX() - mc.getRenderManager().renderPosX;
        double y = blockPos.getY() - mc.getRenderManager().renderPosY;
        double z = blockPos.getZ() - mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Set color and draw bounding box
        GL11.glColor4d(0, 0, 1, 0.5F);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.5625, z + 1.0));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }





    public static void entityESPBox(Entity entity) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS); // Save all OpenGL attributes
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(0.1F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(0, 1, 0, 0.2);

        // Interpolated position
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;

        // Calculate bounding box coordinates
        double minX = entity.boundingBox.minX - entity.posX + (x - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double minY = entity.boundingBox.minY - entity.posY + (y - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double minZ = entity.boundingBox.minZ - entity.posZ + (z - Minecraft.getMinecraft().getRenderManager().renderPosZ);
        double maxX = entity.boundingBox.maxX - entity.posX + (x - Minecraft.getMinecraft().getRenderManager().renderPosX);
        double maxY = entity.boundingBox.maxY - entity.posY + (y - Minecraft.getMinecraft().getRenderManager().renderPosY);
        double maxZ = entity.boundingBox.maxZ - entity.posZ + (z - Minecraft.getMinecraft().getRenderManager().renderPosZ);

        // Draw bounding box
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));

        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRendererObj.drawStringWithShadow("", pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFFFFFF);
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