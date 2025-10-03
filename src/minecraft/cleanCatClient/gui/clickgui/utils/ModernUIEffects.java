package cleanCatClient.gui.clickgui.utils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Modern UI Effects Library
 * Provides advanced visual effects for a modern, Lunar Client-style GUI
 */
public class ModernUIEffects {
    
    /**
     * Draw a smooth glow effect around a rectangular area
     */
    public static void drawGlowEffect(int x1, int y1, int x2, int y2, int radius, Color color) {
        for (int i = 0; i < 4; i++) {
            int offset = i * 2;
            int alpha = color.getAlpha() / (i + 1);
            Gui.drawRoundedRect(x1 - offset, y1 - offset, x2 + offset, y2 + offset, 
                radius + offset, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB());
        }
    }
    
    /**
     * Draw a gradient-filled rounded rectangle
     */
    public static void drawGradientRoundedRect(int x1, int y1, int x2, int y2, int radius, int topColor, int bottomColor) {
        int midY = y1 + (y2 - y1) / 2;
        Gui.drawRoundedRect(x1, y1, x2, midY, radius, topColor);
        Gui.drawRoundedRect(x1, midY, x2, y2, radius, bottomColor);
    }
    
    /**
     * Draw a rounded border outline
     */
    public static void drawRoundedBorder(int x1, int y1, int x2, int y2, int radius, float width, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(width);
        
        // Draw border lines
        Gui.drawRect(x1, y1, x2, y1 + 1, color.getRGB());
        Gui.drawRect(x1, y2 - 1, x2, y2, color.getRGB());
        Gui.drawRect(x1, y1, x1 + 1, y2, color.getRGB());
        Gui.drawRect(x2 - 1, y1, x2, y2, color.getRGB());
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
    
    /**
     * Draw a pulsing glow effect (animated)
     */
    public static void drawPulsingGlow(int x, int y, int width, int height, int radius, Color baseColor, float pulseSpeed) {
        float pulse = (float)(Math.sin(System.currentTimeMillis() / (1000.0 / pulseSpeed)) * 0.4 + 0.6);
        int alpha = (int)(pulse * baseColor.getAlpha());
        Color pulseColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
        
        drawGlowEffect(x, y, x + width, y + height, radius, pulseColor);
    }
    
    /**
     * Draw a modern card with glass morphism effect
     */
    public static void drawGlassMorphismCard(int x, int y, int width, int height, int radius, Color bgColor, Color glowColor) {
        // Outer glow
        drawGlowEffect(x - 2, y - 2, x + width + 2, y + height + 2, radius + 2, glowColor);
        
        // Main background
        Gui.drawRoundedRect(x, y, x + width, y + height, radius, bgColor.getRGB());
        
        // Gradient overlay for depth
        Color gradientTop = new Color(
            Math.min(255, bgColor.getRed() + 20),
            Math.min(255, bgColor.getGreen() + 20),
            Math.min(255, bgColor.getBlue() + 20),
            bgColor.getAlpha() / 2
        );
        Color gradientBottom = new Color(
            Math.max(0, bgColor.getRed() - 20),
            Math.max(0, bgColor.getGreen() - 20),
            Math.max(0, bgColor.getBlue() - 20),
            bgColor.getAlpha() / 3
        );
        
        drawGradientRoundedRect(x, y, x + width, y + height, radius, 
            gradientTop.getRGB(), gradientBottom.getRGB());
        
        // Border highlight
        Color borderColor = new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 100);
        drawRoundedBorder(x, y, x + width, y + height, radius, 1.0f, borderColor);
    }
    
    /**
     * Draw a modern toggle switch
     */
    public static void drawModernToggle(int x, int y, int width, int height, boolean enabled, float animation, boolean isHovering) {
        // Background track
        Color trackColor = enabled ? 
            new Color(100, 200, 255, 200) : 
            new Color(50, 50, 70, 200);
        Gui.drawRoundedRect(x, y, x + width, y + height, height / 2, trackColor.getRGB());
        
        // Animated thumb
        int thumbSize = height - 4;
        int thumbX = x + 2;
        int maxThumbX = x + width - thumbSize - 2;
        int currentThumbX = (int)(thumbX + (maxThumbX - thumbX) * animation);
        int thumbY = y + 2;
        
        // Thumb glow
        if (enabled) {
            drawGlowEffect(currentThumbX - 2, thumbY - 2, currentThumbX + thumbSize + 2, thumbY + thumbSize + 2, 
                thumbSize / 2, new Color(100, 200, 255, 80));
        }
        
        // Thumb
        Color thumbColor = new Color(255, 255, 255, 255);
        Gui.drawRect(currentThumbX, thumbY, currentThumbX + thumbSize, thumbY + thumbSize, thumbColor.getRGB());
        
        // Border
        Color borderColor = isHovering ? 
            new Color(120, 180, 255, 200) : 
            new Color(80, 80, 120, 150);
        drawRoundedBorder(x, y, x + width, y + height, height / 2, 1.0f, borderColor);
    }
    
