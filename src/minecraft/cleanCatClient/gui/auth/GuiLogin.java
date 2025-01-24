package cleanCatClient.gui.auth;

import cleanCatClient.gui.mainmenu.MainMenu;
import cleanCatClient.gui.mainmenu.button.ClientButton;
import cleanCatClient.gui.notification.Notification;
import cleanCatClient.gui.notification.NotificationManager;
import cleanCatClient.utils.SessionChanger;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiLogin extends GuiScreen {
    private GuiTextField username;

    @Override
    protected void actionPerformed(final ClientButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            if (this.username.getText().equals("")) {
                this.mc.displayGuiScreen(new GuiLogin());
            } else {
                SessionChanger.getInstance().setUserOffline(this.username.getText());
                NotificationManager.show(new Notification("Logged in as " + this.username.getText(), 3));
            }
        } else if (button.id == 1) {
            this.mc.displayGuiScreen(new MainMenu());
        }
    }

    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        this.drawDefaultBackground();
        super.drawScreen(x2, y2, z2);
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.username.drawTextBox();
        Gui gui = new Gui();
        gui.drawCenteredString(mc.fontRendererObj, "Username", this.width / 2, sr.getScaledHeight() / 2 - 65, -1);
    }

    @Override
    public void initGui() {
        super.initGui();
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.clear();
        this.buttonList.add(new ClientButton(0, this.width / 2 - 50 - 10, this.height / 2 - 20, 120, 20, I18n.format("Login (Cracked)", new Object[0])));
        this.buttonList.add(new ClientButton(1, this.width / 2 - 50 - 10, this.height / 2 + 10, 120, 20, "Back to Main Screen"));
        (this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50 - 10, sr.getScaledHeight() / 2 - 50, 120, 20)).setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        super.keyTyped(character, key);
        if (character == '\t' && !this.username.isFocused()) {
            this.username.setFocused(true);
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) throws IOException {
        super.mouseClicked(x2, y2, button);
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        mc.entityRenderer.loadEntityShader(null);
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.username.updateCursorCounter();
    }
}