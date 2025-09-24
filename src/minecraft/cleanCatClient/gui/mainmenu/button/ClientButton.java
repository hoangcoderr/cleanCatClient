package cleanCatClient.gui.mainmenu.button;

import cleanCatClient.gui.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Cursor;


import org.lwjgl.input.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.awt.image.BufferedImage;

public class ClientButton extends GuiButton {

    private Cursor handCursor;
    private boolean isCursorSet = false;

    public ClientButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        setupHandCursor(); // Initialize the hand cursor
    }

    public ClientButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        setupHandCursor(); // Initialize the hand cursor
    }

    // Set up the hand cursor from a .cur file
    private void setupHandCursor() {
//        try {
//            // Load the image (preferably a .png file) that you want to use as the cursor
//            BufferedImage cursorImage = ImageIO.read(new File("E:/Game/minecraft/t.png")); // Replace with the path to your image file
//            int imageWidth = cursorImage.getWidth();
//            int imageHeight = cursorImage.getHeight();
//
//            // Convert the image to IntBuffer and flip it vertically
//            IntBuffer buffer = IntBuffer.allocate(imageWidth * imageHeight);
//            int[] pixels = new int[imageWidth * imageHeight];
//            cursorImage.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
//
//            // Flip the image vertically
//            for (int y = 0; y < imageHeight; y++) {
//                for (int x = 0; x < imageWidth; x++) {
//                    buffer.put(pixels[(imageHeight - y - 1) * imageWidth + x]);
//                }
//            }
//            buffer.flip();
//
//            // Adjust the hotspot coordinates to fine-tune cursor alignment
//            int hotspotX = 0; // Adjust X coordinate if needed
//            int hotspotY = imageHeight - 1; // Adjust Y coordinate; closer to imageHeight lowers the active point
//
//            // Create the LWJGL cursor with the adjusted hotspot
//            handCursor = new Cursor(imageWidth, imageHeight, hotspotX, hotspotY, 1, buffer, null);
//        } catch (IOException | org.lwjgl.LWJGLException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
                    && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            // Change color when hovered
            int color = this.hovered ? new Color(105, 170, 192, 187).getRGB()
                    : new Color(86, 78, 78, 60).getRGB();
            Gui.drawRoundedRect(this.xPosition, this.yPosition,
                    this.xPosition + this.width, this.yPosition + this.height,5,color);

            this.mouseDragged(mc, mouseX, mouseY);
            int textColor = this.hovered ? 16777120 : 14737632;

            // Manage cursor setting only when necessary
//            if (this.hovered) {
//                setCursorHand();
//            } else if (!this.hovered) {
//                resetCursor();
//            }

            FontUtil.normal.drawCenteredString(this.displayString,
                    this.xPosition + this.width / 2,
                    this.yPosition + (this.height - 8) / 2, textColor);
        }
    }

    private void setCursorHand() {
        if (!isCursorSet && handCursor != null) {
            try {
                Mouse.setNativeCursor(handCursor);
                isCursorSet = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resetCursor() {
        if (isCursorSet) {
            try {
                Mouse.setNativeCursor(null); // Reset to the default cursor
                isCursorSet = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
//        boolean result = super.mousePressed(mc, mouseX, mouseY);
//        if (result) {
//            resetCursor();
//        }
//        return result;
//    }
}