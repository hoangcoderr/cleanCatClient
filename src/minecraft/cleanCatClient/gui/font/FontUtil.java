package cleanCatClient.gui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NonAtomicOperationOnVolatileField")
public class FontUtil {
    public static volatile int completed;
    public static MinecraftFontRenderer normal;
    public static MinecraftFontRenderer customSize;
    public static MinecraftFontRenderer bold;
    public static MinecraftFontRenderer italic;
    private static Font normal_;
    private static Font customSize_;
    private static Font bold_;
    private static Font italic_;
    private static Map<Integer, MinecraftFontRenderer> fontRenderers = new HashMap<>();

    private static Font getFont(Map<String, Font> locationMap, String location, int size, int style) {
        Font font = null;

        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(style, size);
            } else {
                InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("cleanCatClient/Font/" + location)).getInputStream();
                font = Font.createFont(Font.TRUETYPE_FONT, is);
                locationMap.put(location, font);
                font = font.deriveFont(style, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("Consolas", style, size);
        }

        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 1;
    }

    public static void bootstrap() {
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            normal_ = getFont(locationMap, "font.ttf", 19, Font.PLAIN);
            customSize_ = getFont(locationMap, "font.ttf", 35, Font.PLAIN);
            bold_ = getFont(locationMap, "font.ttf", 19, Font.BOLD);
            italic_ = getFont(locationMap, "font.ttf", 19, Font.ITALIC);
            completed++;
        }).start();

        while (!hasLoaded()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        normal = new MinecraftFontRenderer(normal_, true, true);
        customSize = new MinecraftFontRenderer(customSize_, true, true);
        bold = new MinecraftFontRenderer(bold_, true, true);
        italic = new MinecraftFontRenderer(italic_, true, true);
        getFontRenderer(30);
        getFontRenderer(35);
    }

    public static MinecraftFontRenderer getFontRenderer(int fontSize) {
        if (!fontRenderers.containsKey(fontSize)) {
            Map<String, Font> locationMap = new HashMap<>();
            Font customFont = getFont(locationMap, "font.ttf", fontSize, Font.PLAIN);
            MinecraftFontRenderer customFontRenderer = new MinecraftFontRenderer(customFont, true, true);
            fontRenderers.put(fontSize, customFontRenderer);
        }
        return fontRenderers.get(fontSize);
    }
}