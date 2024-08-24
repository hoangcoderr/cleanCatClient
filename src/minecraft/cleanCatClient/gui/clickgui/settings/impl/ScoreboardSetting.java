package cleanCatClient.gui.clickgui.settings.impl;

import cleanCatClient.gui.clickgui.settings.ModSettings;
import cleanCatClient.mods.ModInstances;

public class ScoreboardSetting extends ModSettings {
    public ScoreboardSetting() {
        super(ModInstances.getScoreboard());
    }
}
