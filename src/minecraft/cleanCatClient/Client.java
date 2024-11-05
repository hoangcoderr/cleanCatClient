// src/minecraft/cleanCatClient/Client.java
package cleanCatClient;

import cleanCatClient.discordrpc.DiscordRP;
import cleanCatClient.event.EventManager;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.gui.clickgui.settings.ModSettingsInstance;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.font.customFont.GlyphPage;
import cleanCatClient.gui.font.customFont.GlyphPageFontRenderer;
import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.utils.FileManager;
import cleanCatClient.utils.SessionChanger;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;

public class Client {
    public static final Client INSTANCE = new Client();

    public static final Client getInstance() {
        return INSTANCE;
    }

    public static final Logger logger = LogManager.getLogger(Client.class);
    public static final String CLIENT_NAME = "cleanCat Client", CLIENT_VERSION = "1.0.0.h - 1.8.9",
            CLIENT_BUILD = "2024.06.02", CLIENT_AUTHOR = "hoangcoderr",
            WINDOW_TITLE = CLIENT_NAME + " (" + CLIENT_VERSION + ")";
    private static DiscordRP discordRPC = new DiscordRP();
    private long startTime;

    public void init() {
        logger.info("Starting " + CLIENT_NAME + " " + CLIENT_VERSION + "");
        startTime = System.currentTimeMillis();
        FileManager.init();
        EventManager.register(this);
        FontUtil.bootstrap();
        start();
    }

    public static DiscordRP getDiscordRPC() {
        return discordRPC;
    }

    public static HUDManager hudManager;

    public ClickGui clickGui = new ClickGui();

    public GlyphPageFontRenderer fontRenderer;

    public void start() {
        hudManager = HUDManager.getInstance();
        ModInstances.register(hudManager);
        ModSettingsInstance.register();
        //SessionChanger.getInstance().setUser("hi", "hi");
        Minecraft.centerX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2;
        Minecraft.centerY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2;
        Minecraft.displayHeightBefore = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        Minecraft.displayWidthBefore = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();

//
//        char[] chars = new char[65536]; // Increase the range of characters
//
//        for (int i = 0; i < 65536; i++) {
//            chars[i] = (char) i;
//        }
//
//        try {
//            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\lebui\\Downloads\\t.ttf")).deriveFont(20f);
//            GlyphPage page = new GlyphPage(customFont, true, true); // Increase the texture size to 2048
//            page.generateGlyphPage(chars);
//            page.setupTexture();
//
//            fontRenderer = new GlyphPageFontRenderer(page, page, page, page);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
            HUDManager hudManager = HUDManager.getInstance();
            hudManager.openConfigScreen(hudManager);
        }
        if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_TOGGLE.isPressed()) {
            openClickGui();
        }

    }

    public void openClickGui() {
        Minecraft.getMinecraft().displayGuiScreen(clickGui);
    }

    // src/minecraft/cleanCatClient/Client.java
    public void shutdown() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = (elapsedTime / (1000 * 60 * 60)) % 24;

        logger.info("Shutting down " + CLIENT_NAME + " " + CLIENT_VERSION + "");
        logger.info(String.format("Elapsed time: %d hours, %d minutes, %d seconds", hours, minutes, seconds));
        discordRPC.shutdown();
    }
}