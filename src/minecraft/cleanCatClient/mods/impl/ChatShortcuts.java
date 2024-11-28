package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ChatShortcuts extends Mod {
    private Map<String, String> shortcuts;
    private static final String CONFIG_FILE = "chat_shortcuts.properties";

    public ChatShortcuts() {
        super(ModConstants.CHAT_SHORTCUTS, ModConstants.CHAT_SHORTCUTS_DESC, ModCategory.PLAYER);
        initializeShortcuts();
        loadConfig();
    }

    private void initializeShortcuts() {
        shortcuts = new HashMap<>();
    }

    public Map<String, String> getShortcuts() {
        return shortcuts;
    }

    public String processChatMessage(String message) {
        if (isEnabled() && shortcuts.containsKey(message)) {
            message = shortcuts.get(message);
        }
        if (message.toLowerCase().startsWith("/pc")){
            return message.toLowerCase().replace("/pc", "/pc &b&l");
        }
        return message;
    }

    public void setShortcuts(Map<String, String> map) {
        this.shortcuts = map;
        saveConfig();
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        for (String line : dataConfig) {
            String[] entry = line.split("=", 2);
            if (entry.length == 2) {
                shortcuts.put(entry[0], entry[1]);
            }
        }
    }

    @Override
    public void saveConfig() {
        String[] data = new String[shortcuts.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : shortcuts.entrySet()) {
            data[index++] = entry.getKey() + "=" + entry.getValue();
        }
        saveDataConfig(data);
    }
}