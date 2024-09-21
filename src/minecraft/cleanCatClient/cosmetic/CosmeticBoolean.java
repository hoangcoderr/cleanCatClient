package cleanCatClient.cosmetic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticBoolean {

    public static boolean snowcape;
    public static boolean nornalCape;
    public static boolean thuderCape;
    public static boolean badWolfCape;
    public static boolean starrySunsetCape;
    static {
        CosmeticBoolean.snowcape = true;
        CosmeticBoolean.nornalCape = false;
        CosmeticBoolean.thuderCape = false;
        CosmeticBoolean.badWolfCape = false;
        CosmeticBoolean.starrySunsetCape = false;
    }


    public static boolean shouldRenderTopHat(final AbstractClientPlayer player) {
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername());
    }

    public static float[] getHaloColor(final AbstractClientPlayer player) {
        return new float[]{0.0f, 51.0f, 153.0f, 255.0f};
    }

    public static boolean SnowCape() {
        return CosmeticBoolean.snowcape;
    }

    public static boolean NornalCape() {
        return CosmeticBoolean.nornalCape;
    }

    public static boolean ThuderCape() {
        return CosmeticBoolean.thuderCape;
    }

    public static boolean BadWolfCape() {
        return CosmeticBoolean.badWolfCape;
    }

    public static boolean StarrySunsetCape() {
        return CosmeticBoolean.starrySunsetCape;
    }
}
