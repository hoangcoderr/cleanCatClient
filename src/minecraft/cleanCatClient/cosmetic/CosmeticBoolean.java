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
    public static boolean dragonBabyWing;
    public static boolean animeGirlCape;
    public static boolean jumpingFrogCape;
    public static boolean dragonObsidianWing;
    public static boolean woolHat;
    public static boolean wavingCape;
    public static boolean chillinBoy;

    static {
        CosmeticBoolean.snowcape = false;
        CosmeticBoolean.nornalCape = false;
        CosmeticBoolean.thuderCape = false;
        CosmeticBoolean.badWolfCape = false;
        CosmeticBoolean.starrySunsetCape = false;
        CosmeticBoolean.topHat = false;
        CosmeticBoolean.dragonWing = false;
        CosmeticBoolean.satanWing = false;
        CosmeticBoolean.dragonBabyWing = false;
        CosmeticBoolean.animeGirlCape = false;
        CosmeticBoolean.dragonObsidianWing = true;
        CosmeticBoolean.woolHat = true;
        CosmeticBoolean.wavingCape = false;
        CosmeticBoolean.chillinBoy = true;
    }

    public static boolean shouldRenderWitchHat(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && woolHat;
    }

    public static boolean shouldRenderDragonObsidianWing(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && dragonObsidianWing;
    }

    public static boolean shouldRenderTopHat(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && topHat;
    }

    public static float[] getTopHatColor(final AbstractClientPlayer player) {
        return new float[]{1.0f, 1.0f, 0.0f};
    }

    public static float[] getBlazeColor(final AbstractClientPlayer player) {
        return new float[]{1.0f, 0.0f, 0.0f};
    }

    public static boolean shouldRenderDragonWing(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && dragonWing;
    }

    public static boolean shouldRenderDragonBabyWing(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && dragonBabyWing;
    }

    public static float[] getDragonWingColor() {
        return new float[]{1.0f, 0.0f, 0.0f};
    }

    public static boolean shouldRenderSatanWings(final AbstractClientPlayer player) {
        //neu la ng choi moi render
        return player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && satanWing;
    }

    public static float[] getWitchHatColor(final AbstractClientPlayer player) {
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
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;


                break;
            case "Nornal Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = true;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;


                break;
            case "Thuder Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = true;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;


                break;
            case "Bad Wolf Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = true;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.topHat = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;


                break;
            case "Starry Sunset Cape":
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = true;
                CosmeticBoolean.topHat = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;

                break;
            case "Top Hat":
                CosmeticBoolean.topHat = true;
                CosmeticBoolean.woolHat = false;
                break;
            case "Wool Hat":
                CosmeticBoolean.woolHat = true;
                CosmeticBoolean.topHat = false;
                break;
            case "Dragon Wings":
                CosmeticBoolean.dragonWing = true;
                CosmeticBoolean.satanWing = false;
                CosmeticBoolean.dragonBabyWing = false;
                CosmeticBoolean.dragonObsidianWing = false;

                break;

            case "Satan Wings":
                CosmeticBoolean.satanWing = true;
                CosmeticBoolean.dragonWing = false;
                CosmeticBoolean.dragonBabyWing = false;
                CosmeticBoolean.dragonObsidianWing = false;

                break;
            case "Baby Dragon Wings":
                CosmeticBoolean.dragonBabyWing = true;
                CosmeticBoolean.dragonWing = false;
                CosmeticBoolean.satanWing = false;
                CosmeticBoolean.dragonObsidianWing = false;

                break;
            case "Anime Girl Cape":
                CosmeticBoolean.animeGirlCape = true;
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.jumpingFrogCape = false;                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;

                break;
            case "Jumping Frog Cape":
                CosmeticBoolean.jumpingFrogCape = true;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.chillinBoy = false;

                break;
            case "Waving Cape":
                CosmeticBoolean.wavingCape = true;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                CosmeticBoolean.chillinBoy = false;

                break;
            case "Dragon Obsidian Wings":
                CosmeticBoolean.dragonObsidianWing = true;
                CosmeticBoolean.dragonBabyWing = false;
                CosmeticBoolean.dragonWing = false;
                CosmeticBoolean.satanWing = false;
                break;
            case "Chillin Boy Cape":
                CosmeticBoolean.chillinBoy = true;
                CosmeticBoolean.wavingCape = false;
                CosmeticBoolean.jumpingFrogCape = false;
                CosmeticBoolean.animeGirlCape = false;
                CosmeticBoolean.snowcape = false;
                CosmeticBoolean.nornalCape = false;
                CosmeticBoolean.thuderCape = false;
                CosmeticBoolean.badWolfCape = false;
                CosmeticBoolean.starrySunsetCape = false;
                break;
            default:
                break;
        }

    }
}
