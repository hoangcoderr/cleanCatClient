package cleanCatClient.mods.impl;
import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class DisableBlockParticles extends Mod{
    public DisableBlockParticles() {
        super(ModConstants.DISABLE_BLOCK_PARTICLES, ModConstants.DISABLE_BLOCK_PARTICLES_DESC, ModCategory.WORLD);
    }
}
