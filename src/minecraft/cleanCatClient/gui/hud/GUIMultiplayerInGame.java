package cleanCatClient.gui.hud;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.gui.GuiMultiplayer;

import java.io.IOException;

public class GUIMultiplayerInGame extends GuiMultiplayer {
    public GUIMultiplayerInGame() {
        super(null);
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        if (button.id == 1 || button.id == 4) {
            this.disconnect();
        }
        super.actionPerformed(button);
    }

    @Override
    public void connectToSelected() {
        this.disconnect();
        super.connectToSelected();
    }

    private void disconnect() {
        if (this.mc.theWorld != null) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(null);
            this.parentScreen = null;
        }
    }

}
