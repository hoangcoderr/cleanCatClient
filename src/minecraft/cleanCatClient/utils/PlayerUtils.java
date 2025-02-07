package cleanCatClient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerUtils {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static Map<String, NetworkPlayerInfo> playerInfoCache = new HashMap<>();

    public static String extractPlayerName(IChatComponent component) {
        String text = component.getUnformattedText();

        // First try colon method
        int colonIndex = text.indexOf(":");
        if (colonIndex != -1) {
            String beforeColon = text.substring(0, colonIndex)
                    .replaceAll("§[0-9a-fklmnor]", ""); // Remove color codes
            String[] parts = beforeColon.split("\\||\\/|\\[|\\]|>|<");
            String lastPart = parts[parts.length - 1].trim();
            return lastPart.replaceAll("[\\[\\]\\s]+", "");
        }

        // Fallback to regex pattern
        Pattern pattern = Pattern.compile("^(?:§r)?(?:\\[.*?\\])?\\s*?(?:<)?([^>§\\s]+)(?:>)?.*$");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    public static void renderPlayerHead(ChatLine chatLine, int x, int y, int opacity) {
        String playerName = extractPlayerName(chatLine.getChatComponent());

        if (playerName != null) {
            NetworkPlayerInfo playerInfo = playerInfoCache.get(playerName);

            if (playerInfo == null) {
                playerInfo = mc.getNetHandler().getPlayerInfo(playerName);
                if (playerInfo != null) {
                    playerInfoCache.put(playerName, playerInfo);
                }   
            }

            if (playerInfo != null) {
                mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
                GlStateManager.enableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, opacity / 255.0F);

                Gui.drawScaledCustomSizeModalRect(
                        x, y,
                        8.0F, 8.0F,
                        8, 8,
                        8, 8,
                        64.0F, 64.0F
                );

                GlStateManager.disableBlend();
            }
        }
    }
}