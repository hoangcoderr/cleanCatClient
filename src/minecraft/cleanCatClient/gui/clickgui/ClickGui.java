package cleanCatClient.gui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cleanCatClient.gui.clickgui.comp.CategoryManager;
import cleanCatClient.gui.clickgui.comp.ClickGuiCategoryButton;
import cleanCatClient.gui.clickgui.comp.ModButton;
import cleanCatClient.gui.clickgui.modSetting.ModSettingManager;
import cleanCatClient.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGui extends GuiScreen{

	public static ArrayList<ClickGuiCategoryButton> clickGuiCategoryButton = new ArrayList<>();
	
	public static ArrayList<ModButton> modButtonToRender = new ArrayList<>();
	
	ScaledResolution sr;
	private ModSettingManager msManager;
	
	int backgroundW = 200;
	int centerW;
	int centerH;
	
	
	@Override
	public void initGui() {
		//Enable Minecrafts blur shader
		//mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
		
		sr = new ScaledResolution(mc);
		centerW = sr.getScaledWidth()/2;
		centerH = sr.getScaledHeight()/2;
		reset();
		this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 65,100,20,  "Player",0));
		this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 45,100,20,  "World",1));
		this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 25,100,20,  "Render",2));
		this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 5,100,20,  "Util",3));
		this.clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH + 15,100,20,  "HudManager",4));
			
		int modButtonW = 260;
		int modButtonH = 25;
		int spaceBetween = 26;

		//player
		this.modButtonToRender.add(new ModButton(centerW,centerH, modButtonW, modButtonH, ModInstances.getToggleSprint(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 1*spaceBetween, modButtonW, modButtonH, ModInstances.getKeystrokes(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 2*spaceBetween, modButtonW, modButtonH, ModInstances.getArmorStatus(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 3*spaceBetween, modButtonW, modButtonH, ModInstances.getFPS(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 4*spaceBetween, modButtonW, modButtonH, ModInstances.getPerspective(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 5*spaceBetween, modButtonW, modButtonH, ModInstances.getNoHurtCam(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 6*spaceBetween, modButtonW, modButtonH, ModInstances.getSwingAnimation(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 7*spaceBetween, modButtonW, modButtonH, ModInstances.getSmoothSneaking(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 8*spaceBetween, modButtonW, modButtonH, ModInstances.getLeftHand(),0));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 9*spaceBetween, modButtonW, modButtonH, ModInstances.getPotionStatus(),0));
		//world
		//this.modButtonToRender.add(new ModButton(centerW,centerH, modButtonW, modButtonH, ModInstances.getModXYZ(),1));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 1*spaceBetween, modButtonW, modButtonH, ModInstances.getLazyChunkLoading(),1));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 2*spaceBetween, modButtonW, modButtonH, ModInstances.getDisableBlockParticles(),1));
		//render
		this.modButtonToRender.add(new ModButton(centerW,centerH, modButtonW, modButtonH, ModInstances.getFullBright(),2));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 1*spaceBetween, modButtonW, modButtonH, ModInstances.getBlockOverlay(),2));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 2*spaceBetween, modButtonW, modButtonH, ModInstances.getGlintColor(),2));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 3*spaceBetween, modButtonW, modButtonH, ModInstances.getScoreboard(),2));
		this.modButtonToRender.add(new ModButton(centerW,centerH + 4*spaceBetween, modButtonW, modButtonH, ModInstances.getCustomCrosshair(),2));
		//this.modButtonToRender.add(new ModButton(centerW,centerH, modButtonW, modButtonH, ModInstances.getModHelloWorld(),2));
		//this.modButtonToRender.add(new ModButton(centerW,centerH + 1*spaceBetween, modButtonW, modButtonH, ModInstances.getModAnimations(),2));
		//this.modButtonToRender.add(new ModButton(centerW,centerH + 2*spaceBetween, modButtonW, modButtonH, ModInstances.getModMotionBlur(),2));
		
		//util
		//this.modButtonToRender.add(new ModButton(centerW,centerH, modButtonW, modButtonH, ModInstances.getModTargetHUD(),3));
		
		msManager = new ModSettingManager(centerW,centerH);
	}
	@Override
    public void onGuiClosed() {
        //Disable Minecrafts blur shader
		mc.entityRenderer.loadEntityShader(null);
        super.onGuiClosed();
        
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		//update center w
		centerW = sr.getScaledWidth()/2;
		centerH = sr.getScaledHeight()/2;
		
		GlStateManager.pushAttrib(); //idk why i do this i sohuld learn gl
		GlStateManager.pushMatrix();
		Gui.drawRoundedRect(centerW - backgroundW, centerH - 100, centerW + backgroundW, centerH + 100, 8, new Color(77, 76, 76, 218).getRGB());
		Gui.drawRoundedRect(centerW - backgroundW + 390, centerH - 100, centerW + backgroundW, centerH + 100, 8, new Color(24, 23, 23, 192).getRGB());
		GlStateManager.popMatrix();
		msManager.render();
		
		for(ClickGuiCategoryButton clickGuiCategoryButton :clickGuiCategoryButton) {
			clickGuiCategoryButton.renderButton();
		}

		
		int wheel = Mouse.getDWheel();
        for (ModButton modButton : modButtonToRender) {
        	if(modButton.id == CategoryManager.currentPage) { 
	            GL11.glEnable(GL11.GL_SCISSOR_TEST);
	            this.glScissor(centerW - backgroundW, centerH - 90, centerW + backgroundW, 180);
	            modButton.render();
	            if (wheel < 0) {
	            	modButton.y -= 8;
	            } else if (wheel > 0) {
	            	modButton.y += 8;
	            }
	            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        	}
        }
		
		//idk why i did this i shuold learn gl
        GlStateManager.popAttrib();
		//System.out.println("2");
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(mouseX >= (centerW - backgroundW) && mouseX <= (centerW + backgroundW) && mouseY >= (centerH - 90) && mouseY <= (centerH + 90)) {
			for(ModButton modButton : modButtonToRender) {

				if(modButton.id == CategoryManager.currentPage) {
					modButton.onClick(mouseX, mouseY, mouseButton);
				}
			}
		}
		
		for(ClickGuiCategoryButton clickGuiCategoryButton :clickGuiCategoryButton) {

			clickGuiCategoryButton.onClick(mouseX, mouseY, mouseButton);
		}
		
	}
	public static ArrayList<ClickGuiCategoryButton> getClickGuiCategoryButton() {
		return clickGuiCategoryButton;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub
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
	
	