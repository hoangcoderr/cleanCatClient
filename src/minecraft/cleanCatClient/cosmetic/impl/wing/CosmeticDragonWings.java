package cleanCatClient.cosmetic.impl.wing;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticBoolean;
import cleanCatClient.cosmetic.CosmeticModelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticDragonWings extends CosmeticBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/dragonwing/wings.png");
    private final ModelDragonWing modelDragonWing;

    public CosmeticDragonWings(RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.modelDragonWing = new ModelDragonWing(renderPlayer);
    }


    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, float partialTicks) {
        if (!player.isInvisible() && CosmeticBoolean.shouldRenderCosmetic(3,7,player)) {
            this.modelDragonWing.renderWings(player, partialTicks);
        }
    }

    private class ModelDragonWing extends CosmeticModelBase {
        private final ModelRenderer wing;
        private final ModelRenderer wingTip;

        private float interpolate(float yaw1, float yaw2, float percent) {
            float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;
            if (f < 0) {
                f += 360;
            }
            return f;
        }

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


        private void renderWings(EntityPlayer player, float partialTicks) {
            GL11.glPushMatrix();
            GL11.glScaled(-1, -1, 1);
            GL11.glTranslated(0D, -0.2D, 0.2D);
            if (player.isSneaking()) {
                GL11.glTranslated(0D, 0.125D, 0D);
            }

            float[] colors = {0.0F, 0.0F, 0.0F};
            GL11.glColor3f(colors[0], colors[1], colors[2]);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
            for (int j = 0; j < 2; ++j) {
                GL11.glEnable(GL11.GL_CULL_FACE);
                float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
                this.wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos(f11) * 0.2F;
                this.wing.rotateAngleY = (float) Math.toRadians(20F) + (float) Math.sin(f11) * 0.4F;
                this.wing.rotateAngleZ = (float) Math.toRadians(20F);
                this.wingTip.rotateAngleZ = -((float) (Math.sin(f11 + 2.0F) + 0.5D)) * 0.75F;
                this.wing.render(0.0625F);
                GL11.glScalef(-1.0F, 1.0F, 1.0F);

                if (j == 0) {
                    GL11.glCullFace(1028);
                }
            }

            GL11.glCullFace(1029);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glColor3f(255F, 255F, 255F);
            GL11.glPopMatrix();
        }
}}