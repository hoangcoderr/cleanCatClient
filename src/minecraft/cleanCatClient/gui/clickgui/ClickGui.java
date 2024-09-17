package cleanCatClient.gui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cleanCatClient.gui.clickgui.comp.SettingsModButton;
import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.gui.clickgui.settings.ModSettingsInstance;
import cleanCatClient.gui.clickgui.settings.impl.CustomCrossHairSettings;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.Mod;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cleanCatClient.gui.clickgui.comp.CategoryManager;
import cleanCatClient.gui.clickgui.comp.ClickGuiCategoryButton;
import cleanCatClient.gui.clickgui.comp.ModButton;
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

    @Override
    public void initGui() {
        sr = new ScaledResolution(mc);
        centerW = sr.getScaledWidth() / 2;
        centerH = sr.getScaledHeight() / 2;
        reset();

        backgroundW = 250;
        int backgroundH = 250;

        this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 85, 120, 25, "Player", 0));
        this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 55, 120, 25, "World", 1));
        this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH - 25, 120, 25, "Render", 2));
        this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH + 5, 120, 25, "Util", 3));
        this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 250, centerH + 35, 120, 25, "HudManager", 4));

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
        addSettingsModButton(ModInstances.getFullBright(), 0, 340,20, spaceBetween);
        addSettingsModButton(ModInstances.getMinimalViewBobbing(), 1, 340,20, spaceBetween);
        addSettingsModButton(ModInstances.getLazyChunkLoading(), 2, 340,20, spaceBetween);
    }

    private void addModButton(int categoryID, Mod mod, int index, int size, int spaceBetween, int buttonsPerRow, ModSettings settings) {
        int row = index / buttonsPerRow;
        int col = index % buttonsPerRow;
        int x = centerW + col * (size + spaceBetween);
        int y = centerH + row * (size + spaceBetween);

        this.modButtonToRender.add(new ModButton(x, y, size, size, mod, categoryID, settings));
    }

    // Add this method to the ClickGui class
    private void addSettingsModButton(Mod mod, int index, int width, int height, int spaceBetween) {
        int y = centerH - 90 + index * (height + spaceBetween);
        int x = centerW - backgroundW + 130; // Adjust x position to avoid overlapping with category buttons

        this.settingsModButton.add(new SettingsModButton(x, y, width, height, mod));
    }
    private int currentScroll = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        FontUtil.getFontRenderer(30).drawStringWithShadow("cleanCat Client", centerW - backgroundW + 10, centerH - 125 + 10, new Color(255, 255, 255, 255).getRGB());
        centerW = sr.getScaledWidth() / 2;
        centerH = sr.getScaledHeight() / 2;

        Gui.drawRoundedRect(centerW - backgroundW, centerH - 125, centerW + backgroundW, centerH + 125, 8, new Color(77, 76, 76, 97).getRGB());
        Gui.drawRoundedRect(centerW - backgroundW + 480, centerH - 125, centerW + backgroundW, centerH + 125, 8, new Color(24, 23, 23, 192).getRGB());

        // Draw "cleanCat" at the top-left corner

        for (ClickGuiCategoryButton clickGuiCategoryButton : clickGuiCategoryButton) {
            clickGuiCategoryButton.renderButton();
        }

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
                modButton.y = modButton.originalY - currentScroll;
                modButton.render(mouseX, mouseY); // Pass mouse coordinates here
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }
        if (CategoryManager.currentPage == 3) {
            for (SettingsModButton settingsModButton : settingsModButton) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                this.glScissor(centerW - backgroundW, centerH - 115, centerW + backgroundW, 230);
                settingsModButton.render(mouseX, mouseY);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }

        drawScrollbar();
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
            for (SettingsModButton settingsModButton : settingsModButton) {
                settingsModButton.onClick(mouseX, mouseY, mouseButton);
            }
        }
        for (ClickGuiCategoryButton clickGuiCategoryButton : clickGuiCategoryButton) {
            clickGuiCategoryButton.onClick(mouseX, mouseY, mouseButton);
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

}