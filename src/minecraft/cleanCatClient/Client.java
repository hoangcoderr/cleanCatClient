// src/minecraft/cleanCatClient/Client.java
package cleanCatClient;

import cleanCatClient.cosmetic.impl.cape.realistic.WavyCapeRenderer;
import cleanCatClient.gui.mainmenu.MainMenu;
import cleanCatClient.gui.mainmenu.updatechecker.UpdateChecker;
import cleanCatClient.utils.discordrpc.DiscordRP;
import cleanCatClient.event.EventManager;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.gui.clickgui.settings.ModSettingsInstance;
import cleanCatClient.gui.font.FontUtil;
import cleanCatClient.gui.hud.HUDManager;
import cleanCatClient.mods.ModInstances;
import cleanCatClient.utils.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    public static final Client INSTANCE = new Client();

    public static final Client getInstance() {
        return INSTANCE;
    }

    public static final Logger logger = LogManager.getLogger(Client.class);
    public static final String CLIENT_NAME = "cleanCat Client", CLIENT_VERSION = "1.0.0.t - 1.8.9",
            CLIENT_BUILD = "2024.06.02", CLIENT_AUTHOR = "hoangcoderr",
            WINDOW_TITLE = CLIENT_NAME + " (" + CLIENT_VERSION + ")";
    private static DiscordRP discordRPC = new DiscordRP();
    private long startTime;
    private WavyCapeRenderer wavyCapeRenderer = new WavyCapeRenderer();

    public void init() {
        start();
    }

    public static DiscordRP getDiscordRPC() {
        return discordRPC;
    }

    public static HUDManager hudManager;

    public ClickGui clickGui = new ClickGui();


    public void start() {
        hudManager = HUDManager.getInstance();
        ModInstances.register(hudManager);
        ModSettingsInstance.register();
        //SessionChanger.getInstance().setUser("hi", "hi");
        Minecraft.centerX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2;
        Minecraft.centerY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2;
        Minecraft.displayHeightBefore = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        Minecraft.displayWidthBefore = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
        FileManager.init();
        logger.info("Starting " + CLIENT_NAME + " " + CLIENT_VERSION);
        startTime = System.currentTimeMillis();

        EventManager.register(this);
        FontUtil.bootstrap();
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