package cleanCatClient.cosmetic.impl.wing;

import cleanCatClient.cosmetic.CosmeticBase;
import cleanCatClient.cosmetic.CosmeticBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticDragonBabyWings extends CosmeticBase {
    private static final ResourceLocation TEXTURE = new ResourceLocation("cleanCatClient/Cosmetic/dragonbabywings/wings.png");
    private final ModelSatanWings modelSatanWings;

    public CosmeticDragonBabyWings(RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.modelSatanWings = new ModelSatanWings();
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (CosmeticBoolean.shouldRenderCosmetic(3,9,player)) {
            this.modelSatanWings.RenderWings(player.isSneaking(), player, 1);
        }

    }

    private class ModelSatanWings extends ModelBase {

        private void renderWingsIn3D(WorldRenderer worldrenderer) {
            Tessellator tessellator = Tessellator.getInstance();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, 0.0D, 0.0D);
            GL11.glEnable(32826);
            GL11.glTranslatef(1.0F, -0.10000001F, 0.1F);
            GL11.glScalef(1.875F, 1.875F, 1.0F);
            GL11.glRotatef(0.0F, 0.0F, 200.0F, 0.0F);
            GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            GL11.glScaled(1.0D, 1.0D, 0.7D);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
            worldrenderer.pos(1.0D, 0.0D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
            worldrenderer.pos(1.0D, 1.0D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
            worldrenderer.pos(0.0D, 1.0D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
            worldrenderer.pos(0.0D, 1.0D, -0.078125D).tex(1.0D, 0.0D).normal(0.0F, 0.0F, -1.0F).endVertex();
            worldrenderer.pos(1.0D, 1.0D, -0.078125D).tex(0.0D, 0.0D).normal(0.0F, 0.0F, -1.0F).endVertex();
            worldrenderer.pos(1.0D, 0.0D, -0.078125D).tex(0.0D, 1.0D).normal(0.0F, 0.0F, -1.0F).endVertex();
            worldrenderer.pos(0.0D, 0.0D, -0.078125D).tex(1.0D, 1.0D).normal(0.0F, 0.0F, -1.0F).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

            int k;
            float f1;
            float f2;
            for (k = 0; (float) k < 32.0F; ++k) {
                f1 = (float) k / 32.0F;
                f2 = 1.0F + -1.0F * f1 - 0.015625F;
                worldrenderer.pos(f1, 0.0D, -0.078125D).tex(f2, 1.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 0.0D, 0.0D).tex(f2, 1.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 1.0D, 0.0D).tex(f2, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 1.0D, -0.078125D).tex(f2, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
            }

            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

            for (k = 0; (float) k < 32.0F; ++k) {
                f1 = (float) k / 32.0F;
                f2 = 1.0F + -1.0F * f1 - 0.015625F;
                f1 += 0.03125F;
                worldrenderer.pos(f1, 1.0D, -0.078125D).tex(f2, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 1.0D, 0.0D).tex(f2, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 0.0D, 0.0D).tex(f2, 1.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
                worldrenderer.pos(f1, 0.0D, -0.078125D).tex(f2, 1.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
            }

            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

            for (k = 0; (float) k < 32.0F; ++k) {
                f1 = (float) k / 32.0F;
                f2 = 1.0F + -1.0F * f1 - 0.015625F;
                f1 += 0.03125F;
                worldrenderer.pos(0.0D, f1, 0.0D).tex(1.0D, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
                worldrenderer.pos(1.0D, f1, 0.0D).tex(0.0D, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
                worldrenderer.pos(1.0D, f1, -0.078125D).tex(0.0D, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
                worldrenderer.pos(0.0D, f1, -0.078125D).tex(1.0D, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
            }

            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

            for (k = 0; (float) k < 32.0F; ++k) {
                f1 = (float) k / 32.0F;
                f2 = 1.0F + -1.0F * f1 - 0.015625F;
                worldrenderer.pos(1.0D, f1, 0.0D).tex(0.0D, f2).normal(0.0F, -1.0F, 0.0F).endVertex();
                worldrenderer.pos(0.0D, f1, 0.0D).tex(1.0D, f2).normal(0.0F, -1.0F, 0.0F).endVertex();
                worldrenderer.pos(0.0D, f1, -0.078125D).tex(1.0D, f2).normal(0.0F, -1.0F, 0.0F).endVertex();
                worldrenderer.pos(1.0D, f1, -0.078125D).tex(0.0D, f2).normal(0.0F, -1.0F, 0.0F).endVertex();
            }

            tessellator.draw();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }

        public void RenderWings(boolean isSneaking, Entity entityIn, int id) {
            Tessellator tessellator = Tessellator.getInstance();
            GL11.glPushMatrix();
            GL11.glScalef(1.1F, 1.1F, 1.1F);
            if (isSneaking) {
                GL11.glTranslatef(0.0F, 0.185F, 0.175F);
            } else {
                GL11.glTranslatef(0.0F, 0.2F, 0.125F);
            }

            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glPushMatrix();
            if (System.currentTimeMillis() > entityIn.animation) {
                if (entityIn.isSneaking()) {
                    entityIn.airTicks = (float) ((double) entityIn.airTicks - 0.188D); // Tăng tốc độ vỗ khi đang cúi
                }


                entityIn.airTicks = (float) ((double) entityIn.airTicks + 0.1D); // Tăng tốc độ vỗ khi đang đứng


                entityIn.animation = System.currentTimeMillis() + 10L;
            }

            if (isSneaking) {
                GL11.glRotatef(33.0F + (float) Math.sin((double) entityIn.airTicks / 4.0D) * 20.0F, 1.5F, 0.0F, 2.5F);
            } else {
                GL11.glRotatef(24.0F + (float) Math.sin((double) entityIn.airTicks / 4.0D) * 20.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glTranslatef(-0.125F, 0.125F, 0.18F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
            GL11.glRotatef(100.0F, 0.0F, 1.0F, 0.0F);
            this.renderWingsIn3D(tessellator.getWorldRenderer());
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            if (isSneaking) {
                GL11.glRotatef(-30.0F - (float) Math.sin((double) entityIn.airTicks / 4.0D) * 20.0F, -1.5F, 0.0F, 2.5F);
            } else {
                GL11.glRotatef(-24.0F - (float) Math.sin((double) entityIn.airTicks / 4.0D) * 20.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glTranslatef(0.0F, 0.125F, 0.18F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
            GL11.glRotatef(80.0F, 0.0F, 1.0F, 0.0F);
            this.renderWingsIn3D(tessellator.getWorldRenderer());
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}