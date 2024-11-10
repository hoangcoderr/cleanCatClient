package cleanCatClient.cosmetic.impl.hat;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticBoolean;
import cleanCatClient.cosmetic.CosmeticModelBase;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.ModelAdapterWitch;
import org.lwjgl.opengl.GL11;

public class CosmeticWitchHat extends CosmeticBase {

    private final ModelWitchHat modelWitchHat;
    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/witchhat/hat.png");

    public CosmeticWitchHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
        modelWitchHat = new ModelWitchHat(renderPlayer);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if (CosmeticBoolean.shouldRenderWitchHat(player)) {
            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(TEXTURE);

            //GL11.glTranslatef(-0.295F, -0.55F, -0.3f);

            if (player.isSneaking()) {
                GL11.glTranslated(0, 0.225D, 0);
            }



            float[] color = CosmeticBoolean.getWitchHatColor(player);
            GL11.glColor3f(color[0], color[1], color[2]);
            modelWitchHat.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
            GL11.glColor3f(1, 1, 1);
            GlStateManager.popMatrix();
        }
    }

    private class ModelWitchHat extends CosmeticModelBase {

        private ModelRenderer witchHat;
        private ModelRenderer brim;
        private ModelRenderer crown;
        private ModelRenderer tip;
        public ModelWitchHat(RenderPlayer player) {
            super(player);
//            this.witchHat = new ModelRenderer(playerModel, 0, 0);
//            this.witchHat.setTextureSize(64, 128);
//
//            this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
//            brim = new ModelRenderer(playerModel, 0, 76);
//            brim.setTextureSize(64, 128);
//            brim.setRotationPoint(1.75F, -4.0F, 2.0F);
//            brim.addBox(0, 0.0F, 0.0F, 7, 4, 7);
//            brim.rotateAngleX = -0.05235988F;
//            brim.rotateAngleZ = 0.02617994F;
//            this.witchHat.addChild(brim);
//            crown = new ModelRenderer(playerModel, 0, 87);
//            crown.setTextureSize(64, 128);
//            crown.setRotationPoint(1.75F, -4.0F, 2.0F);
//            crown.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
//            crown.rotateAngleX = -0.10471976F;
//            crown.rotateAngleZ = 0.05235988F;
//            brim.addChild(crown);
//            tip = new ModelRenderer(playerModel, 0, 95);
//            tip.setTextureSize(64, 128);
//            tip.setRotationPoint(1.75F, -2.0F, 2.0F);
//            tip.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
//            tip.rotateAngleX = -0.20943952F;
//            tip.rotateAngleZ = 0.10471976F;
//            crown.addChild(tip);
            witchHat = new ModelAdapterWitch().getModelRenderer(new ModelWitch(0.0F), "hat");
        }

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
            witchHat.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            witchHat.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            witchHat.rotationPointX = 0.0F;
            witchHat.rotationPointY = 0.0F;
            witchHat.render(scale);

        }
    }
}