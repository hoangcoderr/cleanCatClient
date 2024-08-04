package cleanCatClient.module.movement;
import cleanCatClient.LanguageConstant.English;
import cleanCatClient.events.Event;
import cleanCatClient.events.listeners.EventUpdate;
import cleanCatClient.module.Module;
import org.lwjgl.input.Keyboard;

public class ToggleSprint extends Module{
    public ToggleSprint() {
        super(English.TOGGLE_SPRINT, Keyboard.KEY_Z, English.TOGGLE_SPRINT_DESC,Catelogy.MOVEMENT);
    }

    @Override
    public void onEvent (Event e){
        if (e instanceof EventUpdate){
            if (e.isPre()){
                    mc.thePlayer.setSprinting(true);
            }
        }
    }

    @Override
    public void onEnable(){
            mc.thePlayer.setSprinting(true);

    }
    @Override
    public void onDisable(){
        mc.thePlayer.setSprinting(false);
    }

}
