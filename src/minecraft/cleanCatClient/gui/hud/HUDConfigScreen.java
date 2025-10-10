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
    private int dragOffsetX, dragOffsetY;
    
    // Cache absolute position during drag for smooth movement
    private boolean isDragging = false;
    private double cachedAbsoluteX, cachedAbsoluteY;

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
        
        // Cache ScaledResolution for performance
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        
        // Update drag position every frame for smooth movement
        if (isDragging && selectedRenderer.isPresent()) {
            IRenderer draggedRenderer = selectedRenderer.get();
            
            // Calculate new position
            this.cachedAbsoluteX = mouseX - dragOffsetX;
            this.cachedAbsoluteY = mouseY - dragOffsetY;
            
            // Apply bounds (clamp to screen)
            int maxX = Math.max(sr.getScaledWidth() - draggedRenderer.getWidth(), 0);
            int maxY = Math.max(sr.getScaledHeight() - draggedRenderer.getHeight(), 0);
            this.cachedAbsoluteX = Math.max(0, Math.min(cachedAbsoluteX, maxX));
            this.cachedAbsoluteY = Math.max(0, Math.min(cachedAbsoluteY, maxY));
        }

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);

            // Get renderer width and height
            int width = renderer.getWidth();
            int height = renderer.getHeight();

            // Use cached position if this renderer is being dragged, otherwise use actual position
            boolean isBeingDragged = isDragging && selectedRenderer.isPresent() && selectedRenderer.get() == renderer;
            int x = isBeingDragged ? (int) Math.round(cachedAbsoluteX) : pos.getAbsoluteX();
            int y = isBeingDragged ? (int) Math.round(cachedAbsoluteY) : pos.getAbsoluteY();

            // Draw background and border
            Gui.drawRect(x, y, x + width, y + height, 0x33FFFFFF);
            this.drawHollowRect(x, y, width, height, 0x88FFFFFF);

            // Render the HUD element (temporarily set position for rendering)
            if (isBeingDragged) {
                ScreenPosition tempPos = ScreenPosition.fromAbsolute(x, y);
                renderer.renderDummy(tempPos);
            } else {
                renderer.renderDummy(pos);
            }

            // Check hover
            this.hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
            if (this.hovered) {
                Gui.drawRect(x, y, x + width, y + height, 0x43000000);
            }
            
            // Highlight if selected
            if (selectedRenderer.isPresent() && selectedRenderer.get() == renderer) {
                this.drawHollowRect(x, y, width, height, new Color(70, 0, 70, 230).getRGB());
            }
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
        
        // Initialize drag state
        if (selectedRenderer.isPresent()) {
            ScreenPosition pos = renderers.get(selectedRenderer.get());
            this.cachedAbsoluteX = pos.getAbsoluteX();
            this.cachedAbsoluteY = pos.getAbsoluteY();
            this.dragOffsetX = x - (int) Math.round(cachedAbsoluteX);
            this.dragOffsetY = y - (int) Math.round(cachedAbsoluteY);
            this.isDragging = true;
        }
        
        super.mouseClicked(x, y, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        // Commit cached position to actual position when drag ends
        if (isDragging && selectedRenderer.isPresent()) {
            ScreenPosition pos = renderers.get(selectedRenderer.get());
            pos.setAbsolute((int) Math.round(cachedAbsoluteX), (int) Math.round(cachedAbsoluteY));
        }
        
        this.isDragging = false;
        this.selectedRenderer = Optional.empty();

        super.mouseReleased(mouseX, mouseY, state);
    }


    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        // Position is now updated in drawScreen() for smoother movement
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