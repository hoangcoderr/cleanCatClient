package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.KeyEvent;
import cleanCatClient.mods.Mod;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Perspective extends Mod {
    private boolean returnOnRelease = true; // hold down the key = true
    public boolean perspectiveToggled = false;

    private float cameraYaw = 0.0F;
    private float cameraPitch = 0.0F;

    private int previousPerspective = 0; // pref f5 state

    public Perspective() {
        super(ModConstants.PERSPECTIVE, ModConstants.PERSPECTIVE_DESC);}
    @EventTarget
    public void keyboardEvent(KeyEvent e) {
        if (e.getKey() == mc.gameSettings.CLIENT_PERSPECTIVE.getKeyCode()) {
            if (Keyboard.getEventKeyState()) {
                if (!perspectiveToggled) {
                    perspectiveToggled = true;
                    cameraYaw = mc.thePlayer.rotationYaw;
                    cameraPitch = mc.thePlayer.rotationPitch;
                    previousPerspective = mc.gameSettings.thirdPersonView;
                    mc.gameSettings.thirdPersonView = 1;
                } else if (!returnOnRelease) {
                    perspectiveToggled = false;
                    mc.gameSettings.thirdPersonView = previousPerspective;
                }
            } else if (returnOnRelease) {
                perspectiveToggled = false;
                mc.gameSettings.thirdPersonView = previousPerspective;
            }
        }
        if (e.getKey() == mc.gameSettings.keyBindTogglePerspective.getKeyCode()) {
            perspectiveToggled = false;
        }
    }

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : mc.thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : mc.thePlayer.rotationPitch;
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