package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.event.impl.KeyEvent;
import cleanCatClient.event.impl.Render2D;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.security.Key;

public class Perspective extends Mod {
    private boolean returnOnRelease = true; // hold down the key = true
    public boolean perspectiveToggled = false;

    private float cameraYaw = 0.0F;
    private float cameraPitch = 0.0F;

    private int previousPerspective = 0; // pref f5 state
    long startTime;
    long duration = 1000; // 1 second
    float initialFov = 10.0F;
    private boolean isSmooth = false;

    public Perspective() {
        super(ModConstants.PERSPECTIVE, ModConstants.PERSPECTIVE_DESC, ModCategory.PLAYER);
        loadConfig();
    }

    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.isSmooth = Boolean.parseBoolean(dataConfig[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.keyBind = Keyboard.KEY_LCONTROL;
            this.returnOnRelease = true;
            this.isSmooth = false;
        }
    }

    public void saveConfig() {
        String[] dataConfig = new String[3];
        dataConfig[0] = String.valueOf(this.isSmooth);
        saveDataConfig(dataConfig);
    }

    public boolean isSmooth() {
        return isSmooth;
    }

    public void setSmooth(boolean smooth) {
        isSmooth = smooth;
        saveConfig();
    }
    private float targetFov = mc.gameSettings.fovSetting;

    private float currentFov;

    @EventTarget
    public void keyboardEvent(KeyEvent e) {
        if (e.getKey() == keyBind) {
            if (Keyboard.getEventKeyState()) {

                if (!perspectiveToggled) {
                    perspectiveToggled = true;
                    cameraYaw = mc.thePlayer.rotationYaw;
                    cameraPitch = mc.thePlayer.rotationPitch;
                    previousPerspective = mc.gameSettings.thirdPersonView;
                    mc.gameSettings.thirdPersonView = 1;
                    targetFov = mc.gameSettings.fovSetting;
                    currentFov = 30.0F;
                    mc.gameSettings.fovSetting = currentFov;
                } else if (!returnOnRelease) {
                    perspectiveToggled = false;
                    mc.gameSettings.thirdPersonView = previousPerspective;
                    mc.gameSettings.fovSetting = targetFov;
                }
            } else if (returnOnRelease) {
                perspectiveToggled = false;
                mc.gameSettings.thirdPersonView = previousPerspective;
                mc.gameSettings.fovSetting = targetFov;
            }
        }
        if (e.getKey() == mc.gameSettings.keyBindTogglePerspective.getKeyCode()) {
            perspectiveToggled = false;
            mc.gameSettings.fovSetting = targetFov;
        }
    }

    @EventTarget
    public void onClientTick(Render2D event) {
        if (perspectiveToggled && currentFov < targetFov && isSmooth) {
            currentFov += 3.0F;
            if (currentFov >= targetFov) {
                currentFov = targetFov;
            }

            mc.gameSettings.fovSetting = currentFov;
        }
        else if (!isSmooth){
            mc.gameSettings.fovSetting = targetFov;
        }
    }
//    @EventTarget
//    public void keyboardEvent(ClientTickEvent e) {
//        if (Keyboard.isKeyDown( mc.gameSettings.CLIENT_PERSPECTIVE.getKeyCode())) {
//            if (Keyboard.getEventKeyState()) {
//                if (!perspectiveToggled) {
//                    perspectiveToggled = true;
//                    cameraYaw = mc.thePlayer.rotationYaw;
//                    cameraPitch = mc.thePlayer.rotationPitch;
//                    previousPerspective = mc.gameSettings.thirdPersonView;
//                    mc.gameSettings.thirdPersonView = 1;
//                } else if (!returnOnRelease) {
//                    perspectiveToggled = false;
//                    mc.gameSettings.thirdPersonView = previousPerspective;
//                }
//            } else if (returnOnRelease) {
//                perspectiveToggled = false;
//                mc.gameSettings.thirdPersonView = previousPerspective;
//            }
//        }
//        if (Keyboard.isKeyDown( mc.gameSettings.CLIENT_PERSPECTIVE.getKeyCode())) {
//            perspectiveToggled = false;
//        }
//    }

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : mc.thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : mc.thePlayer.rotationPitch;
    }

    public float getCameraYawForTag() {
        return cameraYaw;
    }

    public float getCameraPitchForTag() {
        return cameraPitch;
    }

    public boolean overrideMouse() {
        if (this.mc.inGameHasFocus && Display.isActive()) {
            if (!this.perspectiveToggled) {
                return true;
            }
            mc.mouseHelper.mouseXYChange();
            float f1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float f2 = f1 * 1.3f * 1.3f;
            float f3 = (float) mc.mouseHelper.deltaX * f2;
            float f4 = -(float) mc.mouseHelper.deltaY * f2;
            this.cameraYaw += f3 * 0.15f;
            this.cameraPitch += f4 * 0.15f;
            if (this.cameraPitch > 90.0f) {
                this.cameraPitch = 90.0f;
            }
            if (this.cameraPitch < -90.0f) {
                this.cameraPitch = -90.0f;
            }
            mc.renderGlobal.setDisplayListEntitiesDirty();
        }
        return false;
    }
}