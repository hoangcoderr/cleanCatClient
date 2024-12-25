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

    private static Font normal_;
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


    public static void bootstrap() {

            Map<String, Font> locationMap = new HashMap<>();
            normal_ = getFont(locationMap, "font.ttf", 1, Font.PLAIN);



        normal = new MinecraftFontRenderer(normal_.deriveFont(19F), true, true);
        MinecraftFontRenderer customFontRenderer = new MinecraftFontRenderer(normal_.deriveFont(30f), true, true);
        fontRenderers.put(30, customFontRenderer);
        customFontRenderer = new MinecraftFontRenderer(normal_.deriveFont(35f), true, true);
        fontRenderers.put(35, customFontRenderer);
    }



    public static MinecraftFontRenderer getFontRenderer(int fontSize) {
        if (!fontRenderers.containsKey(fontSize)) {
            Map<String, Font> locationMap = new HashMap<>();

            MinecraftFontRenderer customFontRenderer = new MinecraftFontRenderer(normal_.deriveFont((float)fontSize), true, true);
            fontRenderers.put(fontSize, customFontRenderer);
        }
        return fontRenderers.get(fontSize);
    }
}