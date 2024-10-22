package cleanCatClient.cosmetic.impl.wing;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticModelBase;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticDragonWings extends CosmeticBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/dragonwing/wings.png");
    private final ModelDragonWing modelDragonWing;

    public CosmeticDragonWings(RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.modelDragonWing = new ModelDragonWing(renderPlayer);
    }

    private float interpolate(float yaw1, float yaw2, float percent) {
        float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;
        if (f < 0) {
            f += 360;
        }
        return f;
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, float partialTicks) {
        if (!player.isInvisible()) {
            double rotate = interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);

            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(TEXTURE);

            double wingScale = 0.01; // Adjust wing scale if needed

            GL11.glScaled(-wingScale, -wingScale, wingScale);  // Scaling wings
            //GL11.glRotated(180 + rotate, 0, 1, 0); // Aligning wings with player's yaw
            GL11.glTranslated(0, 1.25, 0);  // Adjusting position
            GL11.glTranslated(0, 0, 0.2);

            if (player.isSneaking()) {
                GL11.glTranslated(0D, 0.125D, 0D);
            }

            float[] colors = {1.0F, 1.0F, 1.0F}; // Assuming default white color, replace with actual settings if available
            GL11.glColor3f(colors[0], colors[1], colors[2]);

            this.modelDragonWing.renderWings(scale);  // Call to render the dragon wings

            GlStateManager.popMatrix();
        }
    }

    private class ModelDragonWing extends CosmeticModelBase {
        private final ModelRenderer wing;
        private final ModelRenderer wingTip;

        public ModelDragonWing(RenderPlayer player) {
            super(player);

            setTextureOffset("wing.bone", 0, 0);
            setTextureOffset("wing.skin", -10, 8);
            setTextureOffset("wingtip.bone", 0, 5);
            setTextureOffset("wingtip.skin", -10, 18);

            // Create wing model renderer
            wing = new ModelRenderer(this, "wing");
            wing.setTextureSize(30, 30); // Adjust texture size if needed
            wing.setRotationPoint(-2F, 0, 0);
            wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
            wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);

            // Create wing tip model renderer
            wingTip = new ModelRenderer(this, "wingtip");
            wingTip.setTextureSize(30, 30);
            wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
            wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
            wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            wing.addChild(wingTip);
        }

        public void renderWings(float scale) {
            for (int i = 0; i < 2; i++) {
                GL11.glEnable(GL11.GL_CULL_FACE);

                float wingAnimation = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;

                // Rotate the wings
                this.wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos(wingAnimation) * 0.2F;
                this.wing.rotateAngleY = (float) Math.toRadians(20F) + (float) Math.sin(wingAnimation) * 0.4F;
                this.wing.rotateAngleZ = (float) Math.toRadians(20F);

                // Wingtip movement
                this.wingTip.rotateAngleZ = -((float) (Math.sin(wingAnimation + 2.0F) + 0.5D)) * 0.75F;

                // Separate each wing to its side (adjust this to control separation distance)
                if (i == 0) {
                    GL11.glTranslated(-0.75D, 0, 0); // Move left wing to the left
                } else {
                    GL11.glTranslated(0.75D, 0, 0);  // Move right wing to the right
                }

                // Render the current wing
                this.wing.render(scale);

                // Mirror the second wing horizontally
                GL11.glScalef(-1.0F, 1.0F, 1.0F);

                // Set culling to the front for the first wing
                if (i == 0) {
                    GL11.glCullFace(GL11.GL_FRONT);
                }
            }

// Reset culling and disable face culling
            GL11.glCullFace(GL11.GL_BACK);
            GL11.glDisable(GL11.GL_CULL_FACE);

        }
    }
}