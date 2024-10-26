package cleanCatClient.mods.impl;

import cleanCatClient.constants.ModConstants;
import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.Render2D;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class TNTTimer extends Mod {
    private final Map<Integer, Long> tntMap = new HashMap<>();
    private final Minecraft mc = Minecraft.getMinecraft();

    public TNTTimer() {
        super(ModConstants.TNT_TIMER, ModConstants.TNT_TIMER_DESC, ModCategory.PLAYER);
    }

    private long time(){
        //kiem tra neu dang choi tren server thi return 3000
        return mc.isSingleplayer() ? 4000 : 3000;
    }

    @EventTarget
        public void render(Render2D render2D) {
            long currentTime = System.currentTimeMillis();

            // Iterate through all loaded entities
            for (Object entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityTNTPrimed) {
                    EntityTNTPrimed tnt = (EntityTNTPrimed) entity;
                    int entityId = tnt.getEntityId();

                    // If TNT is not in the map, add it with the current time
                    if (!tntMap.containsKey(entityId)) {
                        tntMap.put(entityId, currentTime);
                    }

                    // Calculate the time left before explosion
                    long timePlaced = tntMap.get(entityId);
                    long timeLeft = time() - (currentTime - timePlaced); // TNT explodes after 4 seconds

                    // Render the timer on the screen
                    if (timeLeft > 0) {
                        String timeLeftStr = String.format("%.1f", timeLeft / 1000.0);

                        // Set up OpenGL for rendering text
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float) (tnt.posX - mc.getRenderManager().viewerPosX),
                                (float) (tnt.posY - mc.getRenderManager().viewerPosY + tnt.height + 0.5),
                                (float) (tnt.posZ - mc.getRenderManager().viewerPosZ));
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(-0.025F, -0.025F, 0.025F);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glDepthMask(false);
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                        // Render the text
                        mc.fontRendererObj.drawString(timeLeftStr, -mc.fontRendererObj.getStringWidth(timeLeftStr) / 2, 0, 0xFFFFFF);

                        // Restore OpenGL state
                        GL11.glEnable(GL11.GL_DEPTH_TEST);
                        GL11.glDepthMask(true);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glPopMatrix();
                    } else {
                        tntMap.remove(entityId); // Remove TNT from map after it explodes
                    }
                }
            }
        }
}