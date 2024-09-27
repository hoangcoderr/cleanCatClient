package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class ItemPhysics extends Mod {
    private boolean spin = true;
    public ItemPhysics(){
        super(ModConstants.ITEM_PHYSICS, ModConstants.ITEM_PHYSICS_DESC, ModCategory.WORLD);
        loadConfig();
    }

    public boolean isSpin() {
        return spin;
    }

    public void setSpin(boolean spin) {
        this.spin = spin;
        saveConfig();
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
        saveDataConfig(new String[]{
            String.valueOf(isSpin())
        });
    }
}
