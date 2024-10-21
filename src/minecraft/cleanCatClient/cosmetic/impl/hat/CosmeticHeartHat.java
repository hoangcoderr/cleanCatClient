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

public class CosmeticHeartHat extends CosmeticBase {
    private class ModelHeartHat extends CosmeticModelBase {

        private ModelRenderer heart;

        public ModelHeartHat(RenderPlayer player) {
            super(player);
            heart = new ModelRenderer(playerModel, 0, 0);
            heart.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 1); // Main heart shape
            heart.setTextureOffset(0, 9).addBox(-3.0F, -9.0F, -4.0F, 6, 1, 1); // Top part of the heart
            heart.setTextureOffset(0, 11).addBox(-2.0F, -10.0F, -4.0F, 4, 1, 1); // Top part of the heart
        }

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
            heart.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            heart.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            heart.rotationPointX = 0.0F;
            heart.rotationPointY = 0.0F;
            heart.render(scale);
        }
    }
    private final ModelHeartHat modelHeartHat;
    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/tophat/hat.png");

    public CosmeticHeartHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
        modelHeartHat = new ModelHeartHat(renderPlayer);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if(CosmeticBoolean.shouldRenderTopHat(player)) {
            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(TEXTURE);

            if(player.isSneaking()) {
                GL11.glTranslated(0, 0.225D, 0);
            }

            float[] color = CosmeticBoolean.getTopHatColor(player);
            GL11.glColor3f(color[0], color[1], color[2]);
            modelHeartHat.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
            GL11.glColor3f(1, 1, 1);
            GL11.glPopMatrix();
        }
    }
}