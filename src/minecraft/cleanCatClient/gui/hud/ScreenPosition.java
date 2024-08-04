package cleanCatClient.gui.hud;

import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenPosition {
    @Expose(serialize = false)
    private static final Minecraft mc;
    private int x, y;

    static {
        mc = Minecraft.getMinecraft();
    }

    public ScreenPosition(final double x, final double y) {
        this.setRelative(x, y);
    }

    public ScreenPosition(final int x, final int y) {
        this.setAbsolute(x, y);
    }

    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        return x;
    }

    public int getAbsoluteY() {
        return y;
    }

    public double getRelativeX() {
        ScaledResolution sr = new ScaledResolution(mc);
        return x / sr.getScaledWidth_double();
    }

    public double getRelativeY() {
        ScaledResolution sr = new ScaledResolution(mc);
        return y / sr.getScaledHeight_double();
    }

    public void setAbsolute(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setRelative(double x, double y) {
        ScaledResolution sr = new ScaledResolution(mc);
        this.x = (int) (sr.getScaledWidth() / x);
        this.y = (int) (sr.getScaledHeight() / y);
    }

}