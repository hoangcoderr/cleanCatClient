private class ModelWoolHat extends CosmeticModelBase {
    private final ModelRenderer woolHat;

    public ModelWoolHat(RenderPlayer player) {
        super(player);
        
        woolHat = new ModelRenderer(playerModel);
        woolHat.setTextureSize(64, 32);
        woolHat.addBox(-4.5F, -9F, -4.5F, 9, 3, 9);
        woolHat.setRotationPoint(0F, 0F, 0F);
        woolHat.rotateAngleX = 0F;
        woolHat.rotateAngleY = 0F;
        woolHat.rotateAngleZ = 0F;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
        woolHat.rotateAngleX = playerModel.bipedHead.rotateAngleX;
        woolHat.rotateAngleY = playerModel.bipedHead.rotateAngleY;
        woolHat.rotationPointX = 0.0F;
        woolHat.rotationPointY = 0.0F;
        woolHat.render(scale);
    }
}