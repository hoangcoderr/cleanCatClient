package cleanCatClient.gui.clickgui.settings;

import cleanCatClient.Client;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModSettings extends GuiScreen {
    protected Mod mod;
    private int backgroundW;
    private int backgroundH;
    
    // Animation variables
    private float openAnimation = 0.0f;
    private long lastFrameTime = System.currentTimeMillis();
    private float backButtonHover = 0.0f;
    private float exitButtonHover = 0.0f;

    public ModSettings(Mod mod) {
        this.mod = mod;
    }

    @Override
    public void initGui() {
        super.initGui();
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

        ScaledResolution sr = new ScaledResolution(mc);
        backgroundW = Client.INSTANCE.clickGui.getWidth();
        backgroundH = Client.INSTANCE.clickGui.getHeight();
        
        // No need to create old buttons anymore - we draw custom modern buttons
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Update animations
        updateAnimations(mouseX, mouseY);
        
        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;

        int rectX = centerW - backgroundW / 2;
        int rectY = centerH - backgroundH / 2;
        int rectW = backgroundW;
        int rectH = backgroundH;
        
        GL11.glPushMatrix();
        
        // Apply scale animation for opening effect
        float scale = 0.85f + (openAnimation * 0.15f);
        GL11.glTranslatef(centerW, centerH, 0);
        GL11.glScalef(scale, scale, 1.0f);
        GL11.glTranslatef(-centerW, -centerH, 0);
        
        // Draw animated gradient background overlay
        drawAnimatedBackground();
        
        // Draw main panel with glass morphism
        drawMainPanel(rectX, rectY, rectW, rectH);
        
        // Draw header section
        drawHeaderSection(rectX, rectY, rectW);
        
        // Draw content area
        drawContentArea(rectX, rectY, rectW, rectH);
        
        // Draw modern buttons
        drawModernBackButton(mouseX, mouseY, centerW, centerH);
        drawModernExitButton(mouseX, mouseY, rectX, rectY, rectW);
        
        GL11.glPopMatrix();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void updateAnimations(int mouseX, int mouseY) {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastFrameTime) / 1000.0f;
        lastFrameTime = currentTime;
        
        // Opening animation
        if (openAnimation < 1.0f) {
            openAnimation += deltaTime * 3.5f;
            if (openAnimation > 1.0f) openAnimation = 1.0f;
        }
        
        // Back button hover animation
        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        int centerH = sr.getScaledHeight() / 2;
        int backBtnX = centerW - 50;
        int backBtnY = centerH + backgroundH / 2 + 10;
        boolean backHovering = mouseX >= backBtnX && mouseX <= backBtnX + 100 && 
                               mouseY >= backBtnY && mouseY <= backBtnY + 20;
        
        if (backHovering) {
            backButtonHover += deltaTime * 5.0f;
            if (backButtonHover > 1.0f) backButtonHover = 1.0f;
        } else {
            backButtonHover -= deltaTime * 5.0f;
            if (backButtonHover < 0.0f) backButtonHover = 0.0f;
        }
        
        // Exit button hover animation
        int rectX = centerW - backgroundW / 2;
        int rectY = centerH - backgroundH / 2;
        int exitBtnX = rectX + backgroundW - 60;
        int exitBtnY = rectY + 10;
        boolean exitHovering = mouseX >= exitBtnX && mouseX <= exitBtnX + 50 && 
                               mouseY >= exitBtnY && mouseY <= exitBtnY + 20;
        
        if (exitHovering) {
            exitButtonHover += deltaTime * 5.0f;
            if (exitButtonHover > 1.0f) exitButtonHover = 1.0f;
        } else {
            exitButtonHover -= deltaTime * 5.0f;
            if (exitButtonHover < 0.0f) exitButtonHover = 0.0f;
        }
    }
    
    private void drawAnimatedBackground() {
        int alpha = (int)(openAnimation * 140);
        drawGradientRect(0, 0, this.width, this.height, 
            new Color(0, 0, 0, alpha).getRGB(),
            new Color(20, 20, 40, alpha).getRGB());
    }
    
    private void drawMainPanel(int x, int y, int width, int height) {
        // Outer glow
        drawGlowEffect(x - 4, y - 4, x + width + 4, y + height + 4, 12, 
            new Color(100, 150, 255, (int)(openAnimation * 30)));
        
        // Glass morphism background
        Gui.drawRoundedRect(x, y, x + width, y + height, 12, new Color(25, 25, 35, 220).getRGB());
        
        // Gradient overlay
        drawGradientRoundedRect(x, y, x + width, y + height, 12,
            new Color(60, 60, 80, 50).getRGB(),
            new Color(30, 30, 50, 50).getRGB());
        
        // Border
        drawRoundedBorder(x, y, x + width, y + height, 12, 1.5f, 
            new Color(100, 150, 255, (int)(openAnimation * 100)));
    }
    
    private void drawHeaderSection(int x, int y, int width) {
        // Header background
        Gui.drawRoundedRect(x, y, x + width, y + 50, 12, new Color(35, 35, 50, 180).getRGB());
        
        // Bottom border of header
        Gui.drawRect(x + 10, y + 49, x + width - 10, y + 50, new Color(100, 150, 255, 100).getRGB());
        
        // Mod name with gradient effect
        int modNameX = x + 15;
        int modNameY = y + 15;
        
        // Status indicator
        if (mod.isEnabled()) {
            int indicatorX = x + width - 25;
            int indicatorY = y + 20;
            drawPulsingDot(indicatorX, indicatorY, 5, new Color(100, 255, 150, 255));
        }
        
        // Draw mod name
        int color = new Color(200, 230, 255, (int)(openAnimation * 255)).getRGB();
        FontUtil.getFontRenderer(30).drawStringWithShadow(mod.name, modNameX, modNameY, color);
        
        // Subtitle
        String subtitle = "Module Settings";
        int subtitleY = modNameY + 20;
        FontUtil.normal.drawString(subtitle, modNameX, subtitleY, 
            new Color(150, 170, 200, (int)(openAnimation * 200)).getRGB());
    }
    
    private void drawContentArea(int x, int y, int width, int height) {
        // Content area background - just draw the background, let subclasses draw their own content
        int contentY = y + 55;
        int contentHeight = height - 55 - 45; // Leave space for buttons
        
        Gui.drawRoundedRect(x + 5, contentY, x + width - 5, contentY + contentHeight, 8, 
            new Color(20, 20, 30, 100).getRGB());
        
        // Subclasses will override drawScreen() and draw their settings here
    }
    
    // Helper methods for subclasses to get content area dimensions
    protected int getContentAreaX() {
        ScaledResolution sr = new ScaledResolution(mc);
        int centerW = sr.getScaledWidth() / 2;
        return centerW - backgroundW / 2 + 5;
    }
    
    protected int getContentAreaY() {
        ScaledResolution sr = new ScaledResolution(mc);
        int centerH = sr.getScaledHeight() / 2;
        int rectY = centerH - backgroundH / 2;
        return rectY + 55;
    }
    
    protected int getContentAreaWidth() {
        return backgroundW - 10;
    }
    
    protected int getContentAreaHeight() {
        return backgroundH - 55 - 45;
    }
    
    private void drawModernBackButton(int mouseX, int mouseY, int centerW, int centerH) {
        int btnX = centerW - 50;
        int btnY = centerH + backgroundH / 2 + 10;
        int btnW = 100;
        int btnH = 20;
        
        // Glow on hover
        if (backButtonHover > 0.01f) {
            int glowAlpha = (int)(backButtonHover * 60);
            drawGlowEffect(btnX - 2, btnY - 2, btnX + btnW + 2, btnY + btnH + 2, 8, 
                new Color(100, 200, 255, glowAlpha));
        }
        
        // Background
        Color bgColor = new Color(50, 70, 100, (int)(180 + backButtonHover * 75));
        Gui.drawRoundedRect(btnX, btnY, btnX + btnW, btnY + btnH, 8, bgColor.getRGB());
        
        // Gradient overlay
        drawGradientRoundedRect(btnX, btnY, btnX + btnW, btnY + btnH, 8,
            new Color(70, 100, 140, (int)(backButtonHover * 100)).getRGB(),
            new Color(40, 60, 90, (int)(backButtonHover * 50)).getRGB());
        
        // Border
        drawRoundedBorder(btnX, btnY, btnX + btnW, btnY + btnH, 8, 1.5f, 
            new Color(100, 180, 255, (int)(100 + backButtonHover * 100)));
        
        // Text
        String text = "← Back";
        int textWidth = (int)FontUtil.normal.getStringWidth(text);
        int textX = btnX + (btnW - textWidth) / 2;
        int textY = btnY + (btnH - 8) / 2;
        
        FontUtil.normal.drawStringWithShadow(text, textX, textY, 
            new Color(220, 240, 255, 255).getRGB());
    }
    
    private void drawModernExitButton(int mouseX, int mouseY, int rectX, int rectY, int rectW) {
        int btnX = rectX + rectW - 60;
        int btnY = rectY + 10;
        int btnW = 50;
        int btnH = 20;
        
        // Glow on hover
        if (exitButtonHover > 0.01f) {
            int glowAlpha = (int)(exitButtonHover * 60);
            drawGlowEffect(btnX - 2, btnY - 2, btnX + btnW + 2, btnY + btnH + 2, 8, 
                new Color(255, 100, 100, glowAlpha));
        }
        
        // Background
        Color bgColor = new Color(100, 50, 50, (int)(150 + exitButtonHover * 75));
        Gui.drawRoundedRect(btnX, btnY, btnX + btnW, btnY + btnH, 8, bgColor.getRGB());
        
        // Gradient overlay
        drawGradientRoundedRect(btnX, btnY, btnX + btnW, btnY + btnH, 8,
            new Color(140, 70, 70, (int)(exitButtonHover * 100)).getRGB(),
            new Color(90, 40, 40, (int)(exitButtonHover * 50)).getRGB());
        
        // Border
        drawRoundedBorder(btnX, btnY, btnX + btnW, btnY + btnH, 8, 1.5f, 
            new Color(255, 120, 120, (int)(100 + exitButtonHover * 100)));
        
        // Text
        String text = "✕";
        int textWidth = mc.fontRendererObj.getStringWidth(text);
        int textX = btnX + (btnW - textWidth) / 2;
        int textY = btnY + (btnH - 8) / 2;
        
        FontUtil.normal.drawStringWithShadow(text, textX, textY, 
            new Color(255, 220, 220, 255).getRGB());
    }
    
    // Helper methods for modern UI effects
    private void drawGlowEffect(int x1, int y1, int x2, int y2, int radius, Color color) {
        for (int i = 0; i < 3; i++) {
            int offset = i * 2;
            int alpha = color.getAlpha() / (i + 1);
            Gui.drawRoundedRect(x1 - offset, y1 - offset, x2 + offset, y2 + offset, 
                radius + offset, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB());
        }
    }
    
    private void drawGradientRoundedRect(int x1, int y1, int x2, int y2, int radius, int topColor, int bottomColor) {
        int midY = y1 + (y2 - y1) / 2;
        Gui.drawRoundedRect(x1, y1, x2, midY, radius, topColor);
        Gui.drawRoundedRect(x1, midY, x2, y2, radius, bottomColor);
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
    
    private void drawPulsingDot(int centerX, int centerY, int radius, Color color) {
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


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws java.io.IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        if (mouseButton == 0) {
            ScaledResolution sr = new ScaledResolution(mc);
            int centerW = sr.getScaledWidth() / 2;
            int centerH = sr.getScaledHeight() / 2;
            
            // Check back button click
            int backBtnX = centerW - 50;
            int backBtnY = centerH + backgroundH / 2 + 10;
            if (mouseX >= backBtnX && mouseX <= backBtnX + 100 && 
                mouseY >= backBtnY && mouseY <= backBtnY + 20) {
                mc.displayGuiScreen(Client.INSTANCE.clickGui);
                return;
            }
            
            // Check exit button click
            int rectX = centerW - backgroundW / 2;
            int rectY = centerH - backgroundH / 2;
            int exitBtnX = rectX + backgroundW - 60;
            int exitBtnY = rectY + 10;
            if (mouseX >= exitBtnX && mouseX <= exitBtnX + 50 && 
                mouseY >= exitBtnY && mouseY <= exitBtnY + 20) {
                mc.displayGuiScreen(null);
                return;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        super.onGuiClosed();
    }

}