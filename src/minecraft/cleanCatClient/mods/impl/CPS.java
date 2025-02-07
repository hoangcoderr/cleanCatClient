package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.gui.hud.ScreenPosition;

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

    public CPS() {
        super(ModConstants.CPS, ModConstants.CPS_DESC, ModCategory.RENDER);
    }

    @Override
    public void render(ScreenPosition pos) {
        leftButton.updateCPS();
        rightButton.updateCPS();

       FontUtil.normal.drawStringWithShadow(leftButton.getCPS() + " | " + rightButton.getCPS() + " CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawStringWithShadow("10 | 20 CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    @Override
    public int getWidth() {
        return (int)FontUtil.normal.getStringWidth(leftButton.getCPS() + " | " + rightButton.getCPS() + " CPS");
    }

    @Override
    public int getHeight() {
        return FontUtil.normal.getHeight();
    }
}
