package net.minecraft.client.renderer.entity;

import java.util.Random;

import cleanCatClient.mods.ModInstances;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render<EntityItem> {
    private final RenderItem itemRenderer;
    private Random field_177079_e = new Random();

    public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
        super(renderManagerIn);
        this.itemRenderer = p_i46167_2_;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    private int renderEntityItem(EntityItem itemIn, double x, double y, double z, float partialTicks, IBakedModel model) {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();
        if (item == null) {
            return 0;
        } else {
            boolean flag = model.isGui3d();
            int i = this.getItemGroup(itemstack);
            float f = 0.25F;
            if (ModInstances.getItemPhysics().isEnabled()) {
                float f1 = -0.125f;
                if (!flag) f1 = -0.175f;

                float f2 = model.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
                GlStateManager.translate((float) x, (float) y + f1 + 0.25F * f2, (float) z);

                if (flag || this.renderManager.options != null) {
                    float f3 = (((float) itemIn.getAge() + partialTicks) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                    if (ModInstances.getItemPhysics().isSpin()) {
                        GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
                    } else if (ModInstances.getItemPhysics().isRandomAngle()) {
                        float uniqueAngle = (itemIn.getEntityId() % 360) * 100 + (field_177079_e.nextFloat() * 20 - 10);
                        GlStateManager.rotate(uniqueAngle, 0.0F, 1.0F, 0.0F);
                    }

                }
                if (!flag) {
                    float f6 = -0.0F * (float) (i - 1) * 0.5F;
                    float f4 = -0.0F * (float) (i - 1) * 0.5F;
                    float f5 = -0.046875F * (float) (i - 1) * 0.5F;

                    if (itemIn.onGround) {
                        GlStateManager.rotate(180, 0.0f, 1.0f, 1.0f);
                    }
                }

                float speed = 40;
                if (!itemIn.onGround) {
                    float rotAmount = ((itemIn.getAge() + partialTicks) * speed) % 360;
                    GlStateManager.rotate(rotAmount, 1f, 0f, 1f);
                }
            } else {
                float f1 = MathHelper.sin(((float) itemIn.getAge() + partialTicks) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
                float f2 = model.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
                GlStateManager.translate((float) x, (float) y + f1 + 0.25F * f2, (float) z);

                if (flag || this.renderManager.options != null) {
                    float f3 = (((float) itemIn.getAge() + partialTicks) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                    GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
                }

                if (!flag) {
                    float f6 = -0.0F * (float) (i - 1) * 0.5F;
                    float f4 = -0.0F * (float) (i - 1) * 0.5F;
                    float f5 = -0.046875F * (float) (i - 1) * 0.5F;
                    GlStateManager.translate(f6, f4, f5);
                }
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    private int getItemGroup(ItemStack stack) {
        int i = 1;

        if (stack.stackSize > 48) {
            i = 5;
        } else if (stack.stackSize > 32) {
            i = 4;
        } else if (stack.stackSize > 16) {
            i = 3;
        } else if (stack.stackSize > 1) {
            i = 2;
        }

        return i;
    }

    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ItemStack itemstack = entity.getEntityItem();
        this.field_177079_e.setSeed(187L);
        boolean flag = false;

        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
        int i = this.renderEntityItem(entity, x, y, z, partialTicks, ibakedmodel);

        for (int j = 0; j < i; ++j) {
            if (ibakedmodel.isGui3d()) {
                GlStateManager.pushMatrix();

                if (j > 0) {
                    float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(f, f1, f2);
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
                float f3 = ibakedmodel.getItemCameraTransforms().ground.scale.x;
                float f4 = ibakedmodel.getItemCameraTransforms().ground.scale.y;
                float f5 = ibakedmodel.getItemCameraTransforms().ground.scale.z;
                GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (flag) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityItem entity) {
        return TextureMap.locationBlocksTexture;
    }
}
