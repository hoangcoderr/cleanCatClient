package cleanCatClient.mods.impl;

import cleanCatClient.mods.ModConstants;
import cleanCatClient.mods.Mod;
import cleanCatClient.mods.ModCategory;

public class LazyChunkLoading extends Mod {
    public LazyChunkLoading() {
        super(ModConstants.LAZY_CHUNK_LOADING, ModConstants.LAZY_CHUNK_LOADING_DESC, ModCategory.SETTINGS);
    }
}
