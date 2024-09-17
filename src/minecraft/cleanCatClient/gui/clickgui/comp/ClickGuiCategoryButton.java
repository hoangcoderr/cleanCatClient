package cleanCatClient.gui.clickgui.comp;

import java.awt.Color;

import cleanCatClient.Client;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.utils.animation.AnimationEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGuiCategoryButton extends CategoryManager{
	public int x,y,w,h,r;
	private String name;
	private boolean isOnThisPage = false;
	//this is use for the categorymanager toknow which page this is on
	private int number = 0;
	CategoryManager categoryManager;
	
	private AnimationEngine animation = new AnimationEngine(x, x+w, 500,false);
	
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
		// Ensure the correct color is set before rendering
		//GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f); // Reset color to white

		if (animation.getAnimationValue() >= x + 1) {
			Gui.drawRoundedRect(x, y, animation.getAnimationValue(), y + h, 5, new Color(191, 226, 246, 255).getRGB());
			FontUtil.normal.drawString(name, x + w / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) / 2,
					y + h / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, new Color(20, 23, 34, 255).getRGB());
		} else {
			FontUtil.normal.drawString(name, x + w / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) / 2,
					y + h / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, new Color(20, 23, 34, 255).getRGB());
		}

		if (CategoryManager.currentPage == number) {
			isOnThisPage = true;
			animation.setIsDrawAnimation(true);
		} else if (!isOnThisPage) {
			animation.resetUsingBackWardsAnimation();
		}
	}


	public void onClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			animation.setIsDrawAnimation(true);
			this.isOnThisPage = true;
			if (number == 4){
				Client.hudManager.openConfigScreen();
			}
			else
				CategoryManager.thisPage(number);
		}
	}
	
	public String getName() {
		return name;
	}
	public boolean isOnThisPage() {
		return isOnThisPage;

	}
	
	public void setIsOnThisPage(boolean value) {
		this.isOnThisPage = value;
		
	}
	
	

}
