package cleanCatClient;

import cleanCatClient.discordrpc.DiscordRP;
import cleanCatClient.event.EventManager;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.gui.clickgui.ClickGui;
import cleanCatClient.gui.clickgui.settings.ModSettingsInstance;
import cleanCatClient.gui.font.FontUtil;
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

public class Client {
	public static final Client INSTANCE = new Client();
	public static final Client getInstance() {
		return INSTANCE;
	}
	public static final Logger logger = LogManager.getLogger(Client.class);
	public static final String CLIENT_NAME = "cleanCat Client", CLIENT_VERSION = "1.1.9 - 1.8.9",
			CLIENT_BUILD = "2024.06.02", CLIENT_AUTHOR = "hoangcoderr",
			WINDOW_TITLE = CLIENT_NAME + " (" + CLIENT_VERSION + ")";
	private static DiscordRP discordRPC = new DiscordRP();
	public void init() {
		logger.info("Starting " + CLIENT_NAME + " " + CLIENT_VERSION + "");
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
	public void start() {
		hudManager = HUDManager.getInstance();
		ModInstances.register(hudManager);
		ModSettingsInstance.register();
		//SessionChanger.getInstance().setUser("hi", "hi");
		Minecraft.centerX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2;
		Minecraft.centerY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2;
	}

	@EventTarget
	public void onTick(ClientTickEvent e){
		if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
			hudManager.openConfigScreen();
		}
		if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_TOGGLE.isPressed()) {
			openClickGui();
		}

	}

	public void openClickGui() {
		Minecraft.getMinecraft().displayGuiScreen(clickGui);
	}

	public void shutdown() {
		logger.info("Shutting down " + CLIENT_NAME + " " + CLIENT_VERSION + "");
		discordRPC.shutdown();
	}
}