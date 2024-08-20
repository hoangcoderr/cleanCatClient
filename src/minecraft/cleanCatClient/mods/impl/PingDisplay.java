package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingDisplay extends ModDraggable {
    private int ping = -1;
    private long lastPingTime = 0;

    public PingDisplay() {
        super(ModConstants.PING_DISPLAY, ModConstants.PING_DISPLAY_DESC, ModCategory.RENDER);
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void render(ScreenPosition pos) {
        long currentTime = System.currentTimeMillis();
        ServerData server = Minecraft.getMinecraft().getCurrentServerData();

        // Kiểm tra nếu đã qua 10 giây kể từ lần cập nhật ping cuối cùng
        if (server != null && currentTime - lastPingTime >= 10000) {
            try {
                ping = sendPing(server);
                lastPingTime = currentTime; // Cập nhật thời gian ping cuối cùng
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Vẽ ping
        FontUtil.normal.drawString(ping + " ms", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawString("1000 ms", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    private int sendPing(ServerData server) throws IOException {
        ServerAddress address = ServerAddress.fromString(server.serverIP);
        Socket socket = new Socket();
        socket.setSoTimeout(5000);
        long timeBefore = System.currentTimeMillis();
        try {
            socket.connect(new InetSocketAddress(address.getIP(), address.getPort()));
        } finally {
            socket.close();
        }
        return (int) (System.currentTimeMillis() - timeBefore);
    }
}
