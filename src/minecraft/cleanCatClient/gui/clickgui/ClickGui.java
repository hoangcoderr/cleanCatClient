package cleanCatClient.gui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;

import cleanCatClient.mods.ModInstances;

public class ClickGui extends GuiScreen {
    private ModCategory selectedCategory = ModCategory.PLAYER;
    private String searchText = "";
    private int scrollOffset = 0;
    private int maxScroll = 0;
    private boolean isDraggingScroll = false;
    private int dragStartY = 0;
    private int scrollStartOffset = 0;
    private boolean searchBarFocused = false;
    private int searchBarCursorTick = 0;
    private int lastMouseX = 0, lastMouseY = 0;
    private Mod hoveredMod = null;
    private Mod toggledMod = null;
    private long toggleAnimStart = 0;
    private boolean toggleTargetState = false;

    private final int CATEGORY_HEIGHT = 28;
    private final int SEARCH_HEIGHT = 24;
    private final int CARD_MARGIN = 18;
    private final int TOP_MARGIN = 18;
    private final int SIDE_MARGIN = 18;
    private final int SCROLLBAR_WIDTH = 8;
    private final int ANIMATION_DURATION = 180; // ms
    private final int MIN_CARD_WIDTH = 120;
    private final int MAX_CARDS_PER_ROW = 3;

    public static final ResourceLocation res = new ResourceLocation("cleanCatClient/Logo/clientLogo.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        searchBarCursorTick++;

        // --- BLUR BACKGROUND (fake blur if no shader) ---
        drawBlurBackground(width, height);

        // --- PANEL SIZE & POSITION (center, smaller) ---
        int panelW = (int)(width * 0.62);
        int panelH = (int)(height * 0.74);
        int panelX = (width - panelW) / 2;
        int panelY = (height - panelH) / 2;
        drawShadowedRoundedRect(panelX, panelY, panelX + panelW, panelY + panelH, 18, new Color(24, 32, 48, 235).getRGB());

        // --- LOGO & CLIENT NAME ---
        int logoSize = 56;
        int logoX = panelX + panelW / 2 - logoSize / 2;
        int logoY = panelY + 10;
        mc.getTextureManager().bindTexture(res);
        GlStateManager.enableBlend();
        drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoSize, logoSize, logoSize, logoSize);
        GlStateManager.disableBlend();
        String clientName = "CleanCat Client";
        mc.fontRendererObj.drawString(clientName, panelX + panelW / 2 - mc.fontRendererObj.getStringWidth(clientName) / 2, logoY + logoSize + 4, 0xB0E0FF, false);

        // --- CATEGORY SIDEBAR (vertical left) ---
        int sidebarW = 120;
        int sidebarY = logoY + logoSize + 32;
        drawCategorySidebar(panelX, sidebarY, sidebarW, panelH - (sidebarY - panelY), mouseX, mouseY);

