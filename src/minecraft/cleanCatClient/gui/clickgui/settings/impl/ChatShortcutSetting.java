package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class ChatShortcutSetting extends ModSettings {
    public ChatShortcutSetting() {
       super(ModInstances.getChatShortcuts());
    }
}
