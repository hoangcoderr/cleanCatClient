package cleanCatClient.gui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NonAtomicOperationOnVolatileField")
public class FontUtil {
    public static volatile int completed;
    public static MinecraftFontRenderer normal;
    public static MinecraftFontRenderer customSize;
    private static Font normal_;
    private static Font customSize_;
    private static Map<Integer, MinecraftFontRenderer> fontRenderers = new HashMap<>();
    private static Font baseFont;

    private static Font getFont(Map<String, Font> locationMap, String location, int size) {
        Font font = null;

        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("cleanCatClient/Font/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Error loading font");
            font = new Font("Consolas", Font.PLAIN, 19);;
        }

        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 1;
    }

    public static void bootstrap() {
        new Thread(() -> {
            Map<String, Font> locationMap = new HashMap<>();
            normal_ = getFont(locationMap, "font.ttf", 19);
            customSize_ = getFont(locationMap, "font.ttf", 35);

            completed++;
        }).start();

        while (!hasLoaded()) {
            try {
                //noinspection BusyWait
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        normal = new MinecraftFontRenderer(normal_, true, true);
        customSize = new MinecraftFontRenderer(customSize_, true, true);
        //getFontRenderer(19);
        getFontRenderer(30);
    }
    public static MinecraftFontRenderer getFontRenderer(int fontSize) {
        if (!fontRenderers.containsKey(fontSize)) {
            Map<String, Font> locationMap = new HashMap<>();
            Font customFont = getFont(locationMap, "font.ttf", (int) fontSize);
            MinecraftFontRenderer customFontRenderer = new MinecraftFontRenderer(customFont, true, true);
            fontRenderers.put(fontSize, customFontRenderer);
        }
        return fontRenderers.get(fontSize);
    }
}