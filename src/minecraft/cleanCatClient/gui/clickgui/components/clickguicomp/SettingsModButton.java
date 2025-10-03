package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SettingsModButton {

    public int x, y, w, h;
    public int originalY;
    public Mod mod;
    
    // Animation variables
    private float hoverAnimation = 0.0f;
    private float toggleAnimation = 0.0f;
    private long lastUpdateTime = System.currentTimeMillis();

    public SettingsModButton(int x, int y, int w, int h, Mod mod) {
        this.x = x;
        this.y = y;
        this.originalY = this.y;
        this.w = w;
        this.h = h;
        this.mod = mod;
        this.toggleAnimation = mod.isEnabled() ? 1.0f : 0.0f;
    }

    public void render(int mouseX, int mouseY) {
        // Update animations
        updateAnimations(mouseX, mouseY);
        
        boolean isHovering = isMouseOver(mouseX, mouseY);
        
        GL11.glPushMatrix();
        
        // Draw modern background
        drawModernBackground(isHovering);
        
        // Draw mod name
        drawModName();
        
        // Draw modern toggle switch
        drawModernToggle(mouseX, mouseY);
        
        GL11.glPopMatrix();
    }
    
    private void updateAnimations(int mouseX, int mouseY) {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;
        
        // Hover animation
        boolean isHovering = isMouseOver(mouseX, mouseY);
        if (isHovering) {
            hoverAnimation += deltaTime * 5.0f;
            if (hoverAnimation > 1.0f) hoverAnimation = 1.0f;
        } else {
            hoverAnimation -= deltaTime * 5.0f;
            if (hoverAnimation < 0.0f) hoverAnimation = 0.0f;
        }
        
        // Toggle animation
        float targetToggle = mod.isEnabled() ? 1.0f : 0.0f;
        if (toggleAnimation < targetToggle) {
            toggleAnimation += deltaTime * 6.0f;
            if (toggleAnimation > targetToggle) toggleAnimation = targetToggle;
        } else if (toggleAnimation > targetToggle) {
            toggleAnimation -= deltaTime * 6.0f;
            if (toggleAnimation < targetToggle) toggleAnimation = targetToggle;
        }
    }
    
    private void drawModernBackground(boolean isHovering) {
        // Glow effect on hover
        if (hoverAnimation > 0.01f) {
            int glowAlpha = (int)(hoverAnimation * 40);
            drawGlowEffect(x - 2, y - 2, x + w + 2, y + h + 2, 10, 
                new Color(100, 150, 255, glowAlpha));
        }
        
        // Main background
        Color bgColor = new Color(30, 30, 45, Math.min(255, (int)(180 + hoverAnimation * 40)));
        Gui.drawRoundedRect(x, y, x + w, y + h, 8, bgColor.getRGB());
        
        // Gradient overlay
        drawGradientRoundedRect(x, y, x + w, y + h, 8,
            new Color(40, 40, 60, Math.min(255, (int)(50 + hoverAnimation * 30))).getRGB(),
            new Color(25, 25, 40, Math.min(255, (int)(30 + hoverAnimation * 20))).getRGB());
        
        // Border
        int borderAlpha = (int)(80 + hoverAnimation * 70);
        int clampedBorderAlpha = Math.min(255, borderAlpha);
        Color borderColor = new Color(100, 150, 255, clampedBorderAlpha);
        drawRoundedBorder(x, y, x + w, y + h, 8, 1.0f, borderColor);
    }
    
    private void drawModName() {
        double textWidth = FontUtil.normal.getStringWidth(mod.name);
        int textHeight = 12;
        double textX = x + 8;
        int textY = y + (h - textHeight) / 2;
        
        // Animated text color
        int alpha = Math.min(255, (int)(200 + hoverAnimation * 55));
        Color textColor = new Color(220, 230, 255, alpha);
        
        FontUtil.normal.drawStringWithShadow(mod.name, textX, textY, textColor.getRGB());
    }
    
    private void drawModernToggle(int mouseX, int mouseY) {
        int toggleWidth = 40;
        int toggleHeight = 18;
        int toggleX = x + w - toggleWidth - 8;
        int toggleY = y + (h - toggleHeight) / 2;
        
        boolean isHoveringToggle = mouseX >= toggleX && mouseX <= toggleX + toggleWidth &&
                                   mouseY >= toggleY && mouseY <= toggleY + toggleHeight;
        
        // Toggle background
        Color toggleBg = new Color(20, 20, 35, 200);
        Gui.drawRoundedRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 
            toggleHeight / 2, toggleBg.getRGB());
        
        // Animated toggle slider
        int sliderSize = toggleHeight - 4;
        int sliderX = toggleX + 2;
        int maxSliderX = toggleX + toggleWidth - sliderSize - 2;
        int currentSliderX = (int)(sliderX + (maxSliderX - sliderX) * toggleAnimation);
        int sliderY = toggleY + 2;
        
        // Filled background (animated)
        if (toggleAnimation > 0.01f) {
            int fillWidth = (int)(toggleWidth * toggleAnimation);
            Color fillColor1 = new Color(100, 200, 255, 255);
            Color fillColor2 = new Color(50, 150, 255, 255);
            
            drawGradientRoundedRect(toggleX, toggleY, toggleX + fillWidth, toggleY + toggleHeight,
                toggleHeight / 2, fillColor1.getRGB(), fillColor2.getRGB());
        }
        
        // Toggle slider (circle)
        Color sliderColor = mod.isEnabled() ?
            new Color(255, 255, 255, 255) :
            new Color(150, 150, 170, 255);
        
        // Glow for slider when enabled
        if (mod.isEnabled()) {
            drawCircleGlow(currentSliderX + sliderSize / 2, sliderY + sliderSize / 2, 
                sliderSize / 2 + 2, new Color(100, 200, 255, 100));
        }
        
        drawCircle(currentSliderX + sliderSize / 2, sliderY + sliderSize / 2, 
            sliderSize / 2, sliderColor);
        
        // Status text (ON/OFF) inside toggle background
        String statusText = mod.isEnabled() ? "ON" : "OFF";
        double statusWidth = FontUtil.normal.getStringWidth(statusText);
        
        // Position text on the opposite side of the slider
        double statusX;
        if (mod.isEnabled()) {
            statusX = toggleX + 6;
        } else {
            statusX = toggleX + toggleWidth - statusWidth - 6;
        }
        double statusY = toggleY + (toggleHeight - 8) / 2;
        
        int textAlpha = Math.min(255, (int)(150 + toggleAnimation * 105));
        Color statusColor = new Color(255, 255, 255, textAlpha);
        FontUtil.normal.drawString(statusText, statusX, (int)statusY, statusColor.getRGB());
        
        // Toggle border
        Color toggleBorderColor = isHoveringToggle ?
            new Color(100, 180, 255, 180) :
            new Color(60, 80, 120, 120);
        drawRoundedBorder(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 
            toggleHeight / 2, 1.0f, toggleBorderColor);
    }
    
    private void drawCircle(int centerX, int centerY, int radius, Color color) {
        // Simple circle using rect (for Minecraft 1.8.9)
        Gui.drawRect(centerX - radius, centerY - radius, 
                    centerX + radius, centerY + radius, color.getRGB());
    }
    
    private void drawCircleGlow(int centerX, int centerY, int radius, Color color) {
        for (int i = 0; i < 3; i++) {
            int glowRadius = radius + i * 2;
            int alpha = color.getAlpha() / (i + 1);
            drawCircle(centerX, centerY, glowRadius, 
                new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
        }
    }
    
    private void drawGlowEffect(int x1, int y1, int x2, int y2, int radius, Color color) {
        for (int i = 0; i < 3; i++) {
            int offset = i * 2;
            int alpha = color.getAlpha() / (i + 1);
            Gui.drawRoundedRect(x1 - offset, y1 - offset, x2 + offset, y2 + offset, 
                radius + offset, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB());
        }
    }
    
    private void drawGradientRoundedRect(int x1, int y1, int x2, int y2, int radius, int topColor, int bottomColor) {
        Gui.drawRoundedRect(x1, y1, x2, y1 + (y2 - y1) / 2, radius, topColor);
        Gui.drawRoundedRect(x1, y1 + (y2 - y1) / 2, x2, y2, radius, bottomColor);
    }
    
    private void drawRoundedBorder(int x1, int y1, int x2, int y2, int radius, float width, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(width);
        
        Gui.drawRect(x1, y1, x2, y1 + 1, color.getRGB());
        Gui.drawRect(x1, y2 - 1, x2, y2, color.getRGB());
        Gui.drawRect(x1, y1, x1 + 1, y2, color.getRGB());
        Gui.drawRect(x2 - 1, y1, x2, y2, color.getRGB());
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
    
    /**
     * Clamp alpha value to valid range (0-255)
     */
    private int clampAlpha(int alpha) {
        return Math.max(0, Math.min(255, alpha));
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public void onClick(int mouseX, int mouseY, int button) {
        int toggleWidth = 40;
        int toggleHeight = 18;
        int toggleX = x + w - toggleWidth - 8;
        int toggleY = y + (h - toggleHeight) / 2;

        if (button == 0) {
            if (mouseX >= toggleX && mouseX <= toggleX + toggleWidth && 
                mouseY >= toggleY && mouseY <= toggleY + toggleHeight) {
                mod.setEnabled(!mod.isEnabled());
            }
        }
    }
}