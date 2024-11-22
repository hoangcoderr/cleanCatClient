package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import cleanCatClient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PingDisplay extends ModDraggable {
    private final AtomicInteger ping = new AtomicInteger(-1);
    private long lastPingTime = 0;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public PingDisplay() {
        super(ModConstants.PING_DISPLAY, ModConstants.PING_DISPLAY_DESC, ModCategory.RENDER);
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return 30;
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
        if (server != null && currentTime - lastPingTime >= 3000) {
            lastPingTime = currentTime; // Cập nhật thời gian ping cuối cùng
            executorService.submit(() -> {
                try {
                    int newPing = sendPing(server);
                    ping.set(newPing);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // Vẽ ping
        //RenderUtils.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), getWidth(), getHeight());
        //FontUtil.normal.drawStringWithShadow(ping.get() + " ms", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
        mc.fontRendererObj.drawStringWithShadow(ping.get() + " ms", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawStringWithShadow("1000 ms", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
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