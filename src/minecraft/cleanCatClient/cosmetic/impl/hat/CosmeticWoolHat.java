package cleanCatClient.cosmetic.impl.hat;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticBoolean;
import cleanCatClient.cosmetic.CosmeticModelBase;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticWoolHat extends CosmeticBase {

    private ModelWoolHat modelWoolHat;
    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/witchhat/hat.png");

    public CosmeticWoolHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
        modelWoolHat = new ModelWoolHat(renderPlayer);
    }

    public void reload() {
        modelWoolHat = new ModelWoolHat(playerRenderer);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if (CosmeticBoolean.shouldRenderCosmetic(2, 13, player)) {
            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(TEXTURE);

            //GL11.glTranslatef(-0.295F, -0.55F, -0.3f);

            if (player.isSneaking()) {
                GL11.glTranslated(0, 0.225D, 0);
                //reload();
            }


            float[] color = {1, 1, 1};
            GL11.glColor3f(color[0], color[1], color[2]);
            GL11.glScalef(-1f, -1f, -1f);
            modelWoolHat.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
            GL11.glColor3f(1, 1, 1);
            GlStateManager.popMatrix();
        }
    }

    private class ModelWoolHat extends CosmeticModelBase {

        private final ModelRenderer woolHat;

        public ModelWoolHat(RenderPlayer player) {
            super(player);
            this.woolHat = new ModelRenderer(playerModel, 0, 0);
            this.woolHat.setTextureSize(64, 32);

            // Define the submodels
            ModelRenderer santaHatBase = new ModelRenderer(playerModel, 0, 16);
            santaHatBase.addBox(-4.5F, -3F, -4.5F, 10, 4, 9, 0.1F);
            santaHatBase.setRotationPoint(0.2892F, 8.72718F, 0);
            santaHatBase.rotateAngleZ = (float) Math.toRadians(-15);

            ModelRenderer santaHatTop0 = new ModelRenderer(playerModel, 0, 0);
            santaHatTop0.addBox(-4F, -1F, -4F, 9, 3, 8);
            santaHatTop0.setRotationPoint(0.2892F, 8.72718F, 0);
            santaHatTop0.rotateAngleX = (float) Math.toRadians(-20);

            ModelRenderer santaHatTop1 = new ModelRenderer(playerModel, 0, 0);
            santaHatTop1.addBox(-3F, -1F, -3F, 6, 4, 6);
            santaHatTop1.setRotationPoint(1.03024F, 9.75039F, 0);
            santaHatTop1.rotateAngleX = (float) Math.toRadians(-50);

            ModelRenderer santaHatTop2 = new ModelRenderer(playerModel, 0, 0);
            santaHatTop2.addBox(-0.5F, 2F, -1.5F, 3, 3, 3);
            santaHatTop2.setRotationPoint(0.53024F, 10.61642F, 0);
            santaHatTop2.rotateAngleX = (float) Math.toRadians(-60);

            ModelRenderer santaHatTop3 = new ModelRenderer(playerModel, 0, 16);
            santaHatTop3.addBox(-0.90192F, -1.83013F, -2F, 4, 4, 4);
            santaHatTop3.setRotationPoint(0.0F, 12.11642F, -5.67F);
            santaHatTop3.rotateAngleX = (float) Math.toRadians(-15);

            // Add submodels to the main model
            this.woolHat.addChild(santaHatBase);
            this.woolHat.addChild(santaHatTop0);
            this.woolHat.addChild(santaHatTop1);
            this.woolHat.addChild(santaHatTop2);
            this.woolHat.addChild(santaHatTop3);
        }
        //  woolHat = new ModelAdapterWitch().getModelRenderer(new ModelWitch(0.0F), "hat");


        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
            woolHat.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            woolHat.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            woolHat.rotationPointX = 0.0F;
            woolHat.rotationPointY = 0.0F;
            woolHat.render(scale);

        }
    }
}