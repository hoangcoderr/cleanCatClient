package cleanCatClient.mods.impl;

import cleanCatClient.event.EventTarget;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.minecraft.client.shader.ShaderGroup;

public class ColorSaturation extends ModDraggable {
    public float s = 1.0F;

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled && isShaderActive() && mc.theWorld != null) {
            s = 1.0F;
            reloadSaturation();
        }
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public ColorSaturation() {
        super(ModConstants.COLOR_SATURATION, ModConstants.COLOR_SATURATION_DESC, ModCategory.RENDER);
        loadConfig();
    }

    private static boolean lastEnabled = false;

    private static final ResourceLocation phosphorBlur = new ResourceLocation("minecraft:shaders/post/color_convolve.json");

    @Override
    public int getWidth() {
        return -1;
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.s = Float.parseFloat(dataConfig[0]);
            //setSaturation(this.s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.s = 1.0F;
        }
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.s);
        saveDataConfig(dataConfig);
    }


    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public void render(ScreenPosition pos) {
        if (!isShaderActive() || lastEnabled != isEnabled()) {
            lastEnabled = isEnabled();
            reloadShader();
        }
    }

    public void setSaturation(float saturation) {
        s = saturation;
        reloadSaturation();
        saveConfig();
    }

    public void reloadShader() {
        if (Minecraft.getMinecraft().theWorld == null) {
            return;
        }

        if (!isShaderActive() && isEnabled()) {
            try {
                final ShaderGroup saturationShader = new ShaderGroup(Minecraft.getMinecraft().getTextureManager(), Minecraft.getMinecraft().getResourceManager(), Minecraft.getMinecraft().getFramebuffer(), phosphorBlur);
                saturationShader.createBindFramebuffers(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                Minecraft.getMinecraft().entityRenderer.colorSaturation$setSaturationShader(saturationShader);
                reloadSaturation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isShaderActive() && !isEnabled()) {
            EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;
            if (entityRenderer.colorSaturation$getSaturationShader() != null) {
                entityRenderer.colorSaturation$getSaturationShader().deleteShaderGroup();
            }

            entityRenderer.colorSaturation$setSaturationShader(null);
        }
    }

    public void reloadSaturation() {
        try {
            final List<Shader> listShaders = (Minecraft.getMinecraft().entityRenderer.colorSaturation$getSaturationShader()).getListShaders();

            if (listShaders == null) {
                return;
            }

            for (Shader shader : listShaders) {
                ShaderUniform su = shader.getShaderManager().getShaderUniform("Saturation");

                if (su == null) {
                    continue;
                }

                su.set(s);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isShaderActive() {
        return Minecraft.getMinecraft().entityRenderer.colorSaturation$getSaturationShader() != null && net.minecraft.client.renderer.OpenGlHelper.shadersSupported;
    }
}