package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.gui.hud.ScreenPosition;

import java.awt.*;
import java.util.ArrayList;

public class CPS extends ModDraggable {
    public static class MouseButton {
        private final int buttonCode;
        private final String name;
        private final ArrayList<Long> clicks = new ArrayList<>();
        private long lastPressed;
        private boolean wasPressed;

        public MouseButton(int buttonCode, String name) {
            this.buttonCode = buttonCode;
            this.name = name;
        }

        public int getCPS() {
            final long time = System.currentTimeMillis();
            this.clicks.removeIf(aLong -> aLong + 1000 < time);
            return this.clicks.size();
        }

        public void updateCPS() {
            final boolean pressed = org.lwjgl.input.Mouse.isButtonDown(buttonCode);
            if (pressed != this.wasPressed) {
                this.wasPressed = pressed;
                if (pressed) {
                    this.clicks.add(this.lastPressed = System.currentTimeMillis());
                }
            }
        }

        public String getName() {
            return name;
        }
    }

    private final MouseButton leftButton = new MouseButton(0, "LMB");
    private final MouseButton rightButton = new MouseButton(1, "RMB");
    private Color color = new Color(255, 255, 255, 255);

    public CPS() {
        super(ModConstants.CPS, ModConstants.CPS_DESC, ModCategory.RENDER);
        loadConfig();
    }

    @Override
    public void render(ScreenPosition pos) {
        leftButton.updateCPS();
        rightButton.updateCPS();

        mc.fontRendererObj.drawStringWithShadow(leftButton.getCPS() + " | " + rightButton.getCPS() + " CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), color.getRGB());
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        mc.fontRendererObj.drawStringWithShadow("10 | 20 CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), color.getRGB());
    }

    @Override
    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(leftButton.getCPS() + " | " + rightButton.getCPS() + " CPS");
    }

    @Override
    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        saveConfig();
    }

    public void loadConfig() {
        // Load color from config (example implementation)
        String colorStr[] = loadDataConfig();
        if (colorStr != null) {
            try {
                this.color = new Color(Integer.parseInt(colorStr[0]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveConfig() {
        // Save color to config (example implementation)
        saveDataConfig(new String[] {Integer.toString(this.color.getRGB())});
    }
}