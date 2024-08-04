package cleanCatClient.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenPosition {

    private static final Minecraft mc;
    private double x;
    private double y;

    static {
        mc = Minecraft.getMinecraft();
    }

    public ScreenPosition(final double x, final double y) {
        this.setRelative(x, y);
    }

    public ScreenPosition(final int x, final int y) {
        this.setAbsolute(x, y);
    }

    public static ScreenPosition fromRelativePosition(final double x, final double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(final int x, final int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        final ScaledResolution res = new ScaledResolution(ScreenPosition.mc);
        return (int)(this.x * res.getScaledWidth());
    }

    public int getAbsoluteY() {
        final ScaledResolution res = new ScaledResolution(ScreenPosition.mc);
        return (int)(this.y * res.getScaledHeight());
    }

    public double getRelativeX() {
        return this.x;
    }

    public double getRelativeY() {
        return this.y;
    }

    public void setAbsolute(final int x, final int y) {
        final ScaledResolution res = new ScaledResolution(ScreenPosition.mc);
        this.x = x / (double)res.getScaledWidth();
        this.y = y / (double)res.getScaledHeight();
    }

    public void setRelative(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
}