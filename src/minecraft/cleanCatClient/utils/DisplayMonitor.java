package cleanCatClient.utils;

import net.minecraft.client.Minecraft;

public class DisplayMonitor implements Runnable {
    private static int previousWidth = Minecraft.displayWidthBefore;
    private static int previousHeight = Minecraft.displayHeightBefore;

    @Override
    public void run() {
        while (true) {
            if (Minecraft.displayWidthBefore != previousWidth || Minecraft.displayHeightBefore != previousHeight) {
                // Perform actions when display dimensions change
                onDisplaySizeChanged(Minecraft.displayWidthBefore, Minecraft.displayHeightBefore);

                // Update previous dimensions
                previousWidth = Minecraft.displayWidthBefore;
                previousHeight = Minecraft.displayHeightBefore;
            }

            // Sleep for a short period to avoid excessive CPU usage
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onDisplaySizeChanged(int newWidth, int newHeight) {
        // Implement the actions to be performed when display size changes
        System.out.println("Display size changed to: " + newWidth + "x" + newHeight);
    }
}

