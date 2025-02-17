package cleanCatClient.gui.hud;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {

    int i = 0;

    // ADDED FOR SMOOTH DRAGGING

    private int smX, smY;


    protected boolean hovered;


    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    @Override
    public void initGui() {

        // modified to add your own buttons <3
        reloadPositions(api);

        super.initGui();
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        super.actionPerformed(button);
    }

    private final HUDManager api = HUDManager.getInstance();

    public HUDConfigScreen(HUDManager api) {
        for (IRenderer ren : api.getRegisteredRenderers()) {
            if (!ren.isEnabled()) continue;

            ScreenPosition pos = ren.load();
            if (pos == null) pos = ScreenPosition.fromRelativePosition(0.5, 0.5);

            adjustBounds(ren, pos);
            this.renderers.put(ren, pos);
        }
    }

    private void reloadPositions(HUDManager api) {
        renderers.clear();
        Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();

        for (IRenderer ren : registeredRenderers) {
            if (!ren.isEnabled()) {
                continue;
            }

            ScreenPosition pos = ren.load();
            if (pos == null) {
                pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            }

            adjustBounds(ren, pos);
            this.renderers.put(ren, pos);
        }
    }


    public static void adjustRendererPosition(IRenderer ren) {

        ScreenPosition pos = ren.load();
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        adjustBound(ren, pos);

    }

    private static void adjustBound(IRenderer renderer, ScreenPosition pos) {

    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();

        final float zBackup = this.zLevel;
        this.zLevel = 200;

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);

            // Get renderer width and height
            int width = renderer.getWidth();
            int height = renderer.getHeight();


            // Adjusting the size of the Gui.drawRect when dragging


            Gui.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX() + width, pos.getAbsoluteY() + height, 0x33FFFFFF);
            //fontRendererObj.drawString(pos.getAbsoluteX() + " " + pos.getAbsoluteY(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
            this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), width, height, 0x88FFFFFF);

            renderer.renderDummy(pos);

            // Start of smooth dragging
            int x = pos.getAbsoluteX();
            int y = pos.getAbsoluteY();

            this.hovered = mouseX >= x && mouseX <= x + renderer.getWidth() && mouseY >= y && mouseY <= y + renderer.getHeight();
            if (this.hovered) {
                Gui.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth() + pos.getAbsoluteX(), renderer.getHeight() + pos.getAbsoluteY(), 0x43000000);
                if (selectedRenderer.isPresent() && selectedRenderer.get() == renderer && renderers.get(selectedRenderer.get()) == pos) {
                    pos.setAbsolute(pos.getAbsoluteX() + mouseX - this.prevX, pos.getAbsoluteY() + mouseY - this.prevY);

                    adjustBounds(renderer, pos);

                    this.drawHollowRect(x, y, renderer.getWidth(), renderer.getHeight(), new Color(70, 0, 70, 230).getRGB());

                    this.prevX = mouseX;
                    this.prevY = mouseY;
                }
            }
            // End of smooth dragging
        }

        this.smX = mouseX;
        this.smY = mouseY;

        this.zLevel = zBackup;
    }

    private void drawHollowRect(int x, int y, int w, int h, int color) {

        this.drawHorizontalLine(x, x + w, y, color);
        this.drawHorizontalLine(x, x + w, y + h, color);

        this.drawVerticalLine(x, y + h, y, color);
        this.drawVerticalLine(x + w, y + h, y, color);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            renderers.entrySet().forEach((entry) -> {
                entry.getKey().save(entry.getValue());
            });
            this.mc.displayGuiScreen(null);
        }
    }


    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition pos = renderers.get(renderer);

        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);

        adjustBounds(renderer, pos);
    }

    @Override
    public void onGuiClosed() {

        for (IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        int x = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(sr.getScaledWidth() - renderer.getWidth(), 0)));
        int y = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(sr.getScaledHeight() - renderer.getHeight(), 0)));

        pos.setAbsolute(x, y);
    }


    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        this.prevX = x;
        this.prevY = y;

        loadMouseOver(x, y);
        super.mouseClicked(x, y, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.selectedRenderer = Optional.empty();

        super.mouseReleased(mouseX, mouseY, state);
    }


    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        if (selectedRenderer.isPresent()) moveSelectedRenderBy(x - prevX, y - prevY);

        this.prevX = x;
        this.prevY = y;
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private final int mouseX;
        private final int mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        @Override
        public boolean test(IRenderer renderer) {

            ScreenPosition pos = renderers.get(renderer);

            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {

                return mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight();

            }

            return false;
        }

    }

}