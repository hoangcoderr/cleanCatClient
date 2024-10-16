package cleanCatClient.gui.hud;

import cleanCatClient.gui.mainmenu.button.ClientButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {

    int i = 0;

    // ADDED FOR SMOOTH DRAGGING

    private int smX, smY;

    private boolean dragged = false;

    protected boolean hovered;


    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    @Override
    public void initGui() {

        // modified to add your own buttons <3

        super.initGui();
    }

    @Override
    protected void actionPerformed(ClientButton button) throws IOException {
        super.actionPerformed(button);
    }

    public HUDConfigScreen(HUDManager api) {
        reloadPositions(api);
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
    private static double relativeX, relativeY;
    public static void adjustRendererPosition(IRenderer ren) {
        ScreenPosition pos = ren.load();
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        adjustBound(ren, pos);
    }

    private static void adjustBound(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();
        System.out.println(Minecraft.displayWidthBefore + " " + Minecraft.displayHeightBefore);
// Tính toán tỷ lệ của tọa độ hiện tại dựa trên màn hình hiện tại
        double relativeX = (double) pos.getAbsoluteX() / Minecraft.displayWidthBefore;
        double relativeY = (double) pos.getAbsoluteY() / Minecraft.displayHeightBefore;

// Khi độ phân giải màn hình thay đổi, tính toán lại vị trí dựa trên tỷ lệ này
        int absoluteX = (int) (relativeX * screenWidth);
        int absoluteY = (int) (relativeY * screenHeight);

// Đảm bảo rằng tọa độ không vượt quá giới hạn màn hình
        absoluteX = Math.max(0, Math.min(absoluteX, Math.max(screenWidth - renderer.getWidth(), 0)));
        absoluteY = Math.max(0, Math.min(absoluteY, Math.max(screenHeight - renderer.getHeight(), 0)));

// Đặt lại tọa độ tuyệt đối cho đối tượng
        pos.setAbsolute(absoluteX, absoluteY);

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

            // Draw the small square at the bottom-right corner
            int squareSize = 5; // Size of the small square
            int squareX = pos.getAbsoluteX() + width;  // X coordinate of the bottom-right corner
            int squareY = pos.getAbsoluteY() + height; // Y coordinate of the bottom-right corner

            Gui.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, 0xFFFF0000); // Red color

            // Adjusting the size of the Gui.drawRect when dragging
            if (dragged && hovered) {
                width += mouseX - prevX;
                height += mouseY - prevY;
            }

            Gui.drawRect(pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX() + width, pos.getAbsoluteY() + height, 0x33FFFFFF);
            this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), width, height, 0x88FFFFFF);

            renderer.renderDummy(pos);

            // Start of smooth dragging
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();

            this.hovered = mouseX >= absoluteX && mouseX <= absoluteX + width && mouseY >= absoluteY && mouseY <= absoluteY + height;

            if (this.hovered) {
                if (dragged) {
                    pos.setAbsolute(pos.getAbsoluteX() + mouseX - this.prevX, pos.getAbsoluteY() + mouseY - this.prevY);

                    adjustBounds(renderer, pos);

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

    private boolean resizing = false;


    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition pos = renderers.get(renderer);

        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);

        if (pos.getAbsoluteX() == 0 << 1) {
            pos.setAbsolute(1, pos.getAbsoluteY());
        }

        if (pos.getAbsoluteY() == 0 << 1) {
            pos.setAbsolute(pos.getAbsoluteX(), 1);
        }

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
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

// Tính toán tỷ lệ của tọa độ hiện tại dựa trên màn hình hiện tại
        double relativeX = (double) pos.getAbsoluteX() / screenWidth;
        double relativeY = (double) pos.getAbsoluteY() / screenHeight;

// Khi độ phân giải màn hình thay đổi, tính toán lại vị trí dựa trên tỷ lệ này
        int absoluteX = (int) (relativeX * screenWidth);
        int absoluteY = (int) (relativeY * screenHeight);

// Đảm bảo rằng tọa độ không vượt quá giới hạn màn hình
        absoluteX = Math.max(0, Math.min(absoluteX, Math.max(screenWidth - renderer.getWidth(), 0)));
        absoluteY = Math.max(0, Math.min(absoluteY, Math.max(screenHeight - renderer.getHeight(), 0)));

// Đặt lại tọa độ tuyệt đối cho đối tượng
        pos.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        this.prevX = x;
        this.prevY = y;

        // NEEDED FOR SMOOTH DRAGGING
        dragged = true;

        loadMouseOver(x, y);

        // Check if the click is on the small red square (bottom-right corner)
        if (selectedRenderer.isPresent()) {
            IRenderer renderer = selectedRenderer.get();
            ScreenPosition pos = renderers.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();
            int width = renderer.getWidth();
            int height = renderer.getHeight();
            int squareSize = 5; // Size of the small square

            // Check if the click is within the small red square
            if (x >= absoluteX + width && x <= absoluteX + width + squareSize && y >= absoluteY + height && y <= absoluteY + height + squareSize) {
                resizing = true;
            }
        }

        super.mouseClicked(x, y, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        // NEEDED FOR SMOOTH DRAGGING
        dragged = false;
        resizing = false;

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        if (selectedRenderer.isPresent()) {
            if (resizing) {
                // Resize the selected renderer
                IRenderer renderer = selectedRenderer.get();
                ScreenPosition pos = renderers.get(renderer);
                int newWidth = x - pos.getAbsoluteX();
                int newHeight = y - pos.getAbsoluteY();

                // Ensure minimum size
                newWidth = Math.max(newWidth, 10);
                newHeight = Math.max(newHeight, 10);

                renderer.setWidth(newWidth);
                renderer.setHeight(newHeight);

                pos.setAbsolute(pos.getAbsoluteX(), pos.getAbsoluteY());

            } else {
                // Move the selected renderer
                moveSelectedRenderBy(x - prevX, y - prevY);
            }
        }

        this.prevX = x;
        this.prevY = y;
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private int mouseX, mouseY;

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

                if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {

                    return true;

                }

            }

            return false;
        }

    }

}