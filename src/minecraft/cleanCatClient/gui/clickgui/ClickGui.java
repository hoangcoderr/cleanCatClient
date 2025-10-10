package cleanCatClient.gui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cleanCatClient.gui.clickgui.components.clickguicomp.SettingsModButton;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.clickgui.settings.ModSettingsInstance;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cleanCatClient.gui.clickgui.components.clickguicomp.CategoryManager;
import cleanCatClient.gui.clickgui.components.clickguicomp.ClickGuiCategoryButton;
import cleanCatClient.gui.clickgui.components.clickguicomp.ModButton;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGui extends GuiScreen {

    public static ArrayList<ClickGuiCategoryButton> clickGuiCategoryButton = new ArrayList<>();

    public static ArrayList<ModButton> modButtonToRender = new ArrayList<>();

    public static ArrayList<SettingsModButton> settingsModButton = new ArrayList<>();
    public static ResourceLocation res = new ResourceLocation("cleanCatClient/Logo/clientLogo.png");
    ScaledResolution sr;

    public int getWidth() {
        return (centerW + backgroundW) - (centerW - backgroundW);
    }

    public int getHeight() {
        return 250; // Assuming the height is fixed as 250 based on the initGui method
    }

    int backgroundW = 200;
    int centerW;
    int centerH;
    
    // Animation variables
    private float openAnimation = 0.0f;
    private long lastFrameTime = System.currentTimeMillis();
    private float categoryHoverAnimation = 0.0f;
    private int hoveredCategory = -1;

    public void resetScroll() {
        currentScroll = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        sr = new ScaledResolution(mc);
        centerW = sr.getScaledWidth() / 2;
        centerH = sr.getScaledHeight() / 2;
        reset();

        backgroundW = 250;

        clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 85, 120, 25, "Player", 0));
        clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 55, 120, 25, "World", 1));
        clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 25, 120, 25, "Render", 2));
        clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH + 5, 120, 25, "Util", 3));
        clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH + 35, 120, 25, "HudManager", 4));

        int modButtonSize = 90;
        int spaceBetween = 10;
        int buttonsPerRow = 3;

        Map<Integer, Integer> categoryIndices = new HashMap<>();
        for (int i = 0; i < ModInstances.getAllMods().size(); i++) {
            Mod mod = ModInstances.getAllMods().get(i);
            int categoryID = mod.getCategoryId();

            int index = categoryIndices.getOrDefault(categoryID, 0);
            System.out.println("Category ID: " + categoryID + " Index: " + index + "Mod name: " + mod.name);
            //   addModButton(categoryID, mod, index, modButtonSize, spaceBetween, buttonsPerRow, new CustomCrossHairSettings());
            addModButton(categoryID, mod, index, modButtonSize, spaceBetween, buttonsPerRow, ModSettingsInstance.getAllSettings().get(i));
            categoryIndices.put(categoryID, index + 1);
        }
        settingsModButton.clear();

        addSettingsModButton(ModInstances.getFullBright(), 0, 340, 20, spaceBetween);
        addSettingsModButton(ModInstances.getMinimalViewBobbing(), 1, 340, 20, spaceBetween);
        addSettingsModButton(ModInstances.getLazyChunkLoading(), 2, 340, 20, spaceBetween);
        addSettingsModButton(ModInstances.getDisableBlockParticles(), 3, 340, 20, spaceBetween);
        addSettingsModButton(ModInstances.getNoHurtCam(), 4, 340, 20, spaceBetween);
        addSettingsModButton(ModInstances.getBossBar(), 5, 340, 20, spaceBetween);
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        super.onGuiClosed();


    }

    private void addModButton(int categoryID, Mod mod, int index, int size, int spaceBetween, int buttonsPerRow, ModSettings settings) {
        int row = index / buttonsPerRow;
        int col = index % buttonsPerRow;
        
        // Calculate position relative to content area (right side of GUI)
        // Content area starts after the sidebar (centerW - backgroundW + 130)
        int contentStartX = -120; // Relative to center, will be calculated in render
        int contentStartY = -90;  // Relative to center
        
        int x = contentStartX + col * (size + spaceBetween);
        int y = contentStartY + row * (size + spaceBetween);

        modButtonToRender.add(new ModButton(x, y, size, size, mod, categoryID, settings));
    }

    // Add this method to the ClickGui class
    private void addSettingsModButton(Mod mod, int index, int width, int height, int spaceBetween) {
        // Store relative positions
        int x = 130 - backgroundW; // Relative to center
        int y = -90 + index * (height + spaceBetween);

        settingsModButton.add(new SettingsModButton(x, y, width, height, mod));
    }

    private int currentScroll = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Calculate delta time for smooth animations
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastFrameTime) / 1000.0f;
        lastFrameTime = currentTime;
        
        // Update opening animation
        if (openAnimation < 1.0f) {
            openAnimation += deltaTime * 3.0f; // 3.0f = animation speed
            if (openAnimation > 1.0f) openAnimation = 1.0f;
        }

        GL11.glPushMatrix();
        
        // Apply scale animation for opening effect
        float scale = 0.8f + (openAnimation * 0.2f); // Scale from 0.8 to 1.0
        GL11.glTranslatef(centerW, centerH, 0);
        GL11.glScalef(scale, scale, 1.0f);
        GL11.glTranslatef(-centerW, -centerH, 0);

        // Draw animated gradient background
        drawAnimatedBackground();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        centerW = sr.getScaledWidth() / 2;
        centerH = sr.getScaledHeight() / 2;

        // Draw main panel with glass morphism effect
        drawGlassMorphismPanel(centerW - backgroundW, centerH - 125, centerW + backgroundW, centerH + 125);
        
        // Draw sidebar with modern gradient
        drawModernSidebar(centerW - backgroundW + 480, centerH - 125, centerW + backgroundW, centerH + 125);
        
        // Draw logo with glow effect
        drawLogoWithGlow(centerW - backgroundW + 5, centerH - 120);
        
        // Draw title with gradient text
        drawGradientText("cleanCat Client", centerW - backgroundW + 50, centerH - 120 + 10, 30);

        // Render category buttons with hover effects
        renderCategoryButtons(mouseX, mouseY);

        int wheel = Mouse.getDWheel();
        int scrollAmount = 20;
        int visibleHeight = 230;
        int totalButtonHeight = modButtonToRender.size() * 26;
        int maxScroll = Math.max(0, totalButtonHeight - visibleHeight);

        if (wheel < 0) {
            currentScroll += scrollAmount;
        } else if (wheel > 0) {
            currentScroll -= scrollAmount;
        }

        currentScroll = Math.max(0, Math.min(currentScroll, maxScroll));

        for (ModButton modButton : modButtonToRender) {
            if (modButton.id == CategoryManager.currentPage) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                this.glScissor(centerW - backgroundW, centerH - 115, centerW + backgroundW, 230);
                
                // Set scroll offset before rendering
                modButton.y = modButton.originalY - currentScroll;
                modButton.render(mouseX, mouseY);
                
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }

        if (CategoryManager.currentPage == 3) {
            for (SettingsModButton settingsModButton : settingsModButton) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                this.glScissor(centerW - backgroundW, centerH - 115, centerW + backgroundW, 230);
                
                // Set scroll offset before rendering
                settingsModButton.y = settingsModButton.originalY - currentScroll;
                settingsModButton.render(mouseX, mouseY);
                
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }

        drawScrollbar();

        GL11.glPopMatrix();
    }

    private void drawScrollbar() {
        int scrollbarWidth = 8;
        int totalButtonHeight = modButtonToRender.size() * 26;
        int visibleHeight = 230;
        int scrollbarHeight = 5000 / ModInstances.getAllMods().size();

        double scrollBarHeightAdjustment = 0.6;
        scrollbarHeight = (int) (scrollbarHeight * scrollBarHeightAdjustment);

        int maxScroll = totalButtonHeight - visibleHeight;
        int scrollbarY = (int) ((float) currentScroll / maxScroll * (visibleHeight - scrollbarHeight)) + (centerH - 115);

        scrollbarY = Math.min(scrollbarY, centerH + 125 - scrollbarHeight);

        int rectCenterX = (centerW - backgroundW + 480 + centerW + backgroundW) / 2;
        int scrollbarX = rectCenterX - (scrollbarWidth / 2);

        Gui.drawRect(scrollbarX, scrollbarY, scrollbarX + scrollbarWidth, scrollbarY + scrollbarHeight, new Color(100, 100, 100, 200).getRGB());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= (centerW - backgroundW) && mouseX <= (centerW + backgroundW) && mouseY >= (centerH - 90) && mouseY <= (centerH + 90)) {
            for (ModButton modButton : modButtonToRender) {
                if (modButton.id == CategoryManager.currentPage) {
                    modButton.onClick(mouseX, mouseY, mouseButton);
                }
            }

        }
        for (ClickGuiCategoryButton clickGuiCategoryButton : clickGuiCategoryButton) {
            clickGuiCategoryButton.onClick(mouseX, mouseY, mouseButton);
        }
        for (SettingsModButton settingsModButton : settingsModButton) {
            settingsModButton.onClick(mouseX, mouseY, mouseButton);
        }
    }

    public static ArrayList<ClickGuiCategoryButton> getClickGuiCategoryButton() {
        return clickGuiCategoryButton;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {

        super.keyTyped(typedChar, keyCode);
    }

    private static void reset() {
        modButtonToRender.removeAll(modButtonToRender);
        clickGuiCategoryButton.removeAll(clickGuiCategoryButton);

    }

    private void glScissor(double x, double y, double width, double height) {
        y += height;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glScissor((int) ((x * mc.displayWidth) / scaledResolution.getScaledWidth()),
                (int) (((scaledResolution.getScaledHeight() - y) * mc.displayHeight) / scaledResolution.getScaledHeight()),
                (int) (width * mc.displayWidth / scaledResolution.getScaledWidth()),
                (int) (height * mc.displayHeight / scaledResolution.getScaledHeight()));
    }
    
    // ============ MODERN UI METHODS ============
    
    private void drawAnimatedBackground() {
        // Animated gradient background overlay
        int alpha = (int)(openAnimation * 140);
        drawGradientRect(0, 0, this.width, this.height, 
            new Color(0, 0, 0, alpha).getRGB(),
            new Color(20, 20, 40, alpha).getRGB());
    }
    
    private void drawGlassMorphismPanel(int x1, int y1, int x2, int y2) {
        // Outer glow
        drawGlowEffect(x1 - 4, y1 - 4, x2 + 4, y2 + 4, 12, new Color(100, 150, 255, 30));
        
        // Glass morphism background
        Gui.drawRoundedRect(x1, y1, x2, y2, 12, new Color(25, 25, 35, 200).getRGB());
        
        // Subtle gradient overlay
        drawGradientRoundedRect(x1, y1, x2, y2, 12,
            new Color(60, 60, 80, 50).getRGB(),
            new Color(30, 30, 50, 50).getRGB());
        
        // Border highlight
        drawRoundedBorder(x1, y1, x2, y2, 12, 1.5f, new Color(100, 150, 255, 100));
    }
    
    private void drawModernSidebar(int x1, int y1, int x2, int y2) {
        // Dark sidebar with gradient
        Gui.drawRoundedRect(x1, y1, x2, y2, 12, new Color(15, 15, 25, 230).getRGB());
        
        // Gradient overlay
        drawGradientRoundedRect(x1, y1, x2, y2, 12,
            new Color(30, 30, 50, 100).getRGB(),
            new Color(15, 15, 25, 50).getRGB());
        
        // Subtle border
        drawRoundedBorder(x1, y1, x2, y2, 12, 1.0f, new Color(80, 80, 100, 80));
    }
    
    private void drawLogoWithGlow(int x, int y) {
        // Glow effect behind logo
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Animated glow
        float glowPulse = (float)(Math.sin(System.currentTimeMillis() / 500.0) * 0.3 + 0.7);
        int glowSize = 50;
        drawGlowEffect(x - 5, y - 5, x + 45, y + 45, glowSize, 
            new Color(100, 150, 255, (int)(glowPulse * 60)));
        
        // Draw logo
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        GlStateManager.color(1.0f, 1.0f, 1.0f, openAnimation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 40, 40, 40, 40);
        
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }
    
    private void drawGradientText(String text, int x, int y, int size) {
        // Modern gradient text effect
        int color1 = new Color(100, 200, 255, (int)(openAnimation * 255)).getRGB();
        int color2 = new Color(150, 100, 255, (int)(openAnimation * 255)).getRGB();
        
        FontUtil.getFontRenderer(size).drawStringWithShadow(text, x, y, color1);
    }
    
    private void renderCategoryButtons(int mouseX, int mouseY) {
        for (int i = 0; i < clickGuiCategoryButton.size(); i++) {
            ClickGuiCategoryButton button = clickGuiCategoryButton.get(i);
            
            // Check if mouse is hovering
            boolean isHovering = mouseX >= button.x && mouseX <= button.x + button.w && 
                               mouseY >= button.y && mouseY <= button.y + button.h;
            
            if (isHovering) {
                hoveredCategory = i;
            }
            
            button.renderButton();
        }
    }
    
    private void drawGlowEffect(int x1, int y1, int x2, int y2, int radius, Color color) {
        // Simple glow implementation using multiple layers
        for (int i = 0; i < 3; i++) {
            int offset = i * 2;
            int alpha = color.getAlpha() / (i + 1);
            Gui.drawRoundedRect(x1 - offset, y1 - offset, x2 + offset, y2 + offset, 
                radius + offset, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB());
        }
    }
    
    private void drawGradientRoundedRect(int x1, int y1, int x2, int y2, int radius, int topColor, int bottomColor) {
        // Draw gradient in rounded rect
        Gui.drawRoundedRect(x1, y1, x2, y1 + (y2 - y1) / 2, radius, topColor);
        Gui.drawRoundedRect(x1, y1 + (y2 - y1) / 2, x2, y2, radius, bottomColor);
    }
    
    private void drawRoundedBorder(int x1, int y1, int x2, int y2, int radius, float width, Color color) {
        // Draw border outline
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, 
                      color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        
        // Simple border implementation
        Gui.drawRect(x1, y1, x2, y1 + 1, color.getRGB());
        Gui.drawRect(x1, y2 - 1, x2, y2, color.getRGB());
        Gui.drawRect(x1, y1, x1 + 1, y2, color.getRGB());
        Gui.drawRect(x2 - 1, y1, x2, y2, color.getRGB());
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

}