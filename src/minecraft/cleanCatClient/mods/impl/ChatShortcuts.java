package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

import java.util.HashMap;
import java.util.Map;

public class ChatShortcuts extends Mod {
    private Map<String, String> shortcuts;

    public ChatShortcuts() {
        super(ModConstants.CHAT_SHORTCUTS, ModConstants.CHAT_SHORTCUTS_DESC, ModCategory.PLAYER);
        initializeShortcuts();
    }

    private void initializeShortcuts() {
        shortcuts = new HashMap<>();
        shortcuts.put("brb", "be right back");
        shortcuts.put("gtg", "got to go");
        shortcuts.put("omw", "on my way");
        shortcuts.put("afk", "away from keyboard");
        shortcuts.put("idk", "I don't know");
        shortcuts.put("lol", "laugh out loud");
        shortcuts.put("lmao", "laughing my ass out");
        shortcuts.put("123cat", "/nick &6c&ea&at");
        shortcuts.put("123fcat", "/nick &7randomcat");
        shortcuts.put("gg", "Good Game Bro!");
        shortcuts.put("GG", "Good Game Bro!");
        // Thêm các shortcut khác tại đây
    }

    public Map<String, String> getShortcuts(){
        return shortcuts;
    }

    public String processChatMessage(String message) {

        if (isEnabled())
            for (Map.Entry<String, String> entry : shortcuts.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        return message;
    }
}