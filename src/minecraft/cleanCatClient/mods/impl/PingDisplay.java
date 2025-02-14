package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PingDisplay extends ModDraggable {
    private final AtomicInteger ping = new AtomicInteger(-1); // Giá trị ping lưu trữ
    private long lastPingTime = 0; // Thời gian cuối cùng ping được cập nhật
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Color color = new Color(255, 255, 255, 255);

    public PingDisplay() {
        super(ModConstants.PING_DISPLAY, ModConstants.PING_DISPLAY_DESC, ModCategory.RENDER);
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void render(ScreenPosition pos) {
        String pingText = ping.get() >= 0 ? ping.get() + " ms" : "-1 ms";
        mc.fontRendererObj.drawStringWithShadow(pingText, pos.getAbsoluteX(), pos.getAbsoluteY(), color.getRGB());
        updatePing();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        FontUtil.normal.drawStringWithShadow("1000 ms", pos.getAbsoluteX(), pos.getAbsoluteY(), color.getRGB());
    }

    private void updatePing() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPingTime > 3000) { // Cập nhật mỗi 5 giây
            lastPingTime = currentTime;
            executorService.submit(this::calculatePing);    
        }
    }

    private void calculatePing() {
        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        if (serverData != null) {
            try (Socket socket = new Socket()) {
                String ip = ServerAddress.fromString(serverData.serverIP).getIP();
                int port = ServerAddress.fromString(serverData.serverIP).getPort();
                InetSocketAddress address = new InetSocketAddress(ip, port);

                long startTime = System.currentTimeMillis();
                socket.connect(address, 1000); // Thời gian chờ 1 giây
                long endTime = System.currentTimeMillis();

                ping.set((int) (endTime - startTime)); // Gán giá trị ping
            } catch (IOException e) {
                ping.set(-1); // Lỗi khi không thể kết nối
            }
            
        } else {
            ping.set(-1); // Không ở server
        }
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
