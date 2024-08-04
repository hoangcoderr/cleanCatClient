package cleanCatClient;

import cleanCatClient.events.Event;
import cleanCatClient.module.movement.*;
import cleanCatClient.module.render.*;
import cleanCatClient.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import cleanCatClient.module.Module;
public class ClientConstants {
	private static final ClientConstants INSTANCE = new ClientConstants();
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static final ClientConstants getInstance() {
		return INSTANCE;
	}
	public static HUD hud = new HUD();
	public static final Logger logger = LogManager.getLogger(ClientConstants.class);
	public static final String CLIENT_NAME = "cleanCat Client", CLIENT_VERSION = "1.0.0 - 1.8.9",
			CLIENT_BUILD = "2024.06.02", CLIENT_AUTHOR = "hoangcoderr",
			WINDOW_TITLE = CLIENT_NAME + " (" + CLIENT_VERSION + ")";

	public void init() {
		logger.info("Starting " + CLIENT_NAME + " " + CLIENT_VERSION + "");
		initModules();

	}

	public static void onEvent(Event e){
		for (Module module : modules) {
			if (!module.isEnable()){
				continue;
			}
			module.onEvent(e);
		}
	}
	public void initModules() {
		modules.add(new ToggleSprint());
		modules.add(new FullBright());
	}

	public void addModule(Module module) {
		modules.add(module);
	}

	public void removeModule(Module module) {
		modules.remove(module);
	}


	public void shutdown() {
		logger.info("Shutting down " + CLIENT_NAME + " " + CLIENT_VERSION + "");
	}

	public static void keyPress(int keyCode) {
		logger.info("Key Pressed: " + keyCode);
		for (Module module : modules) {
			if (module.getKeyCode() == keyCode) {
				module.toggle();
			}
		}
	}

	public List<Module> getModulesByCatelogy(Module.Catelogy catelogy) {
		List<Module> modules = new ArrayList<>();
		for (Module module : this.modules) {
			if (module.catelogy == catelogy) {
				modules.add(module);
			}
		}
		return modules;
	}
}