    /**
     * Draw a vertical gradient bar (for progress, selection, etc.)
     */
    public static void drawVerticalGradientBar(int x, int y, int width, int height, Color topColor, Color bottomColor) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        
        worldrenderer.pos(x + width, y, 0.0D)
            .color(topColor.getRed() / 255.0f, topColor.getGreen() / 255.0f, topColor.getBlue() / 255.0f, topColor.getAlpha() / 255.0f)
            .endVertex();
        worldrenderer.pos(x, y, 0.0D)
            .color(topColor.getRed() / 255.0f, topColor.getGreen() / 255.0f, topColor.getBlue() / 255.0f, topColor.getAlpha() / 255.0f)
            .endVertex();
        worldrenderer.pos(x, y + height, 0.0D)
            .color(bottomColor.getRed() / 255.0f, bottomColor.getGreen() / 255.0f, bottomColor.getBlue() / 255.0f, bottomColor.getAlpha() / 255.0f)
            .endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D)
            .color(bottomColor.getRed() / 255.0f, bottomColor.getGreen() / 255.0f, bottomColor.getBlue() / 255.0f, bottomColor.getAlpha() / 255.0f)
            .endVertex();
        
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    /**
     * Draw a shimmer effect (loading/animation)
     */
    public static void drawShimmerEffect(int x, int y, int width, int height, Color color) {
        float time = System.currentTimeMillis() % 2000 / 2000.0f;
        int shimmerX = (int)(x + (width * time));
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Create shimmer gradient
        for (int i = 0; i < width / 3; i++) {
            float alpha = (float)Math.sin((float)i / width * Math.PI) * color.getAlpha();
            Color shimmerColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)alpha);
            
            if (shimmerX + i >= x && shimmerX + i <= x + width) {
                Gui.drawRect(shimmerX + i, y, shimmerX + i + 1, y + height, shimmerColor.getRGB());
            }
        }
        
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * Draw a pulsing dot indicator
     */
    public static void drawPulsingDot(int centerX, int centerY, int radius, Color color) {
        float pulse = (float)(Math.sin(System.currentTimeMillis() / 300.0) * 0.3 + 0.7);
        int outerRadius = (int)(radius * 1.5);
        
        // Outer pulse
        int outerAlpha = (int)(pulse * color.getAlpha() * 0.3);
        Gui.drawRect(centerX - outerRadius, centerY - outerRadius, 
            centerX + outerRadius, centerY + outerRadius, 
            new Color(color.getRed(), color.getGreen(), color.getBlue(), outerAlpha).getRGB());
        
        // Inner dot
        int innerAlpha = (int)(pulse * color.getAlpha());
        Gui.drawRect(centerX - radius, centerY - radius, 
            centerX + radius, centerY + radius, 
            new Color(color.getRed(), color.getGreen(), color.getBlue(), innerAlpha).getRGB());
    }
    
    /**
     * Draw a modern scrollbar
     */
    public static void drawModernScrollbar(int x, int y, int width, int height, float scrollPercentage, boolean isHovering) {
        // Track
        Color trackColor = new Color(30, 30, 45, 150);
        Gui.drawRoundedRect(x, y, x + width, y + height, width / 2, trackColor.getRGB());
        
        // Thumb
        int thumbHeight = Math.max(20, height / 4);
        int thumbY = y + (int)((height - thumbHeight) * scrollPercentage);
        
        Color thumbColor = isHovering ? 
            new Color(120, 150, 200, 220) : 
            new Color(100, 130, 180, 200);
        Gui.drawRoundedRect(x, thumbY, x + width, thumbY + thumbHeight, width / 2, thumbColor.getRGB());
        
        // Thumb glow on hover
        if (isHovering) {
            drawGlowEffect(x - 1, thumbY - 1, x + width + 1, thumbY + thumbHeight + 1, 
                width / 2, new Color(100, 150, 255, 60));
        }
    }
    
    /**
     * Apply a smooth scale animation
     */
    public static void applyScaleAnimation(float centerX, float centerY, float scale) {
        GL11.glTranslatef(centerX, centerY, 0);
        GL11.glScalef(scale, scale, 1.0f);
        GL11.glTranslatef(-centerX, -centerY, 0);
    }
    
    /**
     * Smooth interpolation for animations
     */
    public static float smoothLerp(float current, float target, float speed, float deltaTime) {
        float diff = target - current;
        if (Math.abs(diff) < 0.001f) return target;
        return current + diff * speed * deltaTime;
    }
    
    /**
     * Easing function - ease in out cubic
     */
    public static float easeInOutCubic(float t) {
        return t < 0.5f ? 4 * t * t * t : 1 - (float)Math.pow(-2 * t + 2, 3) / 2;
    }
    
    /**
     * Easing function - ease out elastic
     */
    public static float easeOutElastic(float t) {
        float c4 = (float)(2 * Math.PI) / 3;
        return t == 0 ? 0 : t == 1 ? 1 : 
            (float)(Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * c4) + 1);
    }
}
