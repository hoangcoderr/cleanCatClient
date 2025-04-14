package cleanCatClient.utils;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderUtils {

    public static void drawEntityBox(net.minecraft.entity.Entity entity, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glLineWidth(1.0F);
        GL11.glColor4f(red, green, blue, alpha);

        // Get entity's bounding box
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosZ;

        // Draw the box
        drawBoundingBox(new net.minecraft.util.AxisAlignedBB(
                x - entity.width / 2, y, z - entity.width / 2,
                x + entity.width / 2, y + entity.height, z + entity.width / 2));

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private static void drawBoundingBox(net.minecraft.util.AxisAlignedBB boundingBox) {
        // Draw bottom face
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glEnd();

        // Draw top face
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glEnd();

        // Draw lines connecting all vertices
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glEnd();
    }
    public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private static long lastUpdateTime = System.currentTimeMillis();
    private static int lastColor = 0;
    private static int targetColor = 0;
    
    private static final long UPDATE_INTERVAL = 120; // Khoảng thời gian cập nhật màu mới
    private static final float LERP_FACTOR = 0.01f; // Giá trị nhỏ để chuyển màu mượt hơn

    public static int getRandomColor() {
        long currentTime = System.currentTimeMillis();

        // Sinh màu đích mới nếu đã qua khoảng thời gian UPDATE_INTERVAL
        if (currentTime - lastUpdateTime > UPDATE_INTERVAL) {
            Random random = new Random();
            targetColor = random.nextInt(0xFFFFFF + 1);
            lastUpdateTime = currentTime;
        }

        // Tách thành phần màu từ lastColor và targetColor
        int r1 = (lastColor >> 16) & 0xFF;
        int g1 = (lastColor >> 8) & 0xFF;
        int b1 = lastColor & 0xFF;

        int r2 = (targetColor >> 16) & 0xFF;
        int g2 = (targetColor >> 8) & 0xFF;
        int b2 = targetColor & 0xFF;

        int r = (int) (r1 + (r2 - r1) * LERP_FACTOR);
        int g = (int) (g1 + (g2 - g1) * LERP_FACTOR);
        int b = (int) (b1 + (b2 - b1) * LERP_FACTOR);

        lastColor = (r << 16) | (g << 8) | b;
        return lastColor;
    }   

}