        // --- MOD AREA (right of sidebar) ---
        int modAreaX = panelX + sidebarW + 8;
        int modAreaY = sidebarY;
        int modAreaW = panelW - sidebarW - 24;
        int modAreaH = panelH - (sidebarY - panelY) - 16;
        drawModArea(modAreaX, modAreaY, modAreaW, modAreaH, mouseX, mouseY);
    }

    private void drawBlurBackground(int width, int height) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        drawRect(0, 0, width, height, new Color(18, 18, 18, 220).getRGB());
        drawRect(0, 0, width, height, new Color(32, 32, 32, 80).getRGB());
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void drawCategorySidebar(int x, int y, int w, int h, int mouseX, int mouseY) {
        int btnHeight = 38, spacing = 8;
        int cy = y + 8;
        for (ModCategory category : ModCategory.values()) {
            boolean selected = category == selectedCategory;
            boolean hovered = mouseX >= x + 8 && mouseX <= x + w - 8 && mouseY >= cy && mouseY <= cy + btnHeight;
            int color = selected ? new Color(80, 180, 255, 240).getRGB() : hovered ? new Color(40, 80, 140, 210).getRGB() : new Color(32, 40, 60, 200).getRGB();
            drawRoundedRect(x + 8, cy, x + w - 8, cy + btnHeight, 10, color);
            drawCenteredString(mc.fontRendererObj, category.name(), x + w / 2, cy + (btnHeight - 8) / 2, selected ? 0xFFFFFF : 0xB0E0FF);
            cy += btnHeight + spacing;
        }
    }

    private void drawModArea(int x, int y, int w, int h, int mouseX, int mouseY) {
        int searchH = SEARCH_HEIGHT;
        drawSearchBar(x, y, mouseX, mouseY, w);
        int cardsY = y + searchH + 12;
        int cardsH = h - searchH - 16;
        drawModCardsClipped(x, cardsY, w, cardsH, mouseX, mouseY);
    }

    private void drawSearchBar(int x, int y, int mouseX, int mouseY, int width) {
        int height = SEARCH_HEIGHT;
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int color = searchBarFocused ? new Color(80, 180, 255, 220).getRGB() : hovered ? new Color(40, 80, 140, 200).getRGB() : new Color(32, 40, 60, 200).getRGB();
        drawRoundedRect(x, y, x + width, y + height, 8, color);
        drawRect(x + 8, y + 6, x + 20, y + 18, new Color(120, 180, 255, 180).getRGB());
        String display = searchText.isEmpty() && !searchBarFocused ? "Search..." : searchText;
        int textColor = searchText.isEmpty() && !searchBarFocused ? 0x7FCFFF : 0xFFFFFF;
        mc.fontRendererObj.drawString(display, x + 28, y + 7, textColor);
        if (searchBarFocused && (searchBarCursorTick / 8) % 2 == 0) {
            int textW = mc.fontRendererObj.getStringWidth(searchText);
            drawRect(x + 28 + textW + 2, y + 7, x + 28 + textW + 4, y + 19, 0xFFB0E0FF);
        }
    }

    private void drawSearchBar(int x, int y, int mouseX, int mouseY) {
        drawSearchBar(x, y, mouseX, mouseY, 320);
    }

    private void drawModCardsClipped(int x, int y, int areaW, int areaH, int mouseX, int mouseY) {
        enableScissor(x, y, areaW, areaH);
        drawModCards(x, y, areaW, areaH, mouseX, mouseY);
        disableScissor();
        if (maxScroll > 0) {
            float scrollBarH = Math.max(32, areaH * (areaH / (float)getContentHeight(areaW)));
            float scrollBarY = y + (areaH - scrollBarH) * (scrollOffset / (float)maxScroll);
            drawRoundedRect(x + areaW - SCROLLBAR_WIDTH, (int)scrollBarY, x + areaW, (int)(scrollBarY + scrollBarH), 4, new Color(60, 120, 200, 180).getRGB());
        }
    }

    private void enableScissor(int x, int y, int w, int h) {
        int scale = (int)mc.gameSettings.guiScale;
        if (scale == 0) scale = 1;
        int factor = new ScaledResolution(mc).getScaleFactor();
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        org.lwjgl.opengl.GL11.glEnable(org.lwjgl.opengl.GL11.GL_SCISSOR_TEST);
        org.lwjgl.opengl.GL11.glScissor(x * factor, (mc.displayHeight - (y + h) * factor), w * factor, h * factor);
    }

    private void disableScissor() {
        org.lwjgl.opengl.GL11.glDisable(org.lwjgl.opengl.GL11.GL_SCISSOR_TEST);
        net.minecraft.client.renderer.GlStateManager.popMatrix();
    }

    private int getContentHeight(int areaW) {
        List<Mod> mods = getFilteredMods();
        int cardsPerRow = Math.min(MAX_CARDS_PER_ROW, Math.max(1, areaW / (MIN_CARD_WIDTH + CARD_MARGIN)));
        int cardWidth = (areaW - (cardsPerRow - 1) * CARD_MARGIN) / cardsPerRow;
        int cardHeight = (int)(cardWidth * 0.85);
        int totalRows = (mods.size() + cardsPerRow - 1) / cardsPerRow;
        return totalRows * (cardHeight + CARD_MARGIN);
    }

    private void drawModCards(int x, int y, int areaW, int areaH, int mouseX, int mouseY) {
        List<Mod> mods = getFilteredMods();
        int cardsPerRow = Math.min(MAX_CARDS_PER_ROW, Math.max(1, areaW / (MIN_CARD_WIDTH + CARD_MARGIN)));
        int cardWidth = (areaW - (cardsPerRow - 1) * CARD_MARGIN) / cardsPerRow;
        int cardHeight = (int)(cardWidth * 0.85);
        int cardIndex = 0;
        int totalRows = (mods.size() + cardsPerRow - 1) / cardsPerRow;
        int contentHeight = totalRows * (cardHeight + CARD_MARGIN);
        maxScroll = Math.max(0, contentHeight - areaH);
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
        if (scrollOffset < 0) scrollOffset = 0;
        int startY = y - scrollOffset;
        hoveredMod = null;
        for (Mod mod : mods) {
            int col = cardIndex % cardsPerRow;
            int row = cardIndex / cardsPerRow;
            int cardX = x + col * (cardWidth + CARD_MARGIN);
            int cardY = startY + row * (cardHeight + CARD_MARGIN);
            boolean hovered = mouseX >= cardX && mouseX <= cardX + cardWidth && mouseY >= cardY && mouseY <= cardY + cardHeight;
            if (hovered) hoveredMod = mod;
            drawModCard(mod, cardX, cardY, cardWidth, cardHeight, hovered, mouseX, mouseY);
            cardIndex++;
        }
    }

    private void drawModCard(Mod mod, int x, int y, int cardWidth, int cardHeight, boolean hovered, int mouseX, int mouseY) {
        int bgColor = hovered ? new Color(40, 80, 140, 240).getRGB() : new Color(32, 40, 60, 220).getRGB();
        int borderColor = hovered ? new Color(80, 180, 255, 180).getRGB() : new Color(60, 80, 120, 180).getRGB();
        drawRoundedRect(x - 2, y - 2, x + cardWidth + 2, y + cardHeight + 2, 16, borderColor);
        drawShadowedRoundedRect(x, y, x + cardWidth, y + cardHeight, 14, bgColor);
        int iconSize = Math.min(36, cardWidth / 3);
        drawRoundedRect(x + cardWidth / 2 - iconSize / 2, y + 10, x + cardWidth / 2 + iconSize / 2, y + 10 + iconSize, 10, new Color(80, 180, 255, 200).getRGB());
        String name = mod.name;
        mc.fontRendererObj.drawString(name, x + cardWidth / 2 - mc.fontRendererObj.getStringWidth(name) / 2, y + 14 + iconSize, 0xFFFFFF);
        int bottomY = y + cardHeight - 32;
        mc.fontRendererObj.drawString("OPTIONS", x + 18, bottomY + 8, 0xB0E0FF);
        drawRect(x + cardWidth - 62, bottomY + 7, x + cardWidth - 50, bottomY + 19, new Color(120, 180, 255, 180).getRGB());
        // --- Toggle Switch (animated, blur, modern style) ---
        boolean enabled = mod.isEnabled();
        boolean animating = (toggledMod == mod && System.currentTimeMillis() - toggleAnimStart < ANIMATION_DURATION);
        float anim = enabled ? 1.0f : 0.0f;
        if (animating) {
            float t = (System.currentTimeMillis() - toggleAnimStart) / (float) ANIMATION_DURATION;
            anim = toggleTargetState ? t : 1 - t;
            anim = Math.max(0, Math.min(1, anim));
        } else if (toggledMod == mod) {
            toggledMod = null;
        }
        int toggleW = Math.max(54, cardWidth / 3);
        int toggleH = 22;
        int toggleX = x + cardWidth - toggleW - 18;
        int toggleY = bottomY + 4;
        // Blur background for toggle
        drawBlurRect(toggleX, toggleY, toggleX + toggleW, toggleY + toggleH, 16, 0.45f);
        // Track color
        int trackColor = blendColor(new Color(60, 220, 180), new Color(120, 120, 120), 1 - anim).getRGB();
        drawRoundedRect(toggleX, toggleY, toggleX + toggleW, toggleY + toggleH, 12, trackColor);
        // Track border
        drawRoundedRect(toggleX, toggleY, toggleX + toggleW, toggleY + toggleH, 12, new Color(0,0,0,40).getRGB());
        // Thumb (circle)
        int thumbSize = toggleH - 6;
        int thumbX = (int) (toggleX + 3 + (toggleW - thumbSize - 6) * anim);
        int thumbY = toggleY + 3;
        int thumbColor = enabled ? new Color(80, 220, 180).getRGB() : new Color(180, 180, 180).getRGB();
        drawCircle(thumbX + thumbSize / 2, thumbY + thumbSize / 2, thumbSize / 2, thumbColor);
        // Text
        String toggleText = enabled ? "ON" : "OFF";
        int textColor = enabled ? 0xFFFFFF : 0xB0E0FF;
        mc.fontRendererObj.drawString(toggleText, toggleX + toggleW / 2 - mc.fontRendererObj.getStringWidth(toggleText) / 2, toggleY + 6, textColor);
    }

    // Hiệu ứng blur cho toggle (fake blur overlay)
    private void drawBlurRect(int left, int top, int right, int bottom, int radius, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int blurColor = new Color(180, 200, 220, (int)(alpha * 80)).getRGB();
        drawRoundedRect(left, top, right, bottom, radius, blurColor);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    // Vẽ hình tròn cho thumb toggle
    private void drawCircle(int cx, int cy, int r, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float a = ((color >> 24) & 0xFF) / 255.0F;
        float red = ((color >> 16) & 0xFF) / 255.0F;
        float green = ((color >> 8) & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        org.lwjgl.opengl.GL11.glColor4f(red, green, blue, a);
        org.lwjgl.opengl.GL11.glBegin(org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN);
        org.lwjgl.opengl.GL11.glVertex2f(cx, cy);
        for (int i = 0; i <= 20; i++) {
            double angle = Math.PI * 2 * i / 20;
            org.lwjgl.opengl.GL11.glVertex2f((float) (cx + Math.cos(angle) * r), (float) (cy + Math.sin(angle) * r));
        }
        org.lwjgl.opengl.GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private List<Mod> getFilteredMods() {
        List<Mod> all = ModInstances.getAllMods();
        List<Mod> filtered = new ArrayList<>();
        for (Mod mod : all) {
            if (mod.getCategory() == selectedCategory && (searchText.isEmpty() || mod.name.toLowerCase().contains(searchText.toLowerCase()))) {
                filtered.add(mod);
            }
        }
        return filtered;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        int panelW = (int)(width * 0.62);
        int panelH = (int)(height * 0.74);
        int panelX = (width - panelW) / 2;
        int panelY = (height - panelH) / 2;
        int sidebarW = 120;
        int logoSize = 56;
        int logoY = panelY + 10;
        int sidebarY = logoY + logoSize + 32;
        int modAreaX = panelX + sidebarW + 8;
        int modAreaY = sidebarY;
        int modAreaW = panelW - sidebarW - 24;
        int modAreaH = panelH - (sidebarY - panelY) - 16;
        // --- Category sidebar click ---
        int btnHeight = 38, spacing = 8;
        int cy = sidebarY + 8;
        for (ModCategory category : ModCategory.values()) {
            if (mouseX >= panelX + 8 && mouseX <= panelX + sidebarW - 8 && mouseY >= cy && mouseY <= cy + btnHeight) {
                selectedCategory = category;
                scrollOffset = 0;
                return;
            }
            cy += btnHeight + spacing;
        }
        // --- Search bar click ---
        int searchY = modAreaY;
        if (mouseX >= modAreaX && mouseX <= modAreaX + modAreaW && mouseY >= searchY && mouseY <= searchY + SEARCH_HEIGHT) {
            searchBarFocused = true;
            return;
        } else {
            searchBarFocused = false;
        }
        // --- Mod cards click ---
        int cardsY = modAreaY + SEARCH_HEIGHT + 12;
        int areaW = modAreaW;
        int areaH = modAreaH - SEARCH_HEIGHT - 16;
        List<Mod> mods = getFilteredMods();
        int cardsPerRow = Math.min(MAX_CARDS_PER_ROW, Math.max(1, areaW / (MIN_CARD_WIDTH + CARD_MARGIN)));
        int cardWidth = (areaW - (cardsPerRow - 1) * CARD_MARGIN) / cardsPerRow;
        int cardHeight = (int)(cardWidth * 0.85);
        int cardIndex = 0;
        int startY = cardsY - scrollOffset;
        for (Mod mod : mods) {
            int col = cardIndex % cardsPerRow;
            int row = cardIndex / cardsPerRow;
            int cardX = modAreaX + col * (cardWidth + CARD_MARGIN);
            int cardY = startY + row * (cardHeight + CARD_MARGIN);
            int toggleW = Math.max(54, cardWidth / 3);
            int toggleH = 20;
            int toggleX = cardX + cardWidth - toggleW - 18;
            int toggleY = cardY + cardHeight - 32 + 4;
            if (mouseX >= toggleX && mouseX <= toggleX + toggleW && mouseY >= toggleY && mouseY <= toggleY + toggleH) {
                toggledMod = mod;
                toggleAnimStart = System.currentTimeMillis();
                toggleTargetState = !mod.isEnabled();
                mod.setEnabled(toggleTargetState);
                return;
            }
            int gearX = cardX + cardWidth - 62, gearY = cardY + cardHeight - 32 + 7;
            if (mouseX >= gearX && mouseX <= gearX + 12 && mouseY >= gearY && mouseY <= gearY + 12) {
                return;
            }
            if (mouseX >= cardX && mouseX <= cardX + cardWidth && mouseY >= cardY && mouseY <= cardY + cardHeight) {
                return;
            }
            cardIndex++;
        }
        // --- Scrollbar drag (mod area) ---
        if (maxScroll > 0) {
            int scrollBarX = modAreaX + areaW - SCROLLBAR_WIDTH;
            float scrollBarH = Math.max(32, areaH * (areaH / (float) (Math.max(1, ((mods.size() + cardsPerRow - 1) / cardsPerRow) * (cardHeight + CARD_MARGIN)))));
            float scrollable = areaH - scrollBarH;
            if (scrollable > 0) {
                float percent = (mouseY - dragStartY) / scrollable;
                scrollOffset = Math.max(0, Math.min(maxScroll, scrollStartOffset + (int) (percent * maxScroll)));
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        isDraggingScroll = false;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (isDraggingScroll && maxScroll > 0) {
            int panelH = new ScaledResolution(mc).getScaledHeight() - TOP_MARGIN * 2;
            int cardsY = TOP_MARGIN + CATEGORY_HEIGHT + 10 + SEARCH_HEIGHT + 16;
            int areaH = panelH - (cardsY - TOP_MARGIN) - 12;
            int deltaY = mouseY - dragStartY;
            float scrollBarH = Math.max(32, areaH * (areaH / (float) (maxScroll + areaH)));
            float scrollable = areaH - scrollBarH;
            if (scrollable > 0) {
                float percent = deltaY / scrollable;
                scrollOffset = Math.max(0, Math.min(maxScroll, scrollStartOffset + (int) (percent * maxScroll)));
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dWheel = Mouse.getEventDWheel();
        if (dWheel != 0 && maxScroll > 0) {
            scrollOffset -= dWheel / 6;
            if (scrollOffset < 0) scrollOffset = 0;
            if (scrollOffset > maxScroll) scrollOffset = maxScroll;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (searchBarFocused) {
            if (keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE) {
                if (!searchText.isEmpty()) searchText = searchText.substring(0, searchText.length() - 1);
            } else if (keyCode == Keyboard.KEY_ESCAPE) {
                searchBarFocused = false;
            } else if (keyCode == Keyboard.KEY_RETURN) {
                searchBarFocused = false;
            } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                searchText += typedChar;
            }
        } else if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

    private void drawRoundedRect(int left, int top, int right, int bottom, int radius, int color) {
        drawRect(left, top, right, bottom, color);
    }

    private void drawShadowedRoundedRect(int left, int top, int right, int bottom, int radius, int color) {
        drawRoundedRect(left + 2, top + 2, right + 2, bottom + 2, radius, new Color(0, 0, 0, 80).getRGB());
        drawRoundedRect(left, top, right, bottom, radius, color);
    }

    private Color blendColor(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() * (1 - t) + c2.getRed() * t);
        int g = (int) (c1.getGreen() * (1 - t) + c2.getGreen() * t);
        int b = (int) (c1.getBlue() * (1 - t) + c2.getBlue() * t);
        int a = (int) (c1.getAlpha() * (1 - t) + c2.getAlpha() * t);
        return new Color(r, g, b, a);
    }
}