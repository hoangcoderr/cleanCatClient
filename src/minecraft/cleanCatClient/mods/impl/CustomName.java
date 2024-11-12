package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class CustomName extends Mod{
    private String customName = "꒳cleanCatꔫ";
    public CustomName() {
        super(ModConstants.CUSTOM_NAME, ModConstants.CUSTOM_NAME_DESC, ModCategory.PLAYER);
        loadConfig();
    }

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.customName = dataConfig[0];
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.customName = "cleanCatClient";
        }
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = this.customName;
        saveDataConfig(dataConfig);
    }

    public String getCustomName() {
        return isEnabled() ? this.customName : mc.thePlayer.getName();
    }

    public void setCustomName(String customName) {
        this.customName = customName;
        saveConfig();
    }
}
