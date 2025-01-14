package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class Keystrokes extends ModDraggable {


    private Color downColor = new Color(0, 0, 0, 26);
    private Color upColor = new Color(12, 12, 12, 6);
    private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18);
    private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18);
    private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18);
    private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18);
    private static final Key SPACE = new Key("Space", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 41, 58, 18);
    private static final MouseKey LMB = new MouseKey("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 61, 28, 18);
    private static final MouseKey RMB = new MouseKey("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 61, 28, 18);

    public static enum KeystrokesMode {
        WASD(new Key[]{W, A, S, D}, new MouseKey[]{}),
        WASD_MOUSE(new Key[]{W, A, S, D}, new MouseKey[]{new MouseKey("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18), new MouseKey("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18)}),
        WASD_JUMP(new Key[]{W, A, S, D, SPACE}, new MouseKey[]{}),
        WASD_JUMP_MOUSE(new Key[]{W, A, S, D, SPACE}, new MouseKey[]{LMB, RMB});

        private final Key[] keys;
        private final MouseKey[] mouseKeys;
        private int width;
        private int height;

        private KeystrokesMode(Key[] keys, MouseKey[] mouseKeys) {
            this.keys = keys;
            this.mouseKeys = mouseKeys;

            for (Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
            for (MouseKey key : mouseKeys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }



    private static class Key {
        private final String name;
        private final KeyBinding keyBinding;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        public Key(String name, KeyBinding keyBinding, int x, int y, int width, int height) {
            this.name = name;
            this.keyBinding = keyBinding;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean isDown() {
            return keyBinding.isKeyDown();
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getName() {
            return name;
        }
    }

    private static class MouseKey extends Key {
        private int CPS;
        private ArrayList<Long> clicks = new ArrayList<Long>();
        private long lastPressed;
        private boolean wasPressed;

        public MouseKey(String name, KeyBinding keyBinding, int x, int y, int width, int height) {
            super(name, keyBinding, x, y, width, height);
        }

        public int getCPS() {
            final long time = System.currentTimeMillis();
            this.clicks.removeIf(aLong -> aLong + 1000 < time);
            return this.clicks.size();
        }

        public void updateCPS() {
            final boolean pressed = Mouse.isButtonDown(getName().equals("LMB") ? 0 : 1);
            if (pressed != this.wasPressed) {
                this.wasPressed = pressed;
                if (pressed) {
                    this.clicks.add(this.lastPressed = System.currentTimeMillis());
                }
            }
        }

    }

    private KeystrokesMode mode = KeystrokesMode.WASD_JUMP_MOUSE;

    public void setMode(KeystrokesMode mode) {
        this.mode = mode;
        saveConfig();
    }

    public Keystrokes() {
        super(ModConstants.KEYSTROKES, ModConstants.KEYSTROKES_DESC, ModCategory.RENDER);
        loadConfig();
    }

    public Color getDownColor() {
        return downColor;
    }

    public void setDownColor(Color downColor) {
        this.downColor = downColor;
    }

    public Color getUpColor() {
        return upColor;
    }

    public void setUpColor(Color upColor) {
        this.upColor = upColor;
    }

    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.mode = KeystrokesMode.valueOf(dataConfig[0]);
            this.upColor = new Color(Integer.parseInt(dataConfig[1]));
            this.downColor = new Color(Integer.parseInt(dataConfig[2]));
        } catch (Exception e) {
            e.printStackTrace();
            this.mode = KeystrokesMode.WASD_JUMP_MOUSE;
            this.upColor = new Color(12, 12, 12, 6);
            this.downColor = new Color(0, 0, 0, 26);
        }
    }

    public void saveConfig() {
        String[] dataConfig = new String[3];
        dataConfig[0] = this.mode.name();
        dataConfig[1] = String.valueOf(this.upColor.getRGB());
        dataConfig[2] = String.valueOf(this.downColor.getRGB());
        saveDataConfig(dataConfig);
    }

    @Override
    public int getWidth() {
        return mode.getWidth();
    }

    @Override
    public int getHeight() {
        return mode.getHeight();
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public void setHeight(int height) {
        this.height = height;
    }
    private void renderKeys(ScreenPosition pos, Key[] keys) {
        for (Key key : keys) {
            int textWidth = font.getStringWidth(key.getName());
            Gui.drawRect(pos.getAbsoluteX() + key.getX(),
                    pos.getAbsoluteY() + key.getY(),
                    pos.getAbsoluteX() + key.getX() + key.getWidth(),
                    pos.getAbsoluteY() + key.getY() + key.getHeight(),
                    !key.isDown() ? upColor.getRGB() : downColor.getRGB());
//            FontUtil.normal.drawString(
//                    key.getName(),
//                    pos.getAbsoluteX() + key.getX() + key.getWidth() / 2 - textWidth / 2,
//                    pos.getAbsoluteY() + key.getY() + (key.getHeight() - 8) / 2,
//                    key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
//            );
            mc.fontRendererObj.drawStringWithShadow(
                    key.getName(),
                    pos.getAbsoluteX() + key.getX() + key.getWidth() / 2 - textWidth / 2,
                    pos.getAbsoluteY() + key.getY() + (key.getHeight() - 8) / 2,
                    key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
            );
        }
    }

    private void renderMouseKeys(ScreenPosition pos, MouseKey[] mouseKeys) {
        for (MouseKey key : mouseKeys) {
            key.updateCPS();
            int nameWidth = font.getStringWidth(key.getName());
            int cpsWidth = font.getStringWidth("" + key.getCPS());

            // Draw the background rectangle
            Gui.drawRect(pos.getAbsoluteX() + key.getX(),
                    pos.getAbsoluteY() + key.getY(),
                    pos.getAbsoluteX() + key.getX() + key.getWidth(),
                    pos.getAbsoluteY() + key.getY() + key.getHeight(),
                    !key.isDown() ? upColor.getRGB() : downColor.getRGB());

            // Draw the name of the mouse button (larger font)
            mc.fontRendererObj.drawStringWithShadow(
                    key.getName(),
                    pos.getAbsoluteX() + key.getX() + key.getWidth() / 2 - nameWidth / 2,
                    pos.getAbsoluteY() + key.getY() + 2, // Adjust the Y position as needed
                    key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
            );

            // Draw the CPS value (smaller font)
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.8, 0.8, 0.8); // Scale down to 50%
            mc.fontRendererObj.drawStringWithShadow(
                    "" + key.getCPS(),
                    (pos.getAbsoluteX() + key.getX() + key.getWidth() / 2 - cpsWidth / 2) / 0.8f,
                    (pos.getAbsoluteY() + key.getY() + key.getHeight() - 5) / 0.8f, // Adjust the Y position as needed
                    key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
            );
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void render(ScreenPosition pos) {

        renderKeys(pos, mode.keys);
        renderMouseKeys(pos, mode.mouseKeys);
    }
}