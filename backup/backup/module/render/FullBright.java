package cleanCatClient.module.render;

import cleanCatClient.LanguageConstant.English;
import cleanCatClient.module.Module;
import org.lwjgl.input.Keyboard;

import java.security.Key;

public class FullBright extends Module{
    public FullBright() {
        super(English.FULL_BRIGHT, Keyboard.KEY_B, English.FULL_BRIGHT_DESC, Catelogy.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
