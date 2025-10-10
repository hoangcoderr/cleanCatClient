package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModButton {

    public int x, y, w, h;
    public int originalY;
    public Mod mod;
    public int id;
    public ModSettings modSettings;
    
    // Store relative position instead of absolute
    private int relativeX;
    private int relativeY;
    
    // Animation variables
    private float hoverAnimation = 0.0f;
    private float toggleAnimation = 0.0f;
    private float scaleAnimation = 1.0f;
    private long lastUpdateTime = System.currentTimeMillis();

    public ModButton(int relX, int relY, int w, int h, Mod mod, int id, ModSettings modSettings) {
        this.relativeX = relX;
        this.relativeY = relY;
        this.w = w;
        this.h = h;
        this.mod = mod;
        this.id = id;
        this.modSettings = modSettings;
        this.toggleAnimation = mod.isEnabled() ? 1.0f : 0.0f;
        
        // Will be updated in render based on current screen size
        this.x = 0;
        this.y = 0;
        this.originalY = 0;
    }

    public void render(int mouseX, int mouseY) {
        // Update position based on current screen center
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;
        
        this.x = centerW + relativeX;
        this.originalY = centerH + relativeY;
        // Note: actual y will be set by ClickGui with scroll offset
        
        // Update animations
        updateAnimations(mouseX, mouseY);
        
        GL11.glPushMatrix();
        
        // Apply scale animation
        float centerX = x + w / 2.0f;
        float centerY = y + h / 2.0f;
        GL11.glTranslatef(centerX, centerY, 0);
        GL11.glScalef(scaleAnimation, scaleAnimation, 1.0f);
        GL11.glTranslatef(-centerX, -centerY, 0);
        
        // Draw card with modern design
        drawModernCard(mouseX, mouseY);
        
        // Draw mod name with gradient
        drawModName();
        
        // Draw modern toggle switch
        drawModernToggle(mouseX, mouseY);
        
        GL11.glPopMatrix();
        
        // Draw tooltip on hover
        if (isMouseOver(mouseX, mouseY) && hoverAnimation > 0.5f) {
            drawModernTooltip(mouseX, mouseY, mod.description);
        }
    }
    
    private void updateAnimations(int mouseX, int mouseY) {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;
        
        // Hover animation
        boolean isHovering = isMouseOver(mouseX, mouseY);
        if (isHovering) {
            hoverAnimation += deltaTime * 4.0f;
            if (hoverAnimation > 1.0f) hoverAnimation = 1.0f;
        } else {
            hoverAnimation -= deltaTime * 4.0f;
            if (hoverAnimation < 0.0f) hoverAnimation = 0.0f;
        }
        
        // Toggle animation
        float targetToggle = mod.isEnabled() ? 1.0f : 0.0f;
        if (toggleAnimation < targetToggle) {
            toggleAnimation += deltaTime * 5.0f;
            if (toggleAnimation > targetToggle) toggleAnimation = targetToggle;
        } else if (toggleAnimation > targetToggle) {
            toggleAnimation -= deltaTime * 5.0f;
            if (toggleAnimation < targetToggle) toggleAnimation = targetToggle;
        }
        
        // Scale animation on hover
        float targetScale = isHovering ? 1.05f : 1.0f;
        if (scaleAnimation < targetScale) {
            scaleAnimation += deltaTime * 3.0f;
            if (scaleAnimation > targetScale) scaleAnimation = targetScale;
        } else if (scaleAnimation > targetScale) {
            scaleAnimation -= deltaTime * 3.0f;
            if (scaleAnimation < targetScale) scaleAnimation = targetScale;
        }
    }
    
    private void drawModernCard(int mouseX, int mouseY) {
        // Animated glow effect
        if (hoverAnimation > 0.01f) {
            int glowAlpha = (int)(hoverAnimation * 60);
            Color glowColor = mod.isEnabled() ? 
                new Color(100, 200, 255, glowAlpha) : 
                new Color(150, 150, 200, glowAlpha);
            drawGlowEffect(x - 3, y - 3, x + w + 3, y + h + 3, 15, glowColor);
        }
        
        // Main card background with gradient
        Color bgColor1 = new Color(35, 35, 50, 220);
        Color bgColor2 = new Color(25, 25, 40, 220);
        
        // Draw gradient background
        Gui.drawRoundedRect(x, y, x + w, y + h, 10, bgColor1.getRGB());
        drawGradientRoundedRect(x, y, x + w, y + h, 10, 
            bgColor1.getRGB(), bgColor2.getRGB());
        
        // Animated border
        int borderAlpha = Math.min(255, (int)(100 + hoverAnimation * 100));
        Color borderColor = mod.isEnabled() ?
            new Color(100, 200, 255, borderAlpha) :
            new Color(80, 80, 120, borderAlpha);
        drawRoundedBorder(x, y, x + w, y + h, 10, 1.5f, borderColor);
        
        // Status indicator (top right corner)
        if (mod.isEnabled()) {
            int indicatorSize = 6;
            int indicatorX = x + w - 12;
            int indicatorY = y + 8;
            drawPulsingDot(indicatorX, indicatorY, indicatorSize, new Color(100, 255, 150, 255));
        }
    }
    
    private void drawModName() {
        double textWidth = FontUtil.normal.getStringWidth(mod.name);
        double textX = x + (w - textWidth) / 2;
        int textY = y + 20;
        
        // Gradient text effect
        int color = mod.isEnabled() ?
            new Color(200, 230, 255, 255).getRGB() :
            new Color(180, 180, 200, 255).getRGB();
            
        FontUtil.normal.drawStringWithShadow(mod.name, textX, textY, color);
    }
    
    private void drawModernToggle(int mouseX, int mouseY) {
        int toggleWidth = w - 20;
        int toggleX = x + 10;
        int toggleY = (y + h) - 25;
        int toggleHeight = 16;
        
        boolean isHoveringToggle = mouseX >= toggleX && mouseX <= toggleX + toggleWidth &&
                mouseY >= toggleY && mouseY <= toggleY + toggleHeight;
        
        // Animated toggle background
        Color toggleBgColor = new Color(30, 30, 45, 200);
        Gui.drawRoundedRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 8, toggleBgColor.getRGB());
        
        // Animated fill based on toggle state
        int fillWidth = (int)(toggleWidth * toggleAnimation);
        
        // Gradient colors for enabled state
        Color fillColor1 = new Color(
            (int)(50 + toggleAnimation * 50),
            (int)(150 + toggleAnimation * 100),
            (int)(100 + toggleAnimation * 155),
            255
        );
        Color fillColor2 = new Color(
            (int)(30 + toggleAnimation * 70),
            (int)(200 + toggleAnimation * 55),
            (int)(100 + toggleAnimation * 100),
            255
        );
        
        if (fillWidth > 0) {
            // Draw animated fill with gradient
            drawGradientRoundedRect(toggleX, toggleY, toggleX + fillWidth, toggleY + toggleHeight, 8,
                fillColor1.getRGB(), fillColor2.getRGB());
        }
        
        // Toggle text with animation
        String toggleText = mod.isEnabled() ? "ENABLED" : "DISABLED";
        double textWidth = FontUtil.normal.getStringWidth(toggleText);
        double textX = toggleX + (toggleWidth - textWidth) / 2;
        double textY = toggleY + (toggleHeight - 8) / 2;
        
        // Animated text color
        int textAlpha = isHoveringToggle ? 255 : 230;
        Color textColor = new Color(255, 255, 255, textAlpha);
        
        // Add glow to text when enabled
        if (mod.isEnabled()) {
            FontUtil.normal.drawStringWithShadow(toggleText, textX, (int)textY, textColor.getRGB());
        } else {
            FontUtil.normal.drawString(toggleText, textX, (int)textY, textColor.getRGB());
        }
        
        // Border for toggle
        Color toggleBorderColor = isHoveringToggle ?
            new Color(100, 150, 255, 150) :
            new Color(60, 60, 80, 120);
        drawRoundedBorder(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 8, 1.0f, toggleBorderColor);
    }
    
    private void drawModernTooltip(int mouseX, int mouseY, String description) {
        if (description == null || description.isEmpty()) return;
        
        int tooltipWidth = (int)FontUtil.normal.getStringWidth(description) + 16;
        int tooltipHeight = 24;
        int tooltipX = mouseX + 12;
        int tooltipY = mouseY - tooltipHeight - 8;
        
        // Prevent tooltip from going off screen
        if (tooltipX + tooltipWidth > Minecraft.getMinecraft().currentScreen.width) {
            tooltipX = mouseX - tooltipWidth - 12;
        }
        if (tooltipY < 0) {
            tooltipY = mouseY + 12;
        }
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 300); // Draw on top
        
        // Glow effect
        drawGlowEffect(tooltipX - 2, tooltipY - 2, tooltipX + tooltipWidth + 2, tooltipY + tooltipHeight + 2, 
            10, new Color(100, 150, 255, 40));
        
        // Background with gradient
        Gui.drawRoundedRect(tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 
            6, new Color(20, 20, 30, 240).getRGB());
        drawGradientRoundedRect(tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 6,
            new Color(40, 40, 60, 200).getRGB(),
            new Color(20, 20, 30, 200).getRGB());
        
        // Border
        drawRoundedBorder(tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 
            6, 1.0f, new Color(100, 150, 255, 120));
        
        // Text
        FontUtil.normal.drawStringWithShadow(description, tooltipX + 8, tooltipY + 8, 
            new Color(220, 230, 255, 255).getRGB());
        
        GL11.glPopMatrix();
    }
    
    private void drawPulsingDot(int x, int y, int size, Color color) {
        float pulse = (float)(Math.sin(System.currentTimeMillis() / 300.0) * 0.3 + 0.7);
        int alpha = (int)(pulse * color.getAlpha());
        
        // Outer glow
        Gui.drawRect(x - 1, y - 1, x + size + 1, y + size + 1, 
            new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha / 2).getRGB());
        
        // Inner dot
        Gui.drawRect(x, y, x + size, y + size, 
            new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB());
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

    private int getColor() {
        if (mod.isEnabled()) {
            return new Color(131, 255, 92, 255).getRGB();
        } else {
            return new Color(255, 64, 59, 255).getRGB();
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public void onClick(int mouseX, int mouseY, int button) {
        int rectX1 = x + 10;
        int rectY1 = (y + h) - 25;
        int rectX2 = x + w - 10;
        int rectY2 = (y + h) - 9;

        if (button == 0) {
            if (mouseX >= rectX1 && mouseX <= rectX2 && mouseY >= rectY1 && mouseY <= rectY2) {
                mod.setEnabled(!mod.isEnabled());
            } else if (isMouseOver(mouseX, mouseY)) {
                Minecraft.getMinecraft().displayGuiScreen(modSettings);
            }
        }
    }
}