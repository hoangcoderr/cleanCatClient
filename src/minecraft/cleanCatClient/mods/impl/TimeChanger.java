package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class TimeChanger extends Mod {
    public TimeChanger() {
        super(ModConstants.TIME_CHANGER, ModConstants.TIME_CHANGER_DESC, ModCategory.WORLD);
    }
    private long time = 0;
    public void setTime(long time) {
        this.time = time;
    }
    public long getTime() {
        return time;
    }
    @EventTarget
    public void onUpdate(ClientTickEvent e){
        World world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            world.setWorldTime(time);
        }
    }
}