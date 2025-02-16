package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class TimeChanger extends Mod {
    public TimeChanger() {
        super(ModConstants.TIME_CHANGER, ModConstants.TIME_CHANGER_DESC, ModCategory.WORLD);
        loadConfig();
    }
    private long time = 0;
    public void setTime(long time) {
        this.time = time;
        saveConfig();
    }
    public long getTime() {
        return time;
    }

    public void loadConfig(){
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.time = Long.parseLong(dataConfig[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.time = 0;
        }
    }

    public void saveConfig(){
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.time);
        saveDataConfig(dataConfig);
    }
}