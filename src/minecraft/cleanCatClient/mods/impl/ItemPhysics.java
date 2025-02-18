package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class ItemPhysics extends Mod {
    private boolean spin = true;
    private boolean randomAngle = false;

    public ItemPhysics() {
        super(ModConstants.ITEM_PHYSICS, ModConstants.ITEM_PHYSICS_DESC, ModCategory.WORLD);
        loadConfig();
    }

    public boolean isRandomAngle() {
        return randomAngle;
    }

    public void setRandomAngle(boolean randomAngle) {
        this.randomAngle = randomAngle;
        if (randomAngle) {
            this.spin = false;
        }
        saveConfig();
    }

    public void setSpin(boolean spin) {
        this.spin = spin;
        if (spin) {
            this.randomAngle = false;
        }
        saveConfig();
    }
    public boolean isSpin() {
        return spin;
    }



    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            saveConfig();
            return;
        }
        try {
            setSpin(Boolean.parseBoolean(dataConfig[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        String[] dataConfig = new String[2];
        dataConfig[0] = String.valueOf(spin);
        dataConfig[1] = String.valueOf(randomAngle);
        saveDataConfig(dataConfig);
    }
}
