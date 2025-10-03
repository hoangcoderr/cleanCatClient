package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.Client;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.utils.animation.AnimationEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ClickGuiCategoryButton extends CategoryManager{
	public int x,y,w,h,r;
	private final String name;
	private boolean isOnThisPage = false;
	private int number = 0;

	private final AnimationEngine animation = new AnimationEngine(x, x+w, 500,false);
	
	// Modern animation variables
	private float hoverAnimation = 0.0f;
	private float selectAnimation = 0.0f;
	private long lastUpdateTime = System.currentTimeMillis();
	private float glowPulse = 0.0f;
	
	public ClickGuiCategoryButton(int x, int y, int w, int h, String name, int number) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
		this.number = number;
		animation.AnimationUpdateValue(x, x+w, 500,false);
	}
	
	public int getNumber() {
		return number;
	}

	public void renderButton() {
		// Update animations
		updateAnimations();
		
		boolean isSelected = CategoryManager.currentPage == number;
		
		GL11.glPushMatrix();
		
		// Draw modern button
		drawModernButton(isSelected);
		
		// Draw icon (optional - you can add icons later)
		// drawCategoryIcon();
		
		// Draw text with animation
		drawAnimatedText(isSelected);
		
		GL11.glPopMatrix();
		
		// Update old animation system
		if (isSelected) {
			isOnThisPage = true;
			animation.setIsDrawAnimation(true);
		} else if (!isOnThisPage) {
			animation.resetUsingBackWardsAnimation();
		}
	}
	
	private void updateAnimations() {
		long currentTime = System.currentTimeMillis();
		float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
		lastUpdateTime = currentTime;
		
		boolean isSelected = CategoryManager.currentPage == number;
		
		// Select animation
		float targetSelect = isSelected ? 1.0f : 0.0f;
		if (selectAnimation < targetSelect) {
			selectAnimation += deltaTime * 4.0f;
			if (selectAnimation > targetSelect) selectAnimation = targetSelect;
		} else if (selectAnimation > targetSelect) {
			selectAnimation -= deltaTime * 4.0f;
			if (selectAnimation < targetSelect) selectAnimation = targetSelect;
		}
		
		// Glow pulse for selected button
		if (isSelected) {
			glowPulse += deltaTime * 2.0f;
		}
	}
	
	private void drawModernButton(boolean isSelected) {
		// Animated glow effect for selected button
		if (isSelected && selectAnimation > 0.3f) {
			float pulse = (float)(Math.sin(glowPulse) * 0.3 + 0.7);
			int glowAlpha = (int)(selectAnimation * pulse * 80);
			drawGlowEffect(x - 3, y - 3, x + w + 3, y + h + 3, 8, 
				new Color(100, 180, 255, glowAlpha));
		}
		
		// Background color based on selection
		Color bgColor;
		if (isSelected) {
			// Gradient for selected state
			int alpha = (int)(200 + selectAnimation * 55);
			bgColor = new Color(50, 70, 100, alpha);
		} else {
			bgColor = new Color(30, 30, 45, 180);
		}
		
		// Draw main background
		Gui.drawRoundedRect(x, y, x + w, y + h, 8, bgColor.getRGB());
		
		// Draw gradient overlay for selected state
		if (isSelected) {
			drawGradientRoundedRect(x, y, x + w, y + h, 8,
				new Color(70, 100, 140, (int)(selectAnimation * 150)).getRGB(),
				new Color(40, 60, 90, (int)(selectAnimation * 100)).getRGB());
		}
		
		// Animated selection indicator (left side)
		if (selectAnimation > 0.01f) {
			int indicatorWidth = 3;
			int indicatorHeight = (int)(h * selectAnimation);
			int indicatorY = y + (h - indicatorHeight) / 2;
			
			Color indicatorColor = new Color(100, 200, 255, (int)(selectAnimation * 255));
			Gui.drawRect(x, indicatorY, x + indicatorWidth, indicatorY + indicatorHeight, 
				indicatorColor.getRGB());
		}
		
		// Border
		int borderAlpha = isSelected ? 150 : 80;
		Color borderColor = isSelected ?
			new Color(100, 180, 255, borderAlpha) :
			new Color(60, 60, 80, borderAlpha);
		drawRoundedBorder(x, y, x + w, y + h, 8, 1.0f, borderColor);
	}
	
	private void drawAnimatedText(boolean isSelected) {
		// Text color with animation
		int baseAlpha = isSelected ? 255 : 200;
		int textAlpha = Math.min(255, (int)(baseAlpha + selectAnimation * 55));
		
		Color textColor = isSelected ?
			new Color(200, 230, 255, textAlpha) :
			new Color(180, 180, 200, textAlpha);
		
		// Calculate centered position
		int textWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(name);
		int textX = x + w / 2 - textWidth / 2;
		int textY = y + h / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2;
		
		// Draw text with shadow for selected state
		if (isSelected) {
			FontUtil.normal.drawStringWithShadow(name, textX, textY, textColor.getRGB());
		} else {
			FontUtil.normal.drawString(name, textX, textY, textColor.getRGB());
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

	public void onClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			animation.setIsDrawAnimation(true);
			this.isOnThisPage = true;
			if (number == 4){
				HUDManager hudManager = HUDManager.getInstance();
				Client.hudManager.openConfigScreen(hudManager);
			}
			else{
				CategoryManager.thisPage(number);
				((ClickGui) Minecraft.getMinecraft().currentScreen).resetScroll();
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public void setIsOnThisPage(boolean value) {
		this.isOnThisPage = value;
	}
}
