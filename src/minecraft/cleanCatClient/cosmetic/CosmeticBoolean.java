package cleanCatClient.cosmetic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticBoolean {

    public static boolean snowcape;
    public static boolean nornalCape;
    public static boolean thuderCape;
    public static boolean badWolfCape;
    public static boolean starrySunsetCape;
    public static boolean topHat;
    public static boolean dragonWing;
    public static boolean satanWing;

    static {
        CosmeticBoolean.snowcape = false;
        CosmeticBoolean.nornalCape = false;
        CosmeticBoolean.thuderCape = false;
        CosmeticBoolean.badWolfCape = false;
        CosmeticBoolean.starrySunsetCape = true;
        CosmeticBoolean.topHat = true;
        CosmeticBoolean.dragonWing = true;
        CosmeticBoolean.satanWing = false;
    }


    public static boolean shouldRenderTopHat(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername());
    }

    public static float[] getTopHatColor(final AbstractClientPlayer player) {
        return new float[]{1.0f, 1.0f, 0.0f};
    }

    public static boolean shouldRenderDragonWing(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && dragonWing;
    }

    public static float[] getDragonWingColor() {
        return new float[]{1.0f, 0.0f, 0.0f};
    }

    public static boolean shouldRenderSatanWings(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && satanWing;
    }

    public static float[] getSatanWingsColor(final AbstractClientPlayer player) {
        return new float[]{1.0f, 0.0f, 0.0f};
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

    public static void set(String cosmeticName) {
        System.out.println("Da truyen vao: " + cosmeticName);
        switch (cosmeticName) {
            case "Snow Cape":
                CosmeticBoolean.snowcape = true;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;
                break;
            case "Nornal Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = true;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;

                break;
            case "Thuder Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = true;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;

                break;
            case "Bad Wolf Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = true;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;

                break;
            case "Starry Sunset Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = true;
                CosmeticBoolean.topHat = false;
                break;
            case "Top Hat":
                CosmeticBoolean.topHat = true;
                break;
            case "Dragon Wings":
                CosmeticBoolean.dragonWing = true;
                CosmeticBoolean.satanWing = false;
                break;

            case "Satan Wings":
                CosmeticBoolean.satanWing = true;
                CosmeticBoolean.dragonWing = false;
                break;
            default:
                break;
        }

    }
}
