package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class ParticlesMultiplier extends Mod {
    public ParticlesMultiplier() {
        super(ModConstants.PARTICLES_MULTIPLIER, ModConstants.PARTICLES_MULTIPLIER_DESC, ModCategory.RENDER);
        loadConfig();
    }
    private float multiplier = 16;
    private float savedMultiplier = 16;

    @Override
    public void loadConfig() {
        String[] dataConfig = loadDataConfig();
        if (dataConfig == null) {
            return;
        }
        try {
            this.multiplier = Float.parseFloat(dataConfig[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.multiplier = 16;
        }
    }

    @Override
    public void saveConfig() {
        String[] dataConfig = new String[1];
        dataConfig[0] = String.valueOf(this.multiplier);
        saveDataConfig(dataConfig);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            savedMultiplier = multiplier;
            multiplier = 16;

        }
        else {
            multiplier = savedMultiplier;
        }

        saveConfig();
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
        saveConfig();
    }

    public float getMultiplier() {
        return multiplier;
    }

}
