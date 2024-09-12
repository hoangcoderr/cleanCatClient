package cleanCatClient.mods.impl;

import cleanCatClient.event.EventTarget;
import cleanCatClient.gui.hud.ScreenPosition;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import cleanCatClient.constants.ModConstants;
import cleanCatClient.mods.ModDraggable;
import net.minecraft.util.ResourceLocation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class ColorSaturation extends ModDraggable {
    public ColorSaturation() {
        super(ModConstants.COLOR_SATURATION, ModConstants.COLOR_SATURATION_DESC, ModCategory.RENDER);
    }
    private static boolean lastEnabled = false;

    private static   ResourceLocation phosphorBlur = new ResourceLocation("color_convolve.json");


    @Override
    public int getWidth() {
        return -1;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public void render(ScreenPosition pos) {
        if (isEnabled()) {
            if (!lastEnabled) {
                lastEnabled = true;
                mc.entityRenderer.loadShader(phosphorBlur);
            }
        } else {
            if (lastEnabled) {
                lastEnabled = false;
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
    public void setSaturation(float saturation) {
        // Cập nhật giá trị saturation trong file color_convolve.json
        // (Giả sử bạn có một phương thức để cập nhật file JSON)
        updateSaturationInJson(saturation);
    }

    private void updateSaturationInJson(float saturation) {
        try {
            // Đọc tệp JSON
            FileReader reader = new FileReader("color_convolve.json");

            // Sử dụng JsonParser để đọc file JSON
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(reader).getAsJsonObject();
            reader.close();

            // Cập nhật giá trị saturation
            JsonObject colorConvolvePass = json.getAsJsonArray("passes").get(0).getAsJsonObject();
            JsonObject uniforms = colorConvolvePass.getAsJsonArray("uniforms").get(0).getAsJsonObject();
            uniforms.add("values", new JsonPrimitive(saturation));

            // Ghi lại tệp JSON
            FileWriter writer = new FileWriter("color_convolve.json");
            writer.write(json.toString());
            writer.close();

            // Tải lại shader
            reloadShader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reloadShader() {
        if (isEnabled()) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            phosphorBlur = new ResourceLocation("minecraft:shaders/post/color_convolve.json");
            System.out.println("Reloading shader");
            mc.entityRenderer.loadShader(phosphorBlur);
        }
    }
}
