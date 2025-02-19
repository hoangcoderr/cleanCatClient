package cleanCatClient.cosmetic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

import java.util.HashMap;
import java.util.Map;

public class CosmeticBoolean {

    private static final Map<Integer, Cosmetic> cosmetics = new HashMap<>();

    static {
        cosmetics.put(1, new Cosmetic(1, 1, "Snow Cape", false));
        cosmetics.put(2, new Cosmetic(1, 2, "Normal Cape", false));
        cosmetics.put(3, new Cosmetic(1, 3, "Thunder Cape", false));
        cosmetics.put(4, new Cosmetic(1, 4, "Bad Wolf Cape", false));
        cosmetics.put(5, new Cosmetic(1, 5, "Starry Sunset Cape", false));
        cosmetics.put(6, new Cosmetic(2, 6, "Top Hat", false));
        cosmetics.put(7, new Cosmetic(3, 7, "Dragon Wing", false));
        cosmetics.put(8, new Cosmetic(3, 8, "Satan Wing", false));
        cosmetics.put(9, new Cosmetic(3, 9, "Dragon Baby Wing", false));
        cosmetics.put(10, new Cosmetic(1, 10, "Anime Girl Cape", false));
        cosmetics.put(11, new Cosmetic(1, 11, "Jumping Frog Cape", false));
        cosmetics.put(12, new Cosmetic(3, 12, "Dragon Obsidian Wing", true));
        cosmetics.put(13, new Cosmetic(2, 13, "Wool Hat", true));
        cosmetics.put(14, new Cosmetic(1, 14, "Waving Cape", false));
        cosmetics.put(15, new Cosmetic(1, 15, "Chillin Boy", true));
    }

    public static boolean shouldRenderCosmetic(int type, int id, AbstractClientPlayer player) {
        Cosmetic cosmetic = cosmetics.get(id);
        return cosmetic != null && cosmetic.getType() == type && player.getName().contains(Minecraft.getMinecraft().getSession().getUsername()) && cosmetic.isEnabled();
    }

    public static void set(int type, int id, boolean enabled) {
        System.out.println("Setting cosmetic: " + type + " " + id + " to " + enabled);
        cosmetics.forEach((cosmeticId, cosmetic) -> {
            if (cosmetic.getType() == type) {
                cosmetic.setEnabled(cosmetic.getId() == id && enabled);
            }
        });
    }

    public static Map<Integer, Cosmetic> getAllCosmetics() {
        return new HashMap<>(cosmetics);
    }


}