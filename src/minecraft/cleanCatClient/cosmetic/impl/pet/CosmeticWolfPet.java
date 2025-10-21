package cleanCatClient.cosmetic.impl.pet;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticBoolean;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticWolfPet extends CosmeticBase {

    private ModelWolf modelWolf;
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/wolf/wolf.png");

    public CosmeticWolfPet(RenderPlayer renderPlayer) {
        super(renderPlayer);
        modelWolf = new ModelWolf();
    }

    public void reload() {
        modelWolf = new ModelWolf();
    }

    private void setupWolfStandingPose() {
        // Set default standing pose for wolf body, legs, tail, mane
        // Based on ModelWolf.setLivingAnimations when wolf is standing (not sitting)
        modelWolf.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
        modelWolf.wolfBody.rotateAngleX = (float) Math.PI / 2F;
        modelWolf.wolfBody.rotateAngleY = 0.0F;
        modelWolf.wolfBody.rotateAngleZ = 0.0F;

        // Mane must match body rotation and have correct position
        modelWolf.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
        modelWolf.wolfMane.rotateAngleX = (float) Math.PI / 2F;
        modelWolf.wolfMane.rotateAngleY = 0.0F;
        modelWolf.wolfMane.rotateAngleZ = 0.0F;

        modelWolf.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        modelWolf.wolfLeg1.rotateAngleX = 0.0F;
        
        modelWolf.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        modelWolf.wolfLeg2.rotateAngleX = 0.0F;
        
        modelWolf.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        modelWolf.wolfLeg3.rotateAngleX = 0.0F;
        
        modelWolf.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        modelWolf.wolfLeg4.rotateAngleX = 0.0F;

        modelWolf.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        modelWolf.wolfTail.rotateAngleX = (float) Math.toRadians(40); // Tail up slightly
        modelWolf.wolfTail.rotateAngleY = 0.0F;
        modelWolf.wolfTail.rotateAngleZ = 0.0F;

        modelWolf.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
        modelWolf.wolfHeadMain.rotateAngleX = 0.0F;
        modelWolf.wolfHeadMain.rotateAngleY = 0.0F;
        modelWolf.wolfHeadMain.rotateAngleZ = 0.0F;
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if (CosmeticBoolean.shouldRenderCosmetic(4, 16, player)) {
            GlStateManager.pushMatrix();

            // Attach to the player body so it follows rotations
            playerRenderer.getMainModel().bipedBody.postRender(scale);

            // Position the wolf on the player's right shoulder
            GlStateManager.translate(0.4F, -0.55F, -0.05F);

            // Slightly bigger
            GlStateManager.scale(0.38F, 0.38F, 0.38F);

            if (player.isSneaking()) {
                GlStateManager.translate(0.0F, 0.10F, 0.0F);
            }

            // Face forward with the player
            // (no extra rotation needed because we're parented to bipedBody)

            // Setup default standing pose for wolf (since we pass null entity)
            setupWolfStandingPose();

            // Bind vanilla wolf texture and render the vanilla wolf model
            playerRenderer.bindTexture(TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            modelWolf.render(null, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);

            GlStateManager.popMatrix();
        }
    }
}
