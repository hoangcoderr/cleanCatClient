package cleanCatClient.gui.auth.microsoft;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.utils.SessionChanger;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiLoginMicrosoft extends GuiScreen {
    private boolean started;

    @Override
    protected void actionPerformed(final ClientButton button) {
        if (button.id == 0) {
            if (started) return;
            started = true;
            SessionChanger.getInstance().setUserMicrosoft(false); // mặc định: auto-login, KHÔNG xoá cookies
        } else if (button.id == 1) {
            if (started) return;
            started = true;
            SessionChanger.getInstance().setUserMicrosoft(true); // ép clear cookies, bắt nhập lại acc
        }
    }

    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        Gui gui = new Gui();
        gui.drawCenteredString(this.mc.fontRendererObj, "Sign in with Microsoft", this.width / 2, sr.getScaledHeight() / 2 - 65, -1);
        gui.drawCenteredString(this.mc.fontRendererObj, started ? "Waiting for login..." : "A window will open to sign in", this.width / 2, sr.getScaledHeight() / 2 - 50, -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        // Nút đăng nhập auto (giữ session)
        this.buttonList.add(new ClientButton(0, this.width / 2 - 50 - 10, this.height / 2, 120, 20, I18n.format("Login Microsoft")));
        // Nút đăng nhập = tài khoản khác (clear cookies trước)
        this.buttonList.add(new ClientButton(1, this.width / 2 - 50 - 10, this.height / 2 + 30, 120, 20, "Chọn tài khoản khác"));
        Keyboard.enableRepeatEvents(true);
    }


    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.loadEntityShader(null);
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        // no inputs
    }
}
